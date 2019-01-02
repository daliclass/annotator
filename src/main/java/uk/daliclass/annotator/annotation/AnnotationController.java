package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.domain.AnnotatorView;
import uk.daliclass.annotator.common.domain.Idable;
import uk.daliclass.annotator.annotation.get.AnnotatedItem;
import uk.daliclass.annotator.annotation.get.GetItemForAnnotation;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemFact;

import java.util.List;
import java.util.function.Function;

public class AnnotationController<T extends Idable> {

    private final GetItemForAnnotation getSubjectToAnnotation;
    private final MapAnnotationToAnnotatorView mapAnnotationToAnnotatorView;
    private final AnnotateItem annotateItem;
    private final AddItemsToAnnotate<T> addItemsToAnnotate;
    private final GetFactsForItem getFactsForItem;

    public AnnotationController(GetItemForAnnotation getItemForAnnotation,
                                AnnotateItem annotateItem,
                                AddItemsToAnnotate<T> addItemsToAnnotate, GetFactsForItem getFactsForItem) {
        this.getSubjectToAnnotation = getItemForAnnotation;
        this.mapAnnotationToAnnotatorView = new MapAnnotationToAnnotatorView();
        this.annotateItem = annotateItem;
        this.addItemsToAnnotate = addItemsToAnnotate;
        this.getFactsForItem = getFactsForItem;
    }

    public AnnotatorView<T> getItemToAnnotate(String username, Integer subjectId) {
        AnnotatedItem annotatedItem = getSubjectToAnnotation.apply(username, subjectId);
        return mapAnnotationToAnnotatorView.apply(annotatedItem);
    }

    public void addAnnotationsToItem(ItemAnnotation itemAnnotation) {
        annotateItem.accept(itemAnnotation);
    }

    public void addItemsToAnnotate(List<T> items) {
        addItemsToAnnotate.accept(items);
    }

    public List<ItemFact> getFactsForItem(Integer itemId) {
        return this.getFactsForItem.apply(itemId);
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
}
