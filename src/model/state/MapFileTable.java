package model.state;

import model.exception.MyException;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapFileTable<K, V> implements FileTable<K, V> {
//    private Map<StringValue, BufferedReader> fileTable = new HashMap<>();
//    private Map<K, V> fileTable = new HashMap<>();
    private Map<K, V> fileTable = new ConcurrentHashMap<>();
    // ConcurrentHashMap e thread-safe

    @Override
    public boolean isOpen(K filename) {
        return fileTable.containsKey(filename);
    }

    @Override
    public void addOpenFile(K fileName, V bufferedReader) {
        fileTable.put(fileName, bufferedReader);
    }

    @Override
    public V getOpenFile(K fileName) {
        return fileTable.get(fileName);
    }

    @Override
    public V closeFile(K fileName) throws MyException {
        return fileTable.remove(fileName);
    }

    @Override
    public Iterator<K> iterator() {
        return fileTable.keySet().iterator();
    }
}
