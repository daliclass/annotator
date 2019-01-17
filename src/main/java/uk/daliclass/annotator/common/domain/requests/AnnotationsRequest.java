package uk.daliclass.annotator.common.domain.requests;

import java.util.UUID;

public class AnnotationsRequest {
    private Integer itemId;
    private UUID itemSetUuid;

    public AnnotationsRequest(Integer itemId, UUID itemSetUuid) {
        this.itemId = itemId;
        this.itemSetUuid = itemSetUuid;
    }

    public UUID getItemSetUuid() {
        return itemSetUuid;
    }

    public void setItemSetUuid(UUID itemSetUuid) {
        this.itemSetUuid = itemSetUuid;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
