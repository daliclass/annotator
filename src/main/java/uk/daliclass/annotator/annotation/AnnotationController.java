package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.annotation.get.AnnotatedItem;
import uk.daliclass.annotator.annotation.get.GetItemForAnnotation;
import uk.daliclass.annotator.common.domain.Idable;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.domain.views.AnnotatorView;
import uk.daliclass.annotator.common.domain.views.ItemSetView;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnnotationController<T extends Idable> {

    private final GetItemForAnnotation getItemForAnnotation;
    private final MapAnnotationToAnnotatorView mapAnnotationToAnnotatorView;
    private final AnnotateItem annotateItem;
    private final GetFactsForItem getFactsForItem;
    private final GetItemSets<T> getItemSets;
    private final MapItemSetToView mapItemSetToView;
    private final AddItemsToAnnotate<T> addItemsToAnnotate;

    public AnnotationController(GetItemForAnnotation getItemForAnnotation,
                                AnnotateItem annotateItem,
                                GetFactsForItem getFactsForItem,
                                GetItemSets<T> getItemSets,
                                AddItemsToAnnotate<T> addItemsToAnnotate) {
        this.getItemForAnnotation = getItemForAnnotation;
        this.mapAnnotationToAnnotatorView = new MapAnnotationToAnnotatorView();
        this.annotateItem = annotateItem;
        this.getFactsForItem = getFactsForItem;
        this.mapItemSetToView = new MapItemSetToView();
        this.getItemSets = getItemSets;
        this.addItemsToAnnotate = addItemsToAnnotate;
    }

    public AnnotatorView<T> getItemToAnnotate(ItemToAnnotateRequest itemToAnnotateRequest) {
        AnnotatedItem annotatedItem = getItemForAnnotation.apply(itemToAnnotateRequest);
        return mapAnnotationToAnnotatorView.apply(annotatedItem);
    }

    public void addAnnotationsToItem(ItemAnnotation itemAnnotation) {
        annotateItem.accept(itemAnnotation);
    }

    public List<ItemFact> getFactsForItem(Integer itemId, UUID itemSetUuid) {
        return this.getFactsForItem.apply(itemId, itemSetUuid);
    }

    public List<ItemSetView> getItemSetViews() {
        return getItemSets.get().stream()
                .map(mapItemSetToView::apply)
                .collect(Collectors.toList());
    }

    public void addItemsToAnnotate(ItemSet<T> itemSet) {
        addItemsToAnnotate.accept(itemSet);
    }

    class MapAnnotationToAnnotatorView implements Function<AnnotatedItem<T>, AnnotatorView<T>> {

        @Override
        public AnnotatorView<T> apply(AnnotatedItem<T> annotatedItem) {
            AnnotatorView<T> annotatorView = new AnnotatorView<>();
            annotatorView.setPotentialFacts(annotatedItem.potentialFacts);
            annotatorView.setProductFacts(annotatedItem.itemFacts);
            annotatorView.setNextSubjectId(annotatedItem.nextItemId);
            annotatorView.setItem(annotatedItem.item);
            return annotatorView;
        }
    }

    class MapItemSetToView implements Function<ItemSet, ItemSetView> {

        @Override
        public ItemSetView apply(ItemSet itemSet) {
            return new ItemSetView(itemSet.getName(), itemSet.getUuid(), itemSet.getItems().size());
        }
    }
}
