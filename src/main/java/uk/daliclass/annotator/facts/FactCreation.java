package uk.daliclass.annotator.facts;

import uk.daliclass.annotator.facts.domain.Fact;

import java.util.List;
import java.util.function.Function;

public class FactCreation implements Function<Fact, CreationStatus> {


    private final FactStore factStore;

    public FactCreation(FactStore factStore) {
        this.factStore = factStore;
    }

    public CreationStatus apply(Fact fact) {
        List<Fact> storedFacts = factStore.read();
        if (storedFacts.contains(fact)) {
           return CreationStatus.FACT_ALREADY_EXISTS;
        }
        return createFact(fact);
    }

    private CreationStatus createFact(Fact fact) {
        Boolean factCreated = factStore.create(fact);
        return factCreated ? CreationStatus.OK : CreationStatus.FACT_CREATION_FAILED;
    }
}
