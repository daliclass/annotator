package uk.daliclass.annotator.annotation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.storage.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class AnnotateItemTest {

    private static final List<Fact> ACTUAL_FACTS = new ArrayList<>() {{
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN));
    }};
    private static final Integer ITEM_ID = 1;
    private static final String ANNOTATOR_NAME = "name";

    @Mock
    Log<ItemFact> itemFactLog;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenAnnotatingAItemThenStoreAnnotationInAnnotationLog() {
        ItemAnnotation itemAnnotation = new ItemAnnotation(ACTUAL_FACTS, ITEM_ID, ANNOTATOR_NAME);
        AnnotateItem annotateItem = new AnnotateItem(itemFactLog);
        annotateItem.accept(itemAnnotation);
        List<ItemFact> expectedItemFacts = ACTUAL_FACTS.stream()
                .map(actualFact -> new ItemFact(ITEM_ID, actualFact, ANNOTATOR_NAME))
                .collect(Collectors.toList());
        for (ItemFact itemFact: expectedItemFacts) {
            verify(itemFactLog, times(1)).create(itemFact);
        }
    }
}
