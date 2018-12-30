package uk.daliclass.annotator.facts;

import uk.daliclass.annotator.facts.domain.Fact;

import java.util.function.Function;

public class FactController {

    private final Function<Fact, CreationStatus> createFact;

    public FactController(Function<Fact, CreationStatus> createFact) {
        this.createFact = createFact;
    }

    public CreationStatus createFact(String predicate, String object) {
        Fact.Predicate factPredicate;
        Fact.Object factObject;
        try {
            factPredicate = Fact.Predicate.valueOf(predicate);
            factObject = Fact.Object.valueOf(object);
        } catch (IllegalArgumentException illegalArgumentException) {
            return CreationStatus.INVALID_INPUT;
        }
        Fact fact = new Fact(factPredicate, factObject);
        return createFact.apply(fact);
    }

}
