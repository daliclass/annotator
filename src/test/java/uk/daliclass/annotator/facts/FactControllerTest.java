package uk.daliclass.annotator.facts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.common.Fact;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FactControllerTest {

    @Mock
    Function<Fact, CreationStatus> createFact;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenCreatingAFactWithAValidPredicateAndObjectThenItShouldReturnOK() {
        when(createFact.apply(any())).thenReturn(CreationStatus.OK);
        FactController factController = new FactController(createFact);
        String predicate = "SUITABLE_FOR";
        String object = "MEN";
        CreationStatus actualCreationStatus = factController.createFact(predicate, object);
        assertEquals(CreationStatus.OK, actualCreationStatus);
    }

    @Test
    public void whenCreatingAFactWithAInvalidPredicateAndObjectThenItShouldReturnInvalidInput() {
        when(createFact.apply(any())).thenReturn(CreationStatus.OK);
        FactController factController = new FactController(createFact);
        String predicate = "NOT_SUITABLE_FOR";
        String object = "WOMEN";
        CreationStatus actualCreationStatus = factController.createFact(predicate, object);
        assertEquals(CreationStatus.INVALID_INPUT, actualCreationStatus);
    }

    @Test
    public void whenCreatingAFactWithAInvalidPredicateAndObjectThenItShouldReturnFactCreateFailed() {
        when(createFact.apply(any())).thenReturn(CreationStatus.FACT_CREATION_FAILED);
        FactController factController = new FactController(createFact);
        String predicate = "SUITABLE_FOR";
        String object = "MEN";
        CreationStatus actualCreationStatus = factController.createFact(predicate, object);
        assertEquals(CreationStatus.FACT_CREATION_FAILED, actualCreationStatus);
    }
}
