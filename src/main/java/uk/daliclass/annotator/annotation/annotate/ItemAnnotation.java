package uk.daliclass.annotator.annotation.annotate;

import uk.daliclass.annotator.common.Fact;

import java.util.List;

public class ItemAnnotation {
    private List<Fact> itemFacts;
    private Integer itemId;
    private String annotatorName;

    public ItemAnnotation() {}

    public ItemAnnotation(List<Fact> itemFacts, Integer itemId, String annotatorName) {
        this.itemFacts = itemFacts;
        this.itemId = itemId;
        this.annotatorName = annotatorName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public List<Fact> getItemFacts() {
        return itemFacts;
    }

    public void setItemFacts(List<Fact> itemFacts) {
        this.itemFacts = itemFacts;
    }

    public String getAnnotatorName() {
        return annotatorName;
    }

    public void setAnnotatorName(String annotatorName) {
        this.annotatorName = annotatorName;
    }
}
