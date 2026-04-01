package trelloclone.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.rgielen.fxweaver.core.FxmlView;
import trelloclone.application.CreateBoardUseCase;
import trelloclone.domain.model.Board;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxControllerAndView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

@Component
@FxmlView("login.fxml")
public class LoginController {

    private final FxWeaver fxWeaver;
    private final CreateBoardUseCase createBoardUseCase;

    @Autowired
    public LoginController(FxWeaver fxWeaver, CreateBoardUseCase createBoardUseCase) {
        this.fxWeaver = fxWeaver;
        this.createBoardUseCase = createBoardUseCase;
    }

    @FXML
    private TextField emailField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            errorLabel.setText("Por favor, introduce un email válido.");
            return;
        }
        errorLabel.setText("");
        System.out.println("Información de inicio de sesión para: " + email);
        
        Board board = null;
        try {
            board = createBoardUseCase.execute("Mi Tablero Principal", email);
        } catch (Exception e) {
            System.err.println("Error al inicializar el tablero: " + e.getMessage());
        }

        if (board != null) {
            // Navegar a la pantalla principal pasando el tablero cargado
            FxControllerAndView<BoardController, Node> controllerAndView = fxWeaver.load(BoardController.class);
            controllerAndView.getController().setBoard(board);
            Parent root = (Parent) controllerAndView.getView().get();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
