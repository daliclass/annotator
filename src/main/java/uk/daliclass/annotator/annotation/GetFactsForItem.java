package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.storage.Log;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetFactsForItem implements Function<Integer, List<ItemFact>> {

    private final Log<ItemFact> itemFactLog;

    public GetFactsForItem(Log<ItemFact> itemFactLog) {
        this.itemFactLog = itemFactLog;
    }

    @Override
    public List<ItemFact> apply(Integer integer) {
        List<ItemFact> itemFactList = itemFactLog.read().stream()
                .filter(itemFact -> itemFact.getItemId() == integer)
                .collect(Collectors.toList());
        return itemFactList;
    }
}
