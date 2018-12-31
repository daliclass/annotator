package uk.daliclass.annotator.facts;

import java.util.List;

public interface Log<T> {
    Boolean create(T fact);

    List<T> read();
}
