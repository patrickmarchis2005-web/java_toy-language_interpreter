package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.TypeEnvironment;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        return typeEnv;
    }

    public String toString() {
        return "nop";
    }
}
