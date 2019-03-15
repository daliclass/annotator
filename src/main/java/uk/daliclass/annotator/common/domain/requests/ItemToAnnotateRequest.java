package uk.daliclass.annotator.common.domain.requests;

import java.util.UUID;

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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ItemToAnnotateRequest)) return false;
        ItemToAnnotateRequest request = (ItemToAnnotateRequest) other;
        if (this.uuid != request.uuid) return false;
        if (this.username != request.username) return false;
        if (this.itemId != ((ItemToAnnotateRequest) other).itemId) return false;
        return true;
    }
}
