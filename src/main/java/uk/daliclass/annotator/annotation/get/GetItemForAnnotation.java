package uk.daliclass.annotator.annotation.get;

import uk.daliclass.annotator.common.domain.Idable;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.storage.Log;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class GetItemForAnnotation<T extends Idable> implements BiFunction<String, Integer, AnnotatedItem> {

    private final Log<Fact> factLog;
    private final Log<ItemFact> itemFactLog;
    private final Log<T> itemLog;

    public GetItemForAnnotation(Log<Fact> factLog, Log<ItemFact> itemFactLog, Log<T> itemLog) {
        this.factLog = factLog;
        this.itemFactLog = itemFactLog;
        this.itemLog = itemLog;
    }

    @Override
    public AnnotatedItem<T> apply(String username, Integer itemId) {
        List<Fact> potentialFacts = factLog.read();
        List<T> items = itemLog.read();
        List<ItemFact> itemFacts = itemFactLog.read();
        AnnotatedItem<T> annotatedItem = new AnnotatedItem<>();
        annotatedItem.potentialFacts = potentialFacts;
        annotatedItem.itemFacts = itemFacts.stream()
                .filter(itemFact -> itemFact.getItemId() == itemId)
                .map(ItemFact::getFact)
                .collect(Collectors.toList());
        Optional<T> productOptional =
                items.stream()
                        .filter(product -> product.getItemId() == itemId)
                        .findFirst();
        annotatedItem.item = productOptional.orElse(null);
        Integer index = items.indexOf(annotatedItem.item);
        if (index + 1 < items.size()) {
            annotatedItem.nextItemId = items.get(index + 1).getItemId();
        }
        return annotatedItem;
    }
}
