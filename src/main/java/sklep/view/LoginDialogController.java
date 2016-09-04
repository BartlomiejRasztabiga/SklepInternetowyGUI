package sklep.view;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import sklep.MainApp;
import sklep.model.Basket;
import sklep.model.User;
import sklep.model.Warehouse;
import sklep.util.PasswordAuthentication;
import sklep.util.UserDatabase;
import sklep.util.WarehouseDatabase;

import java.sql.SQLException;


public class LoginDialogController{

    @FXML private TextField usernameField;

    @FXML private PasswordField passwordField;

    @FXML private Label errorText;

    @FXML private ProgressIndicator progressIndicator;

    public static final String[] array = new String[1];

    private MainApp mainApp;

    public LoginDialogController() {}

    @FXML private void initialize()
    {

    }


    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }

    @FXML
    public void handleLoginButton() throws InterruptedException
    {
        if (usernameField.getText() != null && !usernameField.getText().isEmpty() && passwordField.getText() != null && !passwordField.getText().isEmpty())
        {
            //TODO Wszedzie gdzie sie da obsluz SQlException w wypadjku jak baza nie dzala w jakis ludzki sposob
                GetUserService service = new GetUserService();
                service.setUsername(usernameField.getText());
                progressIndicator.visibleProperty().bind(service.runningProperty());
                service.start();
                service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        User user = service.getValue();

                        if (user == null)
                        {
                            errorText.setText("Nie znaleziono użytkownika " + usernameField.getText());
                            return;
                        }

                        char[] password = passwordField.getText().toCharArray();

                        PasswordAuthentication pswdAuth = new PasswordAuthentication();

                        if(pswdAuth.authenticate(password, user.getUserCredentials().getPassword()))
                        {
                            //TODO Zalogowano pomyslnie
                            try {
                                Warehouse warehouse = WarehouseDatabase.getWarehouse();
                                assert warehouse != null;
                                mainApp.setProductData(warehouse.getProducts());
                                mainApp.setBasket(new Basket());
                            } catch (SQLException e) {
                                errorText.setText("Nie można się połączyć z bazą danych");
                            }

                            mainApp.setUser(user);
                            mainApp.showProductOverview();
                        }
                        else
                        {
                            errorText.setText("Niepoprawne hasło");

                    /*Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Niepoprawne hasło");
                    alert.setHeaderText("Niepoprawne hasło");
                    alert.setContentText("Wpisz poprawne hasło");

                    alert.showAndWait();*/
                        }
                    }
                });
                service.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        errorText.setText("Nie można się połączyć z bazą danych");
                    }
                });


        }
        else
        {
            errorText.setText("Wypełnij oba pola");
        }


    }

    public static class GetUserService extends Service<User> {

        private StringProperty username = new SimpleStringProperty();

        public final void setUsername(String username) { this.username.set(username); }

        protected Task<User> createTask() {
            return new Task<User>() {
                protected User call() throws SQLException {
                    return UserDatabase.getUser(username.getValue());
                }
            };
        }
    }

}

