package model.value;

import model.exception.MyException;
import model.type.SimpleType;
import model.type.Type;

public record BooleanValue(boolean value) implements Value {
    @Override
    public Type getType() {
        return SimpleType.BOOLEAN;
    }

    @Override
    public String toString() {
        return "(boolean value) " + value;
    }

    @Override
    public boolean equals(Value obj) {
        if (!(obj instanceof BooleanValue booleanValue)) {
            throw new MyException("Invalid type for BooleanValue");
        } else {
            return value == booleanValue.value;
        }
    }

    @Override
    public Value deepCopy() {
        return new BooleanValue(value);
    }
}
