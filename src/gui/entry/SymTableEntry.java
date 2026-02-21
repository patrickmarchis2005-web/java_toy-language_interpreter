package gui.entry;

import model.value.Value;

public class SymTableEntry {
    private String variableName;
    private Value value;

    public SymTableEntry(String variableName, Value value) {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
