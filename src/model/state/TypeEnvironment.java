package model.state;

import model.type.Type;

public interface TypeEnvironment {
    Type lookup(String variableName);
    void add(String variableName, Type simpleType);
    TypeEnvironment deepCopy();
}
