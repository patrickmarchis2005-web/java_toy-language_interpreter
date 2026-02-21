package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.SimpleType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStatement(Expression expression, String varName) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.symbolTable().isDefined(varName) || state.symbolTable().getValue(varName).getType() != SimpleType.INTEGER) {
            throw new MyException("The variable " + varName + " is not defined or its type is not integer");
        }

        var value = expression.evaluate(state.symbolTable(), state.heap());
        if (!(value instanceof StringValue fileName)) {
            throw new MyException("The expression " + expression + " must be a string value");
        }
//        synchronized (state.fileTable()) {
//
//        }
        if (!state.fileTable().isOpen(fileName)) {
            throw new MyException("The file " + fileName + " is not open");
        }

        BufferedReader bufferedReader = state.fileTable().getOpenFile(fileName);
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }

        if (line == null) {
            state.symbolTable().update(varName, new IntegerValue(0));
        } else {
            state.symbolTable().update(varName, new IntegerValue(Integer.parseInt(line)));
        }
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeExpression == SimpleType.STRING) {
            return typeEnv;
        } else {
            throw new MyException("The file name must be of type string");
        }
    }

    public String toString() {
        return "readFile(" + expression + ", " + varName + ")";
    }
}
