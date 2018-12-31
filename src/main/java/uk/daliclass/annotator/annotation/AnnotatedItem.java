package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.Fact;

import java.util.List;

public class AnnotatedItem<T> {
    T item;
    List<Fact> potentialFacts;
    List<Fact> itemFacts;
    Integer nextItemId;
}
