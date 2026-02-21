package model.expression;

import model.exception.MyException;
import model.state.Heap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.TypeEnvironment;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record HeapReadingExpression(Expression expression) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        var value = expression.evaluate(symbolTable, heap);
        if (!(value instanceof RefValue refValue)) {
            throw new MyException("The expression" + expression.toString() + " must evaluate to a RefValue");
        }
        Value associatedValue = heap.getEntry(refValue.address());
        if (associatedValue == null) {
            throw new MyException("The address " + refValue.address() + " has no associated value in the heap");
        }
        return associatedValue;
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

    @Override
    public Expression deepCopy() {
        return new HeapReadingExpression(expression.deepCopy());
    }

    @Override
    public Type typecheck(TypeEnvironment typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof RefType(Type inner)) {
            return inner;
        } else {
            throw new MyException("The rH argument is not a Ref Type");
        }
    }
}
