package uk.daliclass.annotator.common.domain;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public class ItemFact {
    private UUID uuid;
    private String annotatorName;
    private Integer itemId;
    private Fact fact;

    public ItemFact(Integer itemId, Fact fact, String annotatorName, UUID uuid) {
        this.itemId = itemId;
        this.fact = fact;
        this.annotatorName = annotatorName;
        this.uuid = uuid;
    }

    public ItemFact() {

    }

    public String getAnnotatorName() {
        return annotatorName;
    }

    public void setAnnotatorName(String annotatorName) {
        this.annotatorName = annotatorName;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
