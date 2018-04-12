package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MysqlConnector {


    private Connection connection;

    public MysqlConnector() throws SQLException, ClassNotFoundException {
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

        }
        return materias;
    }

    public ArrayList<Map<String, String>> revisarAlumno(String numControl) {
        String consulta = "call revisarAlumno('" + numControl + "')";
        ResultSet rs;
        ArrayList<Map<String, String>> clases = new ArrayList<>();
        try {
            CallableStatement st = connection.prepareCall(consulta);
            rs = st.executeQuery();
            if(!rs.next()) {
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


}

