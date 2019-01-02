package uk.daliclass.annotator.common.domain;

public class Fact {

    private Predicate predicate;
    private Object object;

    public Fact(Predicate predicate, Object object) {
        this.predicate = predicate;
        this.object = object;
    }

    public Fact() {

    }

    public Object getObject() {
        return object;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public enum Predicate {
        IS_A, SUITABLE_FOR
    }

    public enum Object {
        MEN, FEMALE, ELECTRONIC, SPORT
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (!(o instanceof Fact)) return false;
        Fact other = (Fact) o;
        if (this.object != other.object) return false;
        if (this.predicate != other.predicate) return false;
        return true;
    }
}
