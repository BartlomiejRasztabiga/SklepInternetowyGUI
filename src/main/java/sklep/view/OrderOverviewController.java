package sklep.view;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sklep.MainApp;
import sklep.model.Basket;
import sklep.model.Order;
import sklep.model.Product;
import sklep.model.User;


public class OrderOverviewController {

    private Stage orderStage;
    private Order order;
    private MainApp mainApp;
    private User user;

    @FXML private TableView<Product> orderTable;

    @FXML private TableColumn<Product, String> nameColumn;

    @FXML private TableColumn<Product, Double> priceColumn;

    @FXML private Text orderNumber;

    @FXML private Text priceText;

    @FXML private Label nameLabel;

    @FXML private Label accountNumber;

    @FXML private Label balanceLabel;

    @FXML private TextField street;

    @FXML private TextField city;

    @FXML private TextField zipCode;

    @FXML private TextField country;

    @FXML private TextField phoneNumber;

    @FXML private TextField emailAddress;

    public void setOrderStage(Stage orderStage)
    {
        this.orderStage = orderStage;
    }

    public void createOrder(Basket basket)
    {
        order = new Order(basket);
    }

    @FXML
    private void initialize()
    {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().price().asObject());

    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        LoginDialogController.GetUserService service = new LoginDialogController.GetUserService();
        service.setUsername(mainApp.getUser().getUserCredentials().getUsername());
        service.start();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                mainApp.setUser(service.getValue());

            }
        });
        service.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("chuj");
                mainApp.getPrimaryStage().close();
            }
        });

        orderTable.setItems(order.getProducts());
        orderNumber.setText("#" + Integer.toString(order.getId()));
        priceText.setText(String.valueOf(order.getPrice()) + " zł");
        nameLabel.setText(mainApp.getUser().getName());
        accountNumber.setText(mainApp.getUser().getAccount().getNumber());
        balanceLabel.setText(String.valueOf(mainApp.getUser().getAccount().getBalance()) + " zł");


        if (mainApp.getUser().getAddress() != null) {
            street.setText(mainApp.getUser().getAddress().getStreet());
            city.setText(mainApp.getUser().getAddress().getCity());
            zipCode.setText(mainApp.getUser().getAddress().getZipCode());
            country.setText(mainApp.getUser().getAddress().getCountry());
        } else {
            street.setText("");
            city.setText("");
            zipCode.setText("");
            country.setText("");
        }

        mainApp.setOrderOverviewController(this);

        //TODO Dodac telefony i emaile
        /*String email = "bartlomiej.rasztabiga.official@gmail.com";
        EmailValidator validator = EmailValidator.getInstance(false, false);
        System.out.println(validator.isValid(email));*/
    }

    public void setUser(User user) { this.user = user; }

    public User getUser() { return this.user; }


    @FXML public void handlePayOrder()
    {
        if(street.getText().isEmpty() || street.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak danych wysyłki");
            alert.setHeaderText("Adres do wysyłki jest pusty");
            alert.setContentText("Adres do wysyłki jest pusty, uzupełnij dane, a następnie spróbuj ponownie");
            alert.showAndWait();
        }
    }

    public void closeStage() { this.orderStage.close(); }

    @FXML public void handleEditAddress()
    {
        mainApp.showEditAddressDialog();
    }

    @FXML public void handleEditContactDetails()
    {

    }




}
