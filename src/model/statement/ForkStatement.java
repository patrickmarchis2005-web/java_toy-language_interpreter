package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.StackExecutionStack;
import model.state.SymbolTable;
import model.state.TypeEnvironment;

public record ForkStatement(IStatement statement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StackExecutionStack<IStatement> stack = new StackExecutionStack<>();
        var symbolTable = state.symbolTable().deepCopy();
        return ProgramState.newInstance(stack, symbolTable, state.out(),
                state.fileTable(), state.heap(), state.barrierTable(), statement);
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
