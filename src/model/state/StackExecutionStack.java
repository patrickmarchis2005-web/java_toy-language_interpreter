package model.state;

import model.statement.CompoundStatement;
import model.statement.IStatement;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StackExecutionStack<T> implements ExecutionStack<T> {
    private final List<T> stack = new LinkedList<>();
//    private final Stack<T> stack = new Stack<>();

    @Override
    public void push(T value) {
//        stack.push(value);
        stack.addFirst(value);
    }

    @Override
    public String format() {
        StringBuilder sb = new StringBuilder("Execution Stack:");
        for (T value : stack) {
            sb.append("\n\t\t").append(value.toString());
        }
        return sb.toString();
    }

    @Override
    public T pop() {
//        return stack.pop();
        return stack.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return format();
    }

    @Override
    public Iterator<T> iterator() {
        processStack();
        return stack.iterator();
    }

    @Override
    public void processStack() {
        var copy = new LinkedList<>(stack);
        List<T> result = new LinkedList<>();

        while (!copy.isEmpty()) {
            var statement = copy.removeFirst();
            if (statement instanceof CompoundStatement(IStatement first, IStatement second)) {
                copy.addFirst((T) second);
                copy.addFirst((T) first);
            } else {
                result.add(statement);
            }
        }
        stack.clear();
        stack.addAll(result);
    }
}
