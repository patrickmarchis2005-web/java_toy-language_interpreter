package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;

public record HeapWritingStatement(String variableName, Expression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.symbolTable().isDefined(variableName)) {
            throw new MyException("Variable " + variableName + " is not defined");
        }
        var refValue = (RefValue) state.symbolTable().getValue(variableName);
        if (!(refValue.getType() instanceof RefType )) {
            throw new MyException("Variable " + variableName + " is not of type RefType");
        }
        if (!state.heap().isKey(refValue.address())) {
            throw new MyException("The address " +  refValue.address() + " has no associated value in the heap");
        }

        var value = expression.evaluate(state.symbolTable(), state.heap());
        if (!value.getType().equals(refValue.locationType())) {
            throw new MyException("The variable " + variableName + " and the expression " + expression + " don't have matching types");
        }
        state.heap().updateEntry(refValue.address(), value);
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        Type typeVariable = typeEnv.lookup(variableName);
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeVariable.equals(new RefType(typeExpression))) {
            return typeEnv;
        } else {
            throw new MyException("wH stmt: right hand side and left hand side have different types");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + variableName + ", " + expression + ")";
    }
}
