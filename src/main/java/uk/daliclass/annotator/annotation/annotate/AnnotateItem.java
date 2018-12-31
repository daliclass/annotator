package uk.daliclass.annotator.annotation.annotate;

import uk.daliclass.annotator.annotation.ItemFact;
import uk.daliclass.annotator.common.Fact;
import uk.daliclass.annotator.facts.Log;

import java.util.function.Consumer;

public class AnnotateItem implements Consumer<ItemAnnotation> {

    private final Log<ItemFact> itemFactLog;

    public AnnotateItem(Log<ItemFact> itemFactLog) {
        this.itemFactLog = itemFactLog;
    }

    @Override
    public void accept(ItemAnnotation itemAnnotation) {
        for (Fact fact: itemAnnotation.getItemFacts()) {
            itemFactLog.create(new ItemFact(itemAnnotation.getItemId(), fact, itemAnnotation.getAnnotatorName()));
        }
    }
}
