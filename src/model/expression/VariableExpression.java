package model.expression;

import model.exception.MyException;
import model.state.Heap;
import model.state.SymbolTable;
import model.state.TypeEnvironment;
import model.type.Type;
import model.value.Value;

public record VariableExpression(String variableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symTable, Heap heap) throws MyException {
        if (!symTable.isDefined(variableName)) {
            throw new RuntimeException("Variable " + variableName + " is not defined\n");
        }
        return symTable.getValue(variableName);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(this.variableName);
    }

    @Override
    public Type typecheck(TypeEnvironment typeEnv) throws MyException {
        return typeEnv.lookup(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
