package model.state;

import model.exception.MyException;
import model.statement.IStatement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public record ProgramState
        (ExecutionStack<IStatement> executionStack,
         SymbolTable<String, Value> symbolTable,
         Out<Value> out,
         FileTable<StringValue, BufferedReader> fileTable,
         Heap heap, BarrierTable barrierTable,
         IStatement originalProgram, int id) {

    private static int nextId = 1;

    public static ProgramState newInstance(ExecutionStack<IStatement> executionStack,
                                           SymbolTable<String, Value> symbolTable,
                                           Out<Value> out,
                                           FileTable<StringValue, BufferedReader> fileTable,
                                           Heap heap, BarrierTable barrierTable,
                                           IStatement originalProgram) {
        return new ProgramState(executionStack,
                symbolTable, out, fileTable,
                heap, barrierTable, originalProgram, getNextId());
    }

    private synchronized static int getNextId() {
        return nextId++;
    }

    public ProgramState(ExecutionStack<IStatement> executionStack,
                        SymbolTable<String, Value> symbolTable,
                        Out<Value> out, FileTable<StringValue, BufferedReader> fileTable,
                        Heap heap, BarrierTable barrierTable,
                        IStatement originalProgram, int id) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(this.originalProgram);
        this.heap = heap;
        this.barrierTable = barrierTable;
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\nProgramState " + (id - 1) + " {");
        builder.append("\n\texecutionStack=").append(executionStack.toString());
        builder.append("\n\tsymbolTable=").append(symbolTable.toString());
        builder.append("\n\toutput=").append(out.toString());
        builder.append("\n\tfileTable=").append(fileTable.toString());
        builder.append("\n\theap=").append(heap.toString());
        builder.append("\n\tbarrierTable=").append(barrierTable.toString());
        builder.append("\n}");
        return builder.toString();
    }

    public boolean isNotCompleted() {
        return !(executionStack.isEmpty());
    }

    public ProgramState oneStep() throws MyException {
        if (executionStack.isEmpty()) {
            throw new MyException("ProgramState stack is empty");
        }
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }
}
