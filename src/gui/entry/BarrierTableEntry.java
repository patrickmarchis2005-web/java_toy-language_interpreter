package gui.entry;

import java.util.List;

public class BarrierTableEntry {
    private Integer index;
    private Integer value;
    private List<Integer> listOfValues;

    public BarrierTableEntry(Integer index, Integer value, List<Integer> listOfValues) {
        this.index = index;
        this.value = value;
        this.listOfValues = listOfValues;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<Integer> getListOfValues() {
        return listOfValues;
    }

    public void setListOfValues(List<Integer> listOfValues) {
        this.listOfValues = listOfValues;
    }
}
