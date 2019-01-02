package uk.daliclass.annotator.common.domain;

import java.util.List;

public class AnnotatorView<X extends Idable> {
    private X item;
    private List<Fact> potentialFacts;
    private List<Fact> productFacts;
    private Integer nextSubjectId;

    public AnnotatorView() {

    }

    public List<Fact> getPotentialFacts() {
        return potentialFacts;
    }

    public Integer getNextItemId() {
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
