package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.domain.Idable;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.storage.Log;

import java.util.function.Consumer;

public class AddItemsToAnnotate<T extends Idable> implements Consumer<ItemSet<T>> {

    private final Log<ItemSet<T>> log;

    public ItemSet<T> itemSet;

    public AddItemsToAnnotate(Log<ItemSet<T>> log) {
        this.log = log;
    }

    @Override
    public void accept(ItemSet<T> itemSet) {
        for (int i = 0; i < itemSet.getItems().size(); i++) {
            itemSet.getItems().get(i).setItemId(i);
        }
        this.itemSet = itemSet;
        log.create(itemSet);
    }
}
