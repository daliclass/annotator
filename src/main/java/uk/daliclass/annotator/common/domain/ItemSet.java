package uk.daliclass.annotator.common.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
public class ItemSet<T extends Idable> {
    private String name;
    private UUID uuid;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    private List<T> items;
    private List<Fact> facts;

    public ItemSet() {
    }

    public ItemSet(String name, List<T> items) {
        this.name = name;
        this.items = items;
        this.uuid = UUID.randomUUID();
        this.facts = new ArrayList<>();
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setSetId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }
}
