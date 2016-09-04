package sklep.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sklep.MainApp;
import sklep.model.User;
import sklep.util.DatabaseConnection;
import sklep.util.DatabaseCredentials;
import sklep.util.UserDatabase;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EditAddressDialogController {

    private Stage editAddressStage;
    private MainApp mainApp;

    @FXML private TextField street;

    @FXML private TextField city;

    @FXML private TextField zipCode;

    @FXML private TextField country;

    @FXML private Label errorText;

    @FXML private ProgressIndicator progressIndicator;

    @FXML
    private void initialize()
    {

    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

    }

    public void setEditAddressStage(Stage editAddressStage)
    {
        this.editAddressStage = editAddressStage;
    }


    @FXML
    public void handleSaveContactDetails() throws InterruptedException
    {
        if(street.getText().equals("") || street.getText().isEmpty() ||
                city.getText().equals("") || city.getText().isEmpty() ||
                zipCode.getText().equals("") || zipCode.getText().isEmpty() ||
                country.getText().equals("") || country.getText().isEmpty())
        {
            errorText.setText("Wypełnij wszystkie pola");
        }
        else
        {
            UpdateContactDetailsService service = new UpdateContactDetailsService();
            service.setStreet(street.getText());
            service.setCity(city.getText());
            service.setZipCode(zipCode.getText());
            service.setCountry(country.getText());
            progressIndicator.visibleProperty().bind(service.runningProperty());
            service.start();
            //TODO Zrobic aktualizacje tez na przycisk "Zapisz dane"
            //TODO Pola maja miec wartosci pobierane z bazy danych
            service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    editAddressStage.close();
                }
            });
            service.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    errorText.setText("Bład bazy danych");
                }
            });

            LoginDialogController.GetUserService serviceSecond = new LoginDialogController.GetUserService();
            serviceSecond.setUsername(mainApp.getUser().getUserCredentials().getUsername());
            serviceSecond.start();
            serviceSecond.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    mainApp.setUser(serviceSecond.getValue());

                }
            });
            serviceSecond.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    System.out.println("chuj");
                    mainApp.getPrimaryStage().close();
                }
            });

            Thread.sleep(1000);

            mainApp.getOrderOverviewController().closeStage();
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Aby zauważyć zmianę adresu, zamknij okno zamówienia i ponownie je sfinalizuj.");

            alert.showAndWait();
            */
        }

    }

    public class UpdateContactDetailsService extends Service<Void> {

        private StringProperty street = new SimpleStringProperty();
        private StringProperty city = new SimpleStringProperty();
        private StringProperty zipCode = new SimpleStringProperty();
        private StringProperty country = new SimpleStringProperty();

        public final void setStreet(String street) { this.street.set(street); }
        public final void setCity(String city) { this.city.set(city); }
        public final void setZipCode(String zipCode) { this.zipCode.set(zipCode); }
        public final void setCountry(String country) { this.country.set(country); }

        protected Task<Void> createTask() {
            return new Task<Void>() {
                protected Void call() throws SQLException {
                    DatabaseConnection db = new DatabaseConnection(new DatabaseCredentials(3));
                    db.setConnection();
                    PreparedStatement pst = null;
                    ResultSet rs = null;
                    try {
                        pst = db.getConnection().prepareStatement("UPDATE addresses SET street= ?, city= ?, zipCode= ?, country= ? WHERE userId = ?");
                        pst.setString(1, street.getValue());
                        pst.setString(2, city.getValue());
                        pst.setString(3, zipCode.getValue());
                        pst.setString(4, country.getValue());
                        pst.setInt(5, mainApp.getUser().getId());
                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        db.closeConnection();
                        try {
                            db.closeConnection();
                            assert pst != null;
                            pst.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                return null;
                }
            };
        }
    }

}
