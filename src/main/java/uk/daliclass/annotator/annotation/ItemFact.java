package uk.daliclass.annotator.annotation;

import uk.daliclass.annotator.common.Fact;

public class ItemFact {
    private Integer itemId;
    private Fact fact;

    public ItemFact(Integer itemId, Fact fact, String annotatorName) {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ItemFact)) return false;
        ItemFact other = (ItemFact) o;
        if (this.itemId != other.itemId) return false;
        if (!this.fact.equals(other.fact)) return false;
        return true;
    }
}
