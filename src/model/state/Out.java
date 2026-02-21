package model.state;

import java.util.Iterator;
import java.util.List;

public interface Out<V> {
    void add(V value);
    Iterator<V> iterator();
    List<V> getOutputList();
}
