package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;

public record HeapAllocationStatement(String variableName, Expression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!(state.symbolTable().isDefined(variableName))) {
            throw new MyException("The variable " + variableName + " is not defined");
        } else if (!(state.symbolTable().getValue(variableName).getType() instanceof RefType)) {
            throw new MyException("The variable " + variableName + " must be a ref type");
        }

        var value = expression.evaluate(state.symbolTable(), state.heap());
        RefValue refValue = (RefValue) state.symbolTable().getValue(variableName);
        if (!(refValue.locationType().equals(value.getType()))) {
            throw new MyException("The variable " + variableName + " must be a ref type");
        }

        int freeAddress = state.heap().createEntry(value);
        state.symbolTable().update(variableName, new RefValue(freeAddress, refValue.locationType()));
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        Type typeVariable = typeEnv.lookup(variableName);
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeVariable.equals(new RefType(typeExpression))) {
            return typeEnv;
        } else {
            throw new MyException("NEW stms: right hand side and left hand side have different types");
        }
    }

    @Override
    public String toString() {
        return "new(" +  variableName + ", " + expression + ")";
    }
}
