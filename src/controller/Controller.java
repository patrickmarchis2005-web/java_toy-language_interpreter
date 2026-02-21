package controller;

import model.exception.MyException;
import model.state.Heap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.RefValue;
import model.value.Value;
import repository.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private final Repository repository;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<Value> outputList;

    public Controller(Repository repository) {
        this.repository = repository;

        if (!repository.getProgramStateList().isEmpty()) {
            this.outputList = repository.getProgramStateList().getFirst().out().getOutputList();
        }
    }
    
    
    public void allSteps() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programsList = removeCompletedPrograms(getProgramStateList());
        programsList.forEach(programState -> programState.executionStack().processStack());

        while (!programsList.isEmpty()) {
            var heap = programsList.getFirst().heap();
            heap.setContent(conservativeGarbageCollector(
                    getAddrFromAllSymTables(getAllSymbolTables(programsList)), heap)
            );
            oneStepForAllPrograms(programsList);
            programsList = removeCompletedPrograms(repository.getProgramStateList());
        }
        executor.shutdown();
        setProgramStateList(programsList);
    }

    public void oneStepForAllPrograms(List<ProgramState> programsList) {
        programsList.forEach(repository::logProgramStateExec);

        List<Callable<ProgramState>> callList = programsList.stream()
                .map(program -> (Callable<ProgramState>)(program::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramsList = null;
        try {
            newProgramsList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (MyException | InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(program -> program != null)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        programsList.addAll(newProgramsList);

        programsList.forEach(repository::logProgramStateExec);
        repository.setProgramStateList(programsList);
    }

    
    public List<ProgramState> getProgramStateList() {
        return repository.getProgramStateList();
    }

    public void setProgramStateList(List<ProgramState> programsList) {
        repository.setProgramStateList(programsList);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        return inProgramList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }
    
    public Map<Integer, Value> conservativeGarbageCollector(List<Integer> symTableAddr, Heap heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.address();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromAllSymTables(List<SymbolTable<String, Value>> symTables) {
        return symTables.stream()
                .map(table -> table.getContent().values())
                .flatMap(Collection::stream)
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.address();})
                .collect(Collectors.toList());
    }

    public List<SymbolTable<String, Value>> getAllSymbolTables(List<ProgramState> programsList) {
        return programsList.stream()
                .map(ProgramState::symbolTable)
                .collect(Collectors.toList());
    }

    public int getNrOfProgramStates() {
        return repository.getProgramStateList().size();
    }

    public List<Value> getOutputList() {
        return outputList;
    }
}
