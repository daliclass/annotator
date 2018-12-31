package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.annotation.annotate.AnnotateItem;
import uk.daliclass.annotator.annotation.annotate.ItemAnnotation;
import uk.daliclass.annotator.annotation.get.AnnotatedItem;
import uk.daliclass.annotator.annotation.get.GetItemForAnnotation;

import java.util.function.Function;

public class AnnotationController<T> {

    private final GetItemForAnnotation getSubjectToAnnotation;
    private final MapAnnotationToAnnotatorView mapAnnotationToAnnotatorView;
    private final AnnotateItem annotateItem;

    public AnnotationController(GetItemForAnnotation getItemForAnnotation, AnnotateItem annotateItem) {
        this.getSubjectToAnnotation = getItemForAnnotation;
        this.mapAnnotationToAnnotatorView = new MapAnnotationToAnnotatorView();
        this.annotateItem = annotateItem;
    }

    public AnnotatorView<T> getSubjectToAnnotate(String username, Integer subjectId) {
        AnnotatedItem annotatedItem = getSubjectToAnnotation.apply(username, subjectId);
        return mapAnnotationToAnnotatorView.apply(annotatedItem);
    }

    public void addAnnotationsToItem(ItemAnnotation itemAnnotation) {
        this.annotateItem.accept(itemAnnotation);
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
