package sklep.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sklep.MainApp;
import sklep.model.Product;

public class BasketOverviewController {

    private MainApp mainApp;
    @FXML private TableView<Product> basketTable;

    @FXML private TableColumn<Product, String> nameColumn;

    @FXML private TableColumn<Product, Double> priceColumn;

    @FXML private Label priceLabel;

    @FXML
    private void initialize()
    {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().price().asObject());

    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        basketTable.setItems(mainApp.getBasket().getProducts());
        mainApp.getBasket().setSummaryPrice();
        priceLabel.setText(String.valueOf(mainApp.getBasket().getPrice()) + " zł");
    }

    @FXML public void handleBackToShop()
    {
        mainApp.showProductOverview();
    }

    @FXML public void handleFinalizeOrder()
    {

        if (!mainApp.getBasket().getProducts().isEmpty())
        {
            mainApp.showOrderOverview();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Pusty koszyk");
                    alert.setHeaderText("Koszyk jest pusty");
                    alert.setContentText("Koszyk jest pusty, dodaj produkty, a następnie sfinalizuj zamówienie.");
                    alert.showAndWait();
        }
    }

    //TODO dodac usuwanie z koszyka!!!

}
