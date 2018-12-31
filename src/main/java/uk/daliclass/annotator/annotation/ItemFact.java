package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.Fact;

public class ItemFact {
    private Integer itemId;
    private Fact fact;

    public ItemFact(Integer itemId, Fact fact) {
        this.itemId = itemId;
        this.fact = fact;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }
}
