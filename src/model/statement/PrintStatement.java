package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public record PrintStatement(Expression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.out().add(expression.evaluate(state.symbolTable(), state.heap()));
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}
