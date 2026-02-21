package gui;

import controller.Controller;
import gui.entry.BarrierTableEntry;
import gui.entry.HeapEntry;
import gui.entry.SymTableEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.exception.MyException;
import model.state.BarrierTable;
import model.state.ProgramState;
import model.value.StringValue;
import model.value.Value;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramExecutorController implements Initializable {
    @FXML private TextField prgStatesCountTextField;
    @FXML private ListView<String> exeStackListView;
    @FXML private TableView<SymTableEntry> symTableView;
    @FXML private TableColumn<SymTableEntry, String> variableNameTableColumn;
    @FXML private TableColumn<SymTableEntry, String> variableValueTableColumn;
    @FXML private ListView<String> fileTableListView;
    @FXML private TableView<HeapEntry> heapTableView;
    @FXML private TableColumn<HeapEntry, String> heapAddressTableColumn;
    @FXML private TableColumn<HeapEntry, String> heapValueTableColumn;
    @FXML private TableView<BarrierTableEntry> barrierTableView;
    @FXML private TableColumn<BarrierTableEntry, String> barrierIndexTableColumn;
    @FXML private TableColumn<BarrierTableEntry, String> barrierValueTableColumn;
    @FXML private TableColumn<BarrierTableEntry, String> barrierListOfValuesTableColumn;
    @FXML private ListView<Value> outListView;
    @FXML private ListView<Integer> prgStateIdListView;
    @FXML private Button runOneStepButton;
    private ObservableList<SymTableEntry> symTableEntries = FXCollections.observableArrayList();
    private ObservableList<HeapEntry> heapEntries = FXCollections.observableArrayList();
    private ObservableList<BarrierTableEntry> barrierTableEntries = FXCollections.observableArrayList();
    private Controller controller;
    private Stage stage;
    private int selectedPrgStateId = -1;
    private boolean isInternalUpdate = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prgStateIdListView.getSelectionModel().selectedItemProperty().addListener(
                (prgStateId, oldValue, newValue) -> {
                    if (newValue == null || isInternalUpdate) {
                        return;
                    }
                    if (selectedPrgStateId != newValue) {
                        this.selectedPrgStateId = newValue;
                        refreshAll();
                    }
                }
        );
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setStage(Stage programExecutorStage) {
        this.stage = programExecutorStage;
        setupForStage();
    }

    private void setupForStage() {
        prgStatesCountTextField.setText(
                String.valueOf(controller.getNrOfProgramStates())
        );

        List<ProgramState> programStates = controller.getProgramStateList();
        List<Integer> prgStateIds = new ArrayList<>();
        for (var programState : programStates) {
            prgStateIds.add(programState.id());
        }

        // if the idList is the same, don't change it
        List<Integer> currentIdList = prgStateIdListView.getItems();
        if (currentIdList.equals(prgStateIds)) {
            return;
        }

        // otherwise, I have to change it
        isInternalUpdate = true;
        try {
            prgStateIdListView.getItems().setAll(prgStateIds);
            if (prgStateIds.contains(selectedPrgStateId)) {
                prgStateIdListView.getSelectionModel().select(selectedPrgStateId);
            } else {
                prgStateIdListView.getSelectionModel().clearSelection();
            }
        } finally {
            isInternalUpdate = false;
        }
    }

    private void refreshAll() {
        // first, I do the setup (necessary fields)
        setupForStage();

        // then I want to know: do I modify anything else?
        if (selectedPrgStateId == -1) {
            // no, then return
            return;
        }

        // yes, need to modify more
        populateExeStackListView();
        populateSymbolTableView();
        populateFileTableListView();
        populateHeapTableView();
        populateOutListView();
        pupulateBarrierTableView();
    }

    public void runOneStepButtonOnAction() {
        handleOneStep();
        refreshAll();
    }

    public void handleOneStep() throws MyException {
        List<ProgramState> programsList = controller.removeCompletedPrograms(controller.getProgramStateList());

        if (!programsList.isEmpty()) {
            var heap = programsList.getFirst().heap();
            heap.setContent(controller.conservativeGarbageCollector(
                    controller.getAddrFromAllSymTables(controller.getAllSymbolTables(programsList)), heap)
            );
            controller.oneStepForAllPrograms(programsList);
            programsList = controller.removeCompletedPrograms(controller.getProgramStateList());
        } else {
            stage.close();
        }

        controller.setProgramStateList(programsList);
    }

    private ProgramState getSelectedProgramState() {
        return controller.getProgramStateList()
                .stream().filter(prg -> prg.id() == selectedPrgStateId).findFirst().orElse(null);
    }

    private void populateExeStackListView() {
        ProgramState programState = getSelectedProgramState();
        if (programState == null) {
            exeStackListView.getItems().clear();
            return;
        }

        exeStackListView.getItems().clear();
        List<String> stringIStatements = new ArrayList<>();
        var stackIt = programState.executionStack().iterator();
        while (stackIt.hasNext()) {
            var stackEntry = stackIt.next();
            stringIStatements.add(stackEntry.toString());
        }

        exeStackListView.getItems().setAll(stringIStatements);
    }

    private void populateSymbolTableView() {
        ProgramState programState = getSelectedProgramState();
        if (programState == null) {
            return;
        }

        symTableEntries.clear();
        symTableView.getItems().clear();
        var symTable = programState.symbolTable().iterator();
        while (symTable.hasNext()) {
            var variableName = symTable.next();
            SymTableEntry symTableEntry = new SymTableEntry(variableName, programState.symbolTable().getValue(variableName));
            symTableEntries.add(symTableEntry);
        }

        variableNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("variableName"));
        variableValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        symTableView.setItems(symTableEntries);
    }

    private void populateFileTableListView() {
        var programState = getSelectedProgramState();
        if (programState == null) {
            return;
        }

        fileTableListView.getItems().clear();
        List<String> stringFiles = new ArrayList<>();
        var fileTable = programState.fileTable().iterator();
        while (fileTable.hasNext()) {
            StringValue fileName = fileTable.next();
            stringFiles.add(fileName.value());
        }

        fileTableListView.getItems().setAll(stringFiles);
    }

    private void populateHeapTableView() {
        ProgramState programState = getSelectedProgramState();
        if (programState == null) {
            return;
        }

        heapEntries.clear();
        heapTableView.getItems().clear();
        var heap = programState.heap().iterator();
        while (heap.hasNext()) {
            var address = heap.next();
            HeapEntry heapEntry = new HeapEntry(address, programState.heap().getEntry(address));
            heapEntries.add(heapEntry);
        }

        heapAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        heapTableView.setItems(heapEntries);
    }

    private void populateOutListView() {
//        ProgramState programState = getSelectedProgramState();
//        if (programState == null) {
//            return;
//        }
//
//        outListView.getItems().clear();
//        List<Value> outputs = new ArrayList<>();
//        var outList = programState.out().iterator();
//        while (outList.hasNext()) {
//            var output = outList.next();
//            outputs.add(output);
//        }
        List<Value> outputs = controller.getOutputList();
        outListView.getItems().setAll(outputs);
    }

    private void pupulateBarrierTableView() {
        ProgramState programState = getSelectedProgramState();
        if (programState == null) {
            return;
        }

        barrierTableEntries.clear();
        barrierTableView.getItems().clear();
        var barrierTable = programState.barrierTable().iterator();
        while (barrierTable.hasNext()) {
            var index = barrierTable.next();
            var pair = programState.barrierTable().getPair(index);
            BarrierTableEntry barrierTableEntry = new BarrierTableEntry(index, pair.getKey(), pair.getValue());
            barrierTableEntries.add(barrierTableEntry);
        }

        barrierIndexTableColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        barrierValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        barrierListOfValuesTableColumn.setCellValueFactory(new PropertyValueFactory<>("listOfValues"));
        barrierTableView.setItems(barrierTableEntries);
    }
}
