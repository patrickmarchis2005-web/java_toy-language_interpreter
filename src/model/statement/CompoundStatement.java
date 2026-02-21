package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.TypeEnvironment;

public record CompoundStatement(IStatement first, IStatement second) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var stack = state.executionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    public String toString() {
        return first.toString() + "; " + second.toString();
    }
}
