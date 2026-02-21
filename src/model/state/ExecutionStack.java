package model.state;

import model.statement.IStatement;

import java.util.Iterator;

public interface ExecutionStack<T> {
    void push(T value);
    String format();
    T pop();
    boolean isEmpty();
    Iterator<T> iterator();
    void processStack();
}
