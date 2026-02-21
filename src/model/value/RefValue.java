package model.value;

import model.type.RefType;
import model.type.Type;

public record RefValue(int address, Type locationType) implements Value {

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Value obj) {
        if (obj instanceof RefValue refValue) {
            return (address == refValue.address && locationType == refValue.locationType );
        } else {
            return false;
        }
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }
}
