package uk.daliclass.annotator.annotation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.common.Fact;
import uk.daliclass.annotator.common.items.Product;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnnotatedItemControllerTest {

    private static final String USERNAME = "USERNAME";
    private static final Integer SUBJECT_ID = 1;
    private static final List<Fact> POTENTIAL_FACTS = new ArrayList() {{
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN));
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.FEMALE));
    }};
    private static final List<Fact> ACTUAL_FACTS = new ArrayList() {{
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN));
    }};

    @Mock
    GetItemForAnnotation<Product> getItemForAnnotation;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedAUsernameAndASubjectIdThenPopulatePotentialObjectsAndPredicates() {
        AnnotationController<Product> productAnnotationController = new AnnotationController<>(getItemForAnnotation);
        AnnotatedItem<Product> productAnnotatedItem = new AnnotatedItem<>();
        productAnnotatedItem.potentialFacts = POTENTIAL_FACTS;
        productAnnotatedItem.itemFacts = ACTUAL_FACTS;
        when(getItemForAnnotation.apply(USERNAME, SUBJECT_ID)).thenReturn(productAnnotatedItem);
        AnnotationController<Product>.AnnotatorView<Product> annotatorView =
                productAnnotationController.getSubjectToAnnotate(USERNAME, SUBJECT_ID);
        assertTrue(annotatorView.getProductFacts().containsAll(ACTUAL_FACTS));
        assertTrue(annotatorView.getPotentialFacts().containsAll(POTENTIAL_FACTS));
    }

    @Test
    public void whenProvidedAUsernameAndASubjectIdThenPopulateNextSubjectId() {
        AnnotationController<Product> productAnnotationController = new AnnotationController<>(getItemForAnnotation);
        AnnotatedItem<Product> productAnnotatedItem = new AnnotatedItem<>();

        productAnnotatedItem.nextItemId = 1;

        when(getItemForAnnotation.apply(USERNAME, SUBJECT_ID)).thenReturn(productAnnotatedItem);
        AnnotationController<Product>.AnnotatorView<Product> annotatorView =
                productAnnotationController.getSubjectToAnnotate(USERNAME, SUBJECT_ID);

        assertEquals(Integer.valueOf(1), annotatorView.getNextSubjectId());
    }

    @Test
    public void whenProvidedAUsernameAndSubjectIdThenPopulateProduct() {
        AnnotationController<Product> productAnnotationController = new AnnotationController<>(getItemForAnnotation);
        AnnotatedItem<Product> productAnnotatedItem = new AnnotatedItem<>();
        Product productItem = new Product();
        productAnnotatedItem.item = productItem;

        when(getItemForAnnotation.apply(USERNAME, SUBJECT_ID)).thenReturn(productAnnotatedItem);
        AnnotationController<Product>.AnnotatorView<Product> annotatorView =
                productAnnotationController.getSubjectToAnnotate(USERNAME, SUBJECT_ID);

        assertEquals(productItem, annotatorView.getItem());
    }
}
