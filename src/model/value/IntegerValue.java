package model.value;

import model.exception.MyException;
import model.type.SimpleType;
import model.type.Type;

public record IntegerValue(int value) implements Value {
    @Override
    public Type getType() {
        return SimpleType.INTEGER;
    }

    @Override
    public boolean equals(Value obj) {
        if (!(obj instanceof IntegerValue integerValue)) {
            throw new MyException("Invalid type for IntegerValue");
        } else {
            return integerValue.value() == value;
        }
    }

    @Override
    public Value deepCopy() {
        return new IntegerValue(value);
    }

    @Override
    public String toString() {
        return "(integer value) " + value;
    }
}
