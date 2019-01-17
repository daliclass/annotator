package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.storage.Log;

import java.util.function.Consumer;

public class AddItemsToAnnotate<T> implements Consumer<ItemSet<T>> {

    private final Log<ItemSet<T>> log;

    public AddItemsToAnnotate(Log<ItemSet<T>> log) {
        this.log = log;
    }

    @Override
    public void accept(ItemSet<T> itemSet) {
        log.create(itemSet);
    }
}
