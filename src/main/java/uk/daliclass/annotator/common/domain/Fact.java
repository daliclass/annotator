package uk.daliclass.annotator.common.domain;

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

    @Override
    public boolean equals(java.lang.Object o) {
        if (!(o instanceof Fact)) return false;
        Fact other = (Fact) o;
        if (!this.object.equals(other.object)) return false;
        if (!this.predicate.equals(other.predicate)) return false;
        return true;
    }
}
