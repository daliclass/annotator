package uk.daliclass.annotator.common.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Fact {

    private String predicate;
    private String object;
    private Integer id;

    public Fact(String predicate, String object) {
        this.predicate = predicate;
        this.object = object;
    }

    public Fact(String predicate, String object, Integer id) {
        this(predicate, object);
        this.id = id;
    }

    public Fact() {

    }

    public String getObject() {
        return object;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
