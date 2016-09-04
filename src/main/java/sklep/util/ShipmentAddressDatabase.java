package sklep.util;

import sklep.model.ShipmentAddress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ShipmentAddressDatabase {

    public static ShipmentAddress getAddress(int id)
    {
        DatabaseConnection db = new DatabaseConnection(new DatabaseCredentials(3));
        try {
            db.setConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = db.getConnection().prepareStatement("SELECT addresses.id, street, city, zipCode, state, country FROM addresses INNER JOIN users ON addresses.userId = users.id WHERE users.id =?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next())
            {
                return new ShipmentAddress(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
            else
            {
                return null;
            }
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
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
