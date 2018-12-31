package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.Fact;

import java.util.List;
import java.util.function.Function;

public class AnnotationController<T> {

    private final GetItemForAnnotation getSubjectToAnnotation;
    private final MapAnnotationToAnnotatorView mapAnnotationToAnnotatorView;

    public AnnotationController(GetItemForAnnotation getItemForAnnotation) {
        this.getSubjectToAnnotation = getItemForAnnotation;
        this.mapAnnotationToAnnotatorView = new MapAnnotationToAnnotatorView();
    }

    public AnnotatorView<T> getSubjectToAnnotate(String username, Integer subjectId) {
        AnnotatedItem annotatedItem = getSubjectToAnnotation.apply(username, subjectId);
        return mapAnnotationToAnnotatorView.apply(annotatedItem);
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

    public class AnnotatorView<X> {
        private X item;
        private List<Fact> potentialFacts;
        private List<Fact> productFacts;
        private Integer nextSubjectId;

        public AnnotatorView() {

        }

        public List<Fact> getPotentialFacts() {
            return potentialFacts;
        }

        public Integer getNextSubjectId() {
            return nextSubjectId;
        }

        public X getItem() {
            return item;
        }

        public void setNextSubjectId(Integer nextSubjectId) {
            this.nextSubjectId = nextSubjectId;
        }

        public void setPotentialFacts(List<Fact> potentialFacts) {
            this.potentialFacts = potentialFacts;
        }

        public void setProductFacts(List<Fact> productFacts) {
            this.productFacts = productFacts;
        }

        public void setItem(X item) {
            this.item = item;
        }

        public List<Fact> getProductFacts() {
            return productFacts;
        }
    }
}
