package trelloclone.presentation;

import org.springframework.stereotype.Component;
import net.rgielen.fxweaver.core.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import trelloclone.domain.model.Board;
import trelloclone.domain.model.TaskList;

@Component
@FxmlView("board.fxml")
public class BoardController {

    @FXML
    private Label boardTitleLabel;

    @FXML
    private ListView<String> listsView;

    private Board currentBoard;

    @FXML
    public void initialize() {
        // Inicialización vacía, espera a recibir el tablero real
    }

    public void setBoard(Board board) {
        this.currentBoard = board;
        boardTitleLabel.setText(board.getTitle());
        listsView.getItems().clear();
        for (TaskList list : board.getLists()) {
            listsView.getItems().add(list.getName());
        }
    }

    @FXML
    public void handleAddList(ActionEvent event) {
        listsView.getItems().add("Nueva Lista");
    }
}
