package gui.entry;

import model.value.Value;

public class HeapEntry {
    private Integer address;
    private Value value;

    public HeapEntry(Integer address, Value value) {
        this.address = address;
        this.value = value;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
