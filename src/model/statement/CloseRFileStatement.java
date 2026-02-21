package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.value.StringValue;

import java.io.IOException;

public record CloseRFileStatement(Expression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.symbolTable(), state.heap());
        if (!(value instanceof StringValue fileName)) {
            throw new MyException("Invalid file name");
        }

        if (!state.fileTable().isOpen(fileName)) {
            throw new MyException("The file does not exist");
        }

        try {
            state.fileTable().getOpenFile(fileName).close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString() {
        return "closeRFile(" + expression + ")";
    }
}
