package sklep.util;

import java.sql.*;

public class DatabaseConnection {

    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private DatabaseCredentials dbCred;

    public DatabaseConnection(DatabaseCredentials dbCred) {
        this.dbCred = dbCred;
    }

    public void setConnection() throws SQLException
    {

        con = DriverManager.getConnection("jdbc:mariadb://web.rasztabiga.p3.tiktalik.io:3306/sklepinternetowy?user=sklep&password=Gallendors5");

    }

    public void closeConnection()
    {
        try {

            if (rs != null) {
                rs.close();
            }

            if (st != null) {
                st.close();
            }

            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }

    public Connection getConnection()
    {
        return con;
    }



}