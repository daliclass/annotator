package uk.daliclass.annotator.common.domain.views;

import java.util.UUID;

public class ItemSetView {
    private String name;
    private UUID uuid;
    private Integer numberOfItems;

    public ItemSetView(String name, UUID uuid, Integer numberOfItems) {
        this.name = name;
        this.uuid = uuid;
        this.numberOfItems = numberOfItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ItemSetView)) return false;
        ItemSetView other = (ItemSetView) o;
        if (!this.name.equals(other.name)) return false;
        if (!this.uuid.equals(other.uuid)) return false;
        if (this.numberOfItems != other.numberOfItems) return false;
        return true;
    }
}
