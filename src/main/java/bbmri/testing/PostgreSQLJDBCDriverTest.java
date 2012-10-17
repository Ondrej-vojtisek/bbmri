package bbmri.testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLJDBCDriverTest {
    public static void main(String[] argv) throws InstantiationException, IllegalAccessException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            System.out.println("postgresql JDBC Driver Registered!");

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/users", "postgres",
                    "hanako");
            //Or use this way

            //Do something with the Connection
        } catch (ClassNotFoundException e) {
            //Cannot register postgresql MySQL driver
            System.out.println("This is something you have not add in postgresql library to classpath!");
            e.printStackTrace();
        }catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }finally{
            //After using connection, release the postgresql resource.
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}