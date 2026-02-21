package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.SimpleType;
import model.type.Type;
import model.value.BooleanValue;

public record WhileStatement(Expression expression, IStatement statement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.symbolTable(), state.heap());
        if (value instanceof BooleanValue booleanValue) {
            if (booleanValue.value() == false) {
                return null;
            } else {
                state.executionStack().push(new WhileStatement(expression, statement));
                state.executionStack().push(statement);
                return null;
            }
        } else {
            throw new MyException("The expression " + expression + " does not evaluate to a boolean");
        }
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeExpression == SimpleType.BOOLEAN) {
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The expression of WHILE has not the type bool");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while (" + expression + ") " + statement;
    }
}
