package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.SimpleType;
import model.value.IntegerValue;

public record NewBarrierStatement(String variableName, Expression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var result = expression.evaluate(state.symbolTable(), state.heap());
        if (!result.getType().equals(SimpleType.INTEGER)) {
            throw new MyException("The expression " + expression + " does not evaluate to an integer");
        }

        var nr = ((IntegerValue) result).value();
        int newFreeLocation = state.barrierTable().declareVariable(nr);
        if (state.symbolTable().isDefined(variableName)) {
            if (state.symbolTable().getValue(variableName).getType().equals(SimpleType.INTEGER)) {
                state.symbolTable().update(variableName, new IntegerValue(newFreeLocation));
            }
            return null;
        } else {
            throw new MyException("The variable " + variableName + " has not been declared");
        }
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        var expressionType = expression.typecheck(typeEnv);
        if (!expressionType.equals(SimpleType.INTEGER)) {
            throw new MyException("The expression " + expression + " does not evaluate to an integer");
        }
        var variableType = typeEnv.lookup(variableName);
        if (!variableType.equals(SimpleType.INTEGER)) {
            throw new MyException("The variable " + variableName + " is not of integer type");
        }
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new NewBarrierStatement(variableName, expression);
    }

    @Override
    public String toString() {
        return "newBarrier(%s, %s)".formatted(variableName, expression);
    }
}
