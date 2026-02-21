package model.expression;

import model.exception.MyException;
import model.state.Heap;
import model.state.SymbolTable;
import model.state.TypeEnvironment;
import model.type.Type;
import model.value.Value;

public record ConstantExpression(Value value) implements Expression {
    @Override
    public Value evaluate(SymbolTable symTable, Heap heap) throws MyException {
        return value;
    }

    @Override
    public Expression deepCopy() {
        return new ConstantExpression(value);
    }

    @Override
    public Type typecheck(TypeEnvironment typeEnv) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
