package repository;

import model.exception.MyException;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProgramsRepository implements Repository {
    private List<ProgramState> programStateList;
    private String logFilePath;

    public ProgramsRepository(ProgramState programState, String logFilePath) {
        this.programStateList = new ArrayList<>();
        this.programStateList.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public void setProgramState(ProgramState state) {
        programStateList.clear();
        programStateList.add(state);
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return programStateList;
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStateList) {
        this.programStateList = programStateList;
    }

    @Override
    public void logProgramStateExec(ProgramState programState) throws MyException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                new FileWriter(logFilePath, true)))) {
            var stackIt = programState.executionStack().iterator();
            var symTableIt = programState.symbolTable().iterator();
            var outIt = programState.out().iterator();
            var fileTableIt = programState.fileTable().iterator();
            var heapIt = programState.heap().iterator();
            var barrierTableIt = programState.barrierTable().iterator();

            printWriter.printf("Program State %d\n", programState.id());
            printWriter.printf("ExeStack_%d:\n", programState.id());
            while (stackIt.hasNext()) {
                printWriter.println(stackIt.next());
            }

            printWriter.printf("\nSymTable_%d:\n", programState.id());
            while (symTableIt.hasNext()) {
                var key = symTableIt.next();
                printWriter.print(key);
                printWriter.print(" -> ");
                printWriter.println(programState.symbolTable().getValue(key));
            }

            printWriter.println("\nHeap:");
            while (heapIt.hasNext()) {
                var key = heapIt.next();
                printWriter.print(key);
                printWriter.print(" -> ");
                printWriter.println(programState.heap().getEntry(key));
            }

            printWriter.println("\nFileTable:");
            while (fileTableIt.hasNext()) {
                var key = fileTableIt.next();
                printWriter.print(key);
                printWriter.print(" -> ");
                printWriter.println(programState.fileTable().getOpenFile(key));
            }

            printWriter.println("\nBarrierTable:");
            while (barrierTableIt.hasNext()) {
                var key = barrierTableIt.next();
                printWriter.print(key);
                printWriter.print(" -> ");
                var pair = programState.barrierTable().getPair(key);
                printWriter.println(pair.getKey() + " : " + pair.getValue());
            }

            printWriter.println("\nOut:");
            while (outIt.hasNext()) {
                printWriter.println(outIt.next());
            }
            printWriter.println("______________\n\n");
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }
}
