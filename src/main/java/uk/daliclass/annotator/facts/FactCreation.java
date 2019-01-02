package uk.daliclass.annotator.facts;

import uk.daliclass.annotator.common.domain.CreationStatus;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.storage.Log;

import java.util.List;
import java.util.function.Function;

public class FactCreation implements Function<Fact, CreationStatus> {


    private final Log log;

    public FactCreation(Log log) {
        this.log = log;
    }

    public CreationStatus apply(Fact fact) {
        List<Fact> storedFacts = log.read();
        if (storedFacts.contains(fact)) {
            return CreationStatus.FACT_ALREADY_EXISTS;
        }
        return createFact(fact);
    }

    private CreationStatus createFact(Fact fact) {
        Boolean factCreated = log.create(fact);
        return factCreated ? CreationStatus.OK : CreationStatus.FACT_CREATION_FAILED;
    }
}
