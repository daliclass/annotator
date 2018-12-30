package uk.daliclass.annotator.facts;

import uk.daliclass.annotator.facts.domain.Fact;

import java.util.List;

public interface FactStore {
    Boolean create(Fact fact);
    List<Fact> read();
}
