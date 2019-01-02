package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.storage.Log;

import java.util.List;
import java.util.function.Consumer;

public class AddItemsToAnnotate<T> implements Consumer<List<T>> {

    private final Log<T> log;

    public AddItemsToAnnotate(Log<T> log) {
        this.log = log;
    }

    @Override
    public void accept(List<T> items) {
        for (T item: items) {
            log.create(item);
        }
    }
}
