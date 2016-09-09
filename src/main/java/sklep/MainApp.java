package sklep;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sklep.model.Basket;
import sklep.model.Product;
import sklep.model.User;
import sklep.view.*;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private Basket basket;
    private User user;
    private OrderOverviewController orderOverviewController;

    public MainApp()
    {
        //Simple change to invoke Travis CL Build
        //Again
        //TODO HIBERNATE Zmusić do działania
        //TODO Wyświetlać w koszyku tylko raz taki sam produkt(dodać kolumne ilosc)
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sklep Internetowy");
        this.primaryStage.setResizable(false);

        initRootLayout();

        showLoginDialog();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            loader.setLocation(classLoader.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginDialog()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //loader.setLocation(MainApp.class.getResource("view/LoginDialog.fxml"));
            loader.setLocation(classLoader.getResource("LoginDialog.fxml"));
            AnchorPane loginDialog = loader.load();

            //TODO Jednak tylko zamowienie zrobic w innym stage

            rootLayout.setCenter(loginDialog);

            LoginDialogController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showProductOverview() {
        try {
            // Load products overview.
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //loader.setLocation(MainApp.class.getResource("view/ProductsOverview.fxml"));
            loader.setLocation(classLoader.getResource("ProductsOverview.fxml"));
            AnchorPane productsOverview = loader.load();

            // Set products overview into the center of root layout.
            rootLayout.setCenter(productsOverview);

            ProductsOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBasketOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //loader.setLocation(MainApp.class.getResource("view/BasketOverview.fxml"));
            loader.setLocation(classLoader.getResource("BasketOverview.fxml"));
            AnchorPane basketOverview = loader.load();

            rootLayout.setCenter(basketOverview);

            BasketOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //loader.setLocation(MainApp.class.getResource("view/OrderOverview.fxml"));
            loader.setLocation(classLoader.getResource("OrderOverview.fxml"));
            AnchorPane orderOverview = loader.load();

            Stage orderStage = new Stage();
            orderStage.setTitle("Dokończ zamówienie");
            orderStage.initModality(Modality.WINDOW_MODAL);
            orderStage.initOwner(primaryStage);
            Scene scene = new Scene(orderOverview);
            orderStage.setScene(scene);

            OrderOverviewController controller = loader.getController();
            controller.setOrderStage(orderStage);
            controller.createOrder(basket);
            controller.setMainApp(this);

            orderStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showEditAddressDialog()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            loader.setLocation(classLoader.getResource("EditAddressDialog.fxml"));
            AnchorPane editAddressDialog = loader.load();

            Stage editAddressStage = new Stage();
            editAddressStage.setTitle("Edytuj dane adresowe");
            editAddressStage.initModality(Modality.WINDOW_MODAL);
            editAddressStage.initOwner(primaryStage);
            Scene scene = new Scene(editAddressDialog);
            editAddressStage.setScene(scene);

            EditAddressDialogController controller = loader.getController();
            controller.setEditAddressStage(editAddressStage);
            controller.setMainApp(this);

            editAddressStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Returns the main stage.
     *
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Product> getProductData() { return productData; }

    public void setProductData(ObservableList<Product> products) { this.productData = products; }

    public Basket getBasket() { return basket; }

    public void setBasket(Basket basket) { this.basket = basket; }

    public User getUser() { return this.user; }

    public void setUser(User user) { this.user = user; }

    public void setOrderOverviewController(OrderOverviewController controller) { this.orderOverviewController = controller; }

    public OrderOverviewController getOrderOverviewController() { return this.orderOverviewController; }

    public static void main(String[] args) {
        launch(args);
    }
}