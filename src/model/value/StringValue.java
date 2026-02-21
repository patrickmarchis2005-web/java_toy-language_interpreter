package model.value;

import model.exception.MyException;
import model.type.SimpleType;
import model.type.Type;

public record StringValue(String value) implements Value {
    @Override
    public Type getType() {
        return SimpleType.STRING;
    }

    @Override
    public boolean equals(Value obj) {
        if (!(obj instanceof StringValue(String stringValue))) {
            throw new MyException("Invalid type for StringValue");
        } else {
            return value.equals(stringValue);
        }
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return "(string value) " + value;
    }
}
