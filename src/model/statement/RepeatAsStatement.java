package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.SimpleType;

public record RepeatAsStatement(IStatement statement, Expression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var whileStatement = new WhileStatement(expression, statement);
        state.executionStack().push(whileStatement);
        state.executionStack().push(statement);
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        var expressionType = expression.typecheck(typeEnv);
        if (!expressionType.equals(SimpleType.BOOLEAN)) {
            throw new MyException(expressionType.toString() + " does not evaluate to boolean");
        }
        return statement.typecheck(typeEnv);
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatAsStatement(statement, expression);
    }

    @Override
    public String toString() {
        return "repeat %s as %s".formatted(statement, expression);
    }
}
