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

    public Map<String, String> pedirMaterias(String num_empleado){
        String consulta="call materiasDocente("+num_empleado+")";
        ResultSet rs;
        Map<String, String> materias = new HashMap<>();
        try{
        CallableStatement st=connection.prepareCall(consulta);
        rs=st.executeQuery();
        while(rs.next()){
            materias.put(rs.getString(1), rs.getString(2));
        }
        }
        catch(Exception e){

        }
        return materias;
    }

    public String getNombreDocente(int num_emp){
        String consulta="select nomb from docentes where num_emp="+num_emp;
        try{
            ResultSet rs=connection.prepareCall(consulta).executeQuery();
            while(rs.next()){
                return rs.getString(1);
            }
        }catch (Exception e){

        }
        return null;
    }



}

