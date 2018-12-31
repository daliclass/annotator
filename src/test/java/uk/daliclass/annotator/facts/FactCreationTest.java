package uk.daliclass.annotator.facts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import uk.daliclass.annotator.common.Fact;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class FactCreationTest {

    @Mock
    Log<Fact> log;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenCreatingANewFactThenSaveItInTheStore() {
        Fact fact = new Fact(Fact.Predicate.IS_A, Fact.Object.MEN);
        when(log.create(fact)).thenReturn(Boolean.TRUE);
        FactCreation factCreation = new FactCreation(log);
        CreationStatus actualCreationStatus = factCreation.apply(fact);
        assertEquals(CreationStatus.OK, actualCreationStatus);
        verify(log, times(1)).create(fact);
    }

    @Test
    public void whenCreatingANewFactAndTheStoreFailsToCreateTheFactThenCreationFailed() {
        Fact fact = new Fact(Fact.Predicate.IS_A, Fact.Object.MEN);
        when(log.create(fact)).thenReturn(Boolean.FALSE);
        FactCreation factCreation = new FactCreation(log);
        CreationStatus actualCreationStatus = factCreation.apply(fact);
        assertEquals(CreationStatus.FACT_CREATION_FAILED, actualCreationStatus);
        verify(log, times(1)).create(fact);
    }

    @Test
    public void whenCreatingADuplicateFactThenDoNotSaveItInTheStore() {
        Fact fact = new Fact(Fact.Predicate.IS_A, Fact.Object.MEN);
        List<Fact> facts = new ArrayList() {{
            add(new Fact(Fact.Predicate.IS_A, Fact.Object.MEN));
            add(new Fact(Fact.Predicate.IS_A, Fact.Object.ELECTRONIC));
        }};
        when(log.read()).thenReturn(facts);
        FactCreation factCreation = new FactCreation(log);
        CreationStatus actualCreationStatus = factCreation.apply(fact);
        assertEquals(CreationStatus.FACT_ALREADY_EXISTS, actualCreationStatus);
        verify(log, times(0)).create(ArgumentMatchers.any());
    }
}
