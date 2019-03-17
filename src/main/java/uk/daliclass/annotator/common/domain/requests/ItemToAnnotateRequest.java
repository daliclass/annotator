package uk.daliclass.annotator.common.domain.requests;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public class ItemToAnnotateRequest {
    private UUID uuid;
    private String username;
    private Integer itemId;

    public ItemToAnnotateRequest(UUID uuid, String username, Integer itemId) {
        this.uuid = uuid;
        this.username = username;
        this.itemId = itemId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getItemId() {
        return itemId;
    }
}
