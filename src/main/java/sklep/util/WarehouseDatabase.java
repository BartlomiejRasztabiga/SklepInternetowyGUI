package sklep.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sklep.model.Product;
import sklep.model.Warehouse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseDatabase {

    public static Warehouse getWarehouse() throws SQLException {
        DatabaseConnection db = new DatabaseConnection(new DatabaseCredentials(3));
        db.setConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = db.getConnection().prepareStatement("SELECT id, name, price FROM laptopy");
            rs = pst.executeQuery();

            ObservableList<Product> tmpProducts = FXCollections.observableArrayList();
            while (rs.next()) {
                tmpProducts.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }
            return new Warehouse(tmpProducts);
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
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}

