package model.state;

import model.exception.MyException;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public interface FileTable<K, V> {
    boolean isOpen(K filename);

    void addOpenFile(K fileName, V bufferedReader);

    V getOpenFile(K fileName);

    V closeFile(K fileName) throws MyException;

    Iterator<K> iterator();
}
