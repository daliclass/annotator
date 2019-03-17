package uk.daliclass.annotator.common.domain;

import java.util.List;
import java.util.UUID;

public class ItemAnnotation {
    private List<Fact> itemFacts;
    private Integer itemId;
    private String annotatorName;
    private UUID itemSetUuid;

    public ItemAnnotation() {
    }

    public ItemAnnotation(List<Fact> itemFacts, Integer itemId, String annotatorName, UUID itemSetUuid) {
        this.itemFacts = itemFacts;
        this.itemId = itemId;
        this.annotatorName = annotatorName;
        this.itemSetUuid = itemSetUuid;
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

    public UUID getItemSetUuid() {
        return itemSetUuid;
    }

    public void setItemSetUuid(UUID itemSetUuid) {
        this.itemSetUuid = itemSetUuid;
    }
}
