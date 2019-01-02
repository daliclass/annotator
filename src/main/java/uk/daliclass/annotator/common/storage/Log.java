package uk.daliclass.annotator.common.storage;

import java.util.List;

public interface Log<T> {
    Boolean create(T item);

    List<T> read();
}
