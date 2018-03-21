package sample;
import java.sql.*;

import java.util.List ;
import java.util.ArrayList ;

public class MysqlConnector {


    private Connection connection;

    public MysqlConnector() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("localhost/escuela", "root", "");
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public int checarDocente(int num_empleado){
        String consulta="call CheckDocente("+num_empleado+")";
        ResultSet rs;

        try{
        CallableStatement st=connection.prepareCall(consulta);
        rs=st.executeQuery();
        while(rs.next()){
            return rs.getInt(1);
        }
        }
        catch(Exception e){

        }
        return 0;
    }

    public String getNombreDocente(int )



}

