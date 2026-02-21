package model.state;

import javafx.util.Pair;

import java.util.Iterator;
import java.util.List;

public interface BarrierTable {
    int declareVariable(int nr);
    boolean lookup(int foundIndex);
    Pair<Integer, List<Integer>> getPair(int foundIndex);
    Iterator<Integer> iterator();
}
