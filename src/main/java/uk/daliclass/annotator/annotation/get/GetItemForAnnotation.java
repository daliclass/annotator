package uk.daliclass.annotator.annotation.get;

import uk.daliclass.annotator.common.domain.Idable;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.storage.Log;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetItemForAnnotation<T extends Idable> implements Function<ItemToAnnotateRequest, AnnotatedItem<T>> {

    private final Log<ItemFact> itemFactLog;
    private final Log<ItemSet<T>> itemLog;

    public GetItemForAnnotation(Log<ItemFact> itemFactLog, Log<ItemSet<T>> itemLog) {
        this.itemFactLog = itemFactLog;
        this.itemLog = itemLog;
    }

    @Override
    public AnnotatedItem<T> apply(ItemToAnnotateRequest itemToAnnotateRequest) {
        List<ItemSet<T>> items = itemLog.read();

        ItemSet<T> requestedItemSet = items.stream()
                .filter(itemSet -> itemSet.getUuid().equals(itemToAnnotateRequest.getUuid()))
                .findFirst()
                .get();

        List<ItemFact> itemFacts = itemFactLog.read();
        AnnotatedItem<T> annotatedItem = new AnnotatedItem<>();

        annotatedItem.potentialFacts = requestedItemSet.getFacts();

        annotatedItem.itemFacts = itemFacts.stream()
                .filter(itemFact -> itemFact.getItemId() == itemToAnnotateRequest.getItemId())
                .map(ItemFact::getFact)
                .collect(Collectors.toList());

        Optional<T> productOptional =
                requestedItemSet.getItems().stream()
                        .filter(item -> item.getItemId() == itemToAnnotateRequest.getItemId())
                        .findFirst();

        annotatedItem.item = productOptional.orElse(requestedItemSet.getItems().get(0));


        if (requestedItemSet.getItems().indexOf(annotatedItem.item) < requestedItemSet.getItems().size() - 1) {
            annotatedItem.nextItemId = requestedItemSet.getItems().get(requestedItemSet.getItems().indexOf(annotatedItem.item) + 1).getItemId();
        }
        return annotatedItem;
    }
}
