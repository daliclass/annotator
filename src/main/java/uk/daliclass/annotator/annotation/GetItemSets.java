package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.domain.Idable;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.storage.Log;

import java.util.List;
import java.util.function.Supplier;

public class GetItemSets<T extends Idable> implements Supplier<List<ItemSet<T>>> {

    private final Log<ItemSet<T>> itemSetLog;

    public GetItemSets(Log<ItemSet<T>> itemSetLog) {
        this.itemSetLog = itemSetLog;
    }

    @Override
    public List<ItemSet<T>> get() {
        return itemSetLog.read();
    }
}
