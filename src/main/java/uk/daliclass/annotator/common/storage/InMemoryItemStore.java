package uk.daliclass.annotator.common.storage;

import java.util.ArrayList;
import java.util.List;

public class InMemoryItemStore<T> implements Log<T> {

    private List<T> items = new ArrayList<>();

    @Override
    public Boolean create(T item) {
        items.add(item);
        return Boolean.TRUE;
    }

    @Override
    public List<T> read() {
        return new ArrayList<>(items);
    }
}
