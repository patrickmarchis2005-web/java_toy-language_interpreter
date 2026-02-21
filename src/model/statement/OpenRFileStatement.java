package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public record OpenRFileStatement(Expression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.symbolTable(), state.heap());
        if (!(value instanceof StringValue fileName)) {
            throw new MyException("The expression has to be a string value");
        }

        if (state.fileTable().isOpen(fileName)) {
            throw new MyException("The file has already been opened");
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName.value()));
        } catch (FileNotFoundException e) {
            throw new MyException(e.getMessage());
        }
        state.fileTable().addOpenFile(fileName, bufferedReader);
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString() {
        return "openRFile(" + expression + ")";
    }
}
