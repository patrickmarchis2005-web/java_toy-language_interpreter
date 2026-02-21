package model.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ListOut<V> implements Out<V> {
    private final List<V> outputList;

    public ListOut() {
//        outputList = new ArrayList<>();
        outputList = new Vector<>();
        // Vector e thread-safe, dar mai incet
    }

    @Override
    public void add(V value) {
        outputList.add(value);
    }

    @Override
    public Iterator<V> iterator() {
        return outputList.iterator();
    }

    @Override
    public List<V> getOutputList() {
        return outputList;
    }

    @Override
    public String toString() {
        return outputList.toString();
    }
}
