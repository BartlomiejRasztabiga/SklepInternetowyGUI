package sklep.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import sklep.model.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDatabase {

    public static Account getAccount(String number)
    {
        DatabaseConnection db = new DatabaseConnection(new DatabaseCredentials(3));
        try {
            db.setConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = db.getConnection().prepareStatement("SELECT COUNT(*) FROM accounts WHERE number = ?");
            pst.setString(1, number);
            rs = pst.executeQuery();
            rs.next();
            //TODO Do poprawy zliczanie wierszy
            if (rs.getInt(1) > 1)
            {
                System.out.println("Niejednoznaczny wynik zapytania SQL");
                return null;
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
        try {
            pst = db.getConnection().prepareStatement("SELECT number, balance FROM accounts WHERE number = ?");
            pst.setString(1, number);
            rs = pst.executeQuery();
//TODO Sprawdzic czy zwrocony jest tylko jeden wiersz
            if (rs.next())
            {
                return new Account(rs.getString("number"), rs.getDouble("balance"));
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
                pst.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }



    public static void setBalance(Account acc)
    {
        DatabaseConnection db = new DatabaseConnection(new DatabaseCredentials(2));
        try {
            db.setConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pst = null;
        try {
            pst = db.getConnection().prepareStatement("UPDATE accounts SET balance = ? WHERE number = ?");
            pst.setDouble(1, acc.getBalance());
            pst.setString(2, acc.getNumber());
            pst.executeUpdate();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AccountDatabase.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            db.closeConnection();
            try {
                assert pst != null;
                pst.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


}