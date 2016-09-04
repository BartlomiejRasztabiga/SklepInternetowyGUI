package sklep.view;


import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sklep.MainApp;
import sklep.model.Product;
import sklep.util.DatabaseConnection;
import sklep.util.DatabaseCredentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsOverviewController
{

    @FXML private TableView<Product> productTable;

    @FXML private TableColumn<Product, String> nameColumn;

    @FXML private TableColumn<Product, Double> priceColumn;

    @FXML private Label nameLabel;

    @FXML private Label priceLabel;

    @FXML private Label idLabel;

    @FXML private AnchorPane addedToBasketPane;

    @FXML private TextField searchText;

    @FXML private Label errorText;

    private MainApp mainApp;

    public ProductsOverviewController() {}

    @FXML private void initialize()
    {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().price().asObject());

        showProductData(null);

        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductData(newValue));
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

            productTable.setItems(mainApp.getProductData());
    }

    private void showProductData(Product product)
    {
        if (product != null)
        {
            nameLabel.setText(product.getName());
            priceLabel.setText(Double.toString(product.getPrice()));
        }
        else
        {
            nameLabel.setText("");
            priceLabel.setText("");
        }
    }

    @FXML
    private void handleAddProductToBasket()
    {
        try {
            int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex <= -1) throw new ArrayIndexOutOfBoundsException("Nie zaznaczono produktu");
            Product product = productTable.getItems().get(selectedIndex);

            mainApp.getBasket().addProduct(product);

            mainApp.getBasket().setSummaryPrice();


            addedToBasketPane.setVisible(true);

            FadeTransition ft = new FadeTransition(Duration.millis(600), addedToBasketPane);
            ft.setFromValue(0);
            ft.setToValue(1.0);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);

            ft.play();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void handleGoToBasket()
    {
        mainApp.showBasketOverview();
    }

    @FXML
    private void handleSearchProduct()
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
            pst = db.getConnection().prepareStatement("SELECT * FROM laptopy WHERE name LIKE '%"+searchText.getText()+"%'");
            rs = pst.executeQuery();

            ObservableList<Product> tmpProducts = FXCollections.observableArrayList();
            while (rs.next()) {
                tmpProducts.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }
            if(!tmpProducts.isEmpty())
            {
                productTable.setItems(tmpProducts);
                errorText.setText("");
            }
            else errorText.setText("Nie znaleziono produktów");
        } catch (SQLException ex) {
            ex.printStackTrace();
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
