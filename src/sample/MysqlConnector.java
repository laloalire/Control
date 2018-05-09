package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MysqlConnector {


    private Connection connection;

    public MysqlConnector() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/escuela?user=root");
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Map<String, String> pedirMaterias(String num_empleado) {
        String consulta = "call materiasDocente(" + num_empleado + ")";
        ResultSet rs;
        Map<String, String> materias = new HashMap<>();
        try {
            CallableStatement st = connection.prepareCall(consulta);
            rs = st.executeQuery();
            while (rs.next()) {
                materias.put(rs.getString(1), rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materias;
    }

    public ArrayList<Map<String, String>> revisarAlumno(String numControl) {
        String consulta = "call revisarAlumno('" + numControl + "')";
        ResultSet rs;
        ArrayList<Map<String, String>> clases = new ArrayList<>();
        try {
            CallableStatement st = connection.prepareCall(consulta);

            if(!st.execute()) {
                //Si no existe es numero de control
                return null;
            }
            consulta = "call clasesEnCurso()";
            st = connection.prepareCall(consulta);
            rs = st.executeQuery();
            while (rs.next()) {
                Map<String, String> clase = new HashMap<>();
                clase.put("Rg_id", rs.getString("Rg_id"));
                clase.put("maestro", rs.getString("maestro"));
                clase.put("asignatura", rs.getString("asignatura"));
                clase.put("entrada", rs.getString("entrada"));
                clase.put("salida", rs.getString("salida"));
                clase.put("aula", rs.getString("aula"));
                clases.add(clase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clases;
    }

    //funcion para checar el pw del Admin
    public boolean checarAdmin(String pw){
        String consulta="call checkarAdmin(?)";
        try{
            CallableStatement st=connection.prepareCall(consulta);
            st.setString(1, pw);
            ResultSet rs = st.executeQuery();
            rs.first();
             int resultado = rs.getInt(1);
             return  resultado > 0;

        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }
    public boolean alumnoYaRegistrado(String numControl, String registroId){
        String consulta = "Select count(*) from registroalumno where reg_id='"+registroId+"' and ncont='"+numControl+"'";
        try {
            CallableStatement st = connection.prepareCall(consulta);
            ResultSet rs = st.executeQuery();
            rs.next();
            int count =rs.getInt(1);
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public  ArrayList<String> alumnoEnClase(String numControl){
        ArrayList<String> resultado = new ArrayList<>();
        String consulta = "call revisarAlumnoEnClase('"+numControl+"')";
        try {
            CallableStatement st = connection.prepareCall(consulta);
            ResultSet rs = st.executeQuery();
            agregarElementoArray(resultado, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void registrarAlumno(String registroID, String numControl){
        String consulta = "call registrarAlumno("+registroID+", '"+numControl+"')";
        try {
            CallableStatement st = connection.prepareCall(consulta);
            System.out.println(st.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getNombreDocente(int num_emp) {
        String consulta = "select nomb from docentes where num_emp=" + num_emp;
        try {
            ResultSet rs = connection.prepareCall(consulta).executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public ArrayList<String> aulasDisponibles (){
        String consulta = "select  aulaid  from  aulas  where  aulaid  not in  ( select  aula_id  from registros  where  fecha  = CURDATE()  and  entrada <  CURTIME()  and salida > CURTIME());";
        return  consultaAArrayList(consulta);
    }

    private ArrayList<String > consultaAArrayList(String consulta) {
        ArrayList<String> respuesta = new ArrayList<>();
        try {
            CallableStatement st = connection.prepareCall(consulta);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                respuesta.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public ArrayList<String> getRegistros(String numEmp, String fechainicio, String fechafin) {
        String consulta = "call getRegistros("+ numEmp +",'"+fechainicio+"', '"+fechafin+"')";
        return  consultaAArrayList(consulta);
    }

    public ArrayList<ArrayList<String>> getEntradas() {
        String consulta = "call getInfRegistros";
        ResultSet rs;
        ArrayList<ArrayList<String>> registros = new ArrayList<>();
        try {
           CallableStatement st = connection.prepareCall(consulta);
            rs = st.executeQuery();
            while (rs.next()) {
                ArrayList<String> registro = new ArrayList<>();
                for(int i = 0; i < 11; i++){
                    registro.add( rs.getString(i+1));
                }
                registros.add(registro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registros;

    }

    public ArrayList<String>  revisarDocenteEnClase(String numEmp){
        String consulta = "call revisarDocenteEnClase ("+numEmp+")";
        ArrayList<String> resultado = new ArrayList<>();
        try {
            CallableStatement st = connection.prepareCall(consulta);
            ResultSet rs = st.executeQuery();
            agregarElementoArray(resultado, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    public void eliminarRegistro(String registroId){
        String consulta = "call eliminarRegistro("+registroId+")";
        try {
            CallableStatement st = connection.prepareCall(consulta);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void agregarElementoArray(ArrayList<String> resultado, ResultSet rs) throws SQLException {
        while (rs.next()){
            resultado.add(rs.getString(1));
            resultado.add(rs.getString(2));
            resultado.add(rs.getString(3));
            resultado.add(rs.getString(4));
        }
    }


    public ArrayList<String> getListaDatos(String idRegistro) {
        String consulta = "call getDatosLista("+idRegistro+")";
        ArrayList<String> respuesta = new ArrayList<>();
        try {
            CallableStatement st = connection.prepareCall(consulta);
            ResultSet rs = st.executeQuery();
            agregarElementoArray(respuesta, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public void insertarRegistro(String entrada, String salida,  int aula, int asignatura, String numEmp){
        String query = "INSERT INTO registros(sexoh, sexom, fecha, entrada, salida, aula_id, asig_id, num_emp) VALUES (0, 0, now(), ?, ?, ?, ?, ?)";
        try {
            CallableStatement st = connection.prepareCall(query);
            st.setString(1, entrada);
            st.setString(2, salida);
            st.setInt(3, aula);
            st.setInt(4, asignatura);
            st.setString(5, numEmp);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getListaAlumnos(String idRegistro) {
        String consulta = "call getLista("+idRegistro+")";
        return  consultaAArrayList(consulta);
    }

    public void eliminarAsistencia(String idregistro, String numControl) {
        String consulta = "delete from registroalumno where reg_id ="+idregistro +" and ncont='"+numControl+"'";
        try {
            CallableStatement st = connection.prepareCall(consulta);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

