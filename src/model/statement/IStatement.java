package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.TypeEnvironment;

public interface IStatement extends Cloneable {
    ProgramState execute(ProgramState state) throws MyException;
    TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException;
    default IStatement deepCopy() {
        return this;
    }
    default String format() {
        return this.toString();
    }
}
