package sklep.util;


import sklep.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {

    public static User getUser(String username) throws SQLException
    {
        DatabaseConnection db = new DatabaseConnection(new DatabaseCredentials(3));
        db.setConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = db.getConnection().prepareStatement("SELECT id, firstName, lastName, accNumber, username, password, phoneNumber, email FROM users WHERE username = ?");
            pst.setString(1, username);
            rs = pst.executeQuery();

            if (rs.next())
            {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
            else
            {
                return null;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            db.closeConnection();
            try {
                assert pst != null;
                pst.close();
                assert rs != null;
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
