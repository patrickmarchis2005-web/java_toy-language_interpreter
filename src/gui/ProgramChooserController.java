package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.statement.IStatement;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ProgramChooserController implements Initializable {
    private Map<IStatement, Controller> statementsAndControllers;
    private Map<Integer, IStatement> integerIStatementMap = new HashMap<>();
    @FXML
    private ListView<String> programsListView;
    @FXML
    private Button executeProgramButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setStatementsAndControllers(Map<IStatement, Controller> statementsAndControllers) {
        this.statementsAndControllers = statementsAndControllers;
        getStringAndProcessPrograms();
    }

    private void getStringAndProcessPrograms() {
        int nr = 0;
        for (var statement : statementsAndControllers.keySet()) {
            programsListView.getItems().add((nr + 1) + ". " + statement.toString());
            integerIStatementMap.put(nr++, statement);
        }
    }

    public void executeProgramButtonOnAction(ActionEvent event) throws Exception {
        int selectedIndex = programsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }

        Controller selectedController = statementsAndControllers.get(integerIStatementMap.get(selectedIndex));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramExecutor.fxml"));
        Parent root = loader.load();

        Stage programExecutorStage = new Stage();
        programExecutorStage.setScene(new Scene(root, 800, 700));
        programExecutorStage.setTitle("Program Chooser");

        ProgramExecutorController programExecutorController = loader.getController();
        programExecutorController.setController(selectedController);
        programExecutorController.setStage(programExecutorStage);

        programExecutorStage.show();
    }
}

