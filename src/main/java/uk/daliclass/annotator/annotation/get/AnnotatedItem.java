package uk.daliclass.annotator.annotation.get;

import uk.daliclass.annotator.common.Fact;

import java.util.List;

public class AnnotatedItem<T> {
    public T item;
    public List<Fact> potentialFacts;
    public List<Fact> itemFacts;
    public Integer nextItemId;
}
