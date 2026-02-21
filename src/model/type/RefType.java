package model.type;

import model.value.RefValue;
import model.value.Value;

public record RefType(Type inner) implements Type {
    public boolean equals(Object another) {
        if (another instanceof RefType anotherRef) {
            return inner.equals(anotherRef.inner());
        } else {
            return false;
        }
    }

    public String toString() {
        return "Ref " + inner.toString();
    }

    public Value getDefaultValue() {
        return new RefValue(0, inner);
    }
}
