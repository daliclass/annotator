package uk.daliclass.annotator.annotation.get;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.annotation.ItemFact;
import uk.daliclass.annotator.common.Fact;
import uk.daliclass.annotator.common.items.Product;
import uk.daliclass.annotator.facts.Log;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetProductForAnnotatedItemTest {

    private static final String USERNAME = "USERNAME";
    private static final Integer SUBJECT_ID = 1;
    private static final List<Fact> FACTS = new ArrayList<Fact>() {{
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN));
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.FEMALE));
    }};
    private static final List<ItemFact> ITEM_FACTS = new ArrayList<ItemFact>() {{
        add(new ItemFact(1, new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN), USERNAME));
        add(new ItemFact(1, new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.FEMALE), USERNAME));
        add(new ItemFact(2, new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.FEMALE), USERNAME));
    }};
    private static final List<Fact> EXPECTED_ITEM_FACTS = new ArrayList<Fact>() {{
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN));
        add(new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.FEMALE));
    }};
    private static final List<Product> PRODUCTS = new ArrayList<Product>() {{
        add(new Product(1, "Toaster", "A green toaster", 20.0, "image.url"));
        add(new Product(2, "Microwave", "A green Microwave", 30.0, "image.url"));
    }};

    @Mock
    Log<Fact> factLog;

    @Mock
    Log<ItemFact> itemFactLog;

    @Mock
    Log<Product> productLog;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedASubjectAndAUsernameThenPopulateThePotentialFacts() {
        when(factLog.read()).thenReturn(FACTS);
        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(factLog, itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(USERNAME, SUBJECT_ID);
        assertEquals(FACTS, productAnnotatedItem.potentialFacts);
    }

    @Test
    public void whenProvidedASubjectAndAUsernameThenPopulateTheProductFacts() {
        when(itemFactLog.read()).thenReturn(ITEM_FACTS);
        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(factLog, itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(USERNAME, SUBJECT_ID);
        assertEquals(EXPECTED_ITEM_FACTS, productAnnotatedItem.itemFacts);
    }

    @Test
    public void whenProvidedASubjectAndAUsernameThenPopulateProductAnnotation() {
        when(productLog.read()).thenReturn(PRODUCTS);
        Product expectedProduct =
                new Product(1, "Toaster", "A green toaster", 20.0, "image.url");
        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(factLog, itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(USERNAME, SUBJECT_ID);
        assertEquals(expectedProduct, productAnnotatedItem.item);
    }

    @Test
    public void whenProvidedASubjectAndAUsernameThenNextItem() {
        when(productLog.read()).thenReturn(PRODUCTS);
        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(factLog, itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(USERNAME, SUBJECT_ID);
        assertEquals(Integer.valueOf(2), productAnnotatedItem.nextItemId);
    }

    @Test
    public void whenProvidedASubjectAndAUsernameAndItsTheLastItemThenDoNotPopulateNextItemId() {
        when(productLog.read()).thenReturn(PRODUCTS);
        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(factLog, itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(USERNAME, 2);
        assertEquals(null, productAnnotatedItem.nextItemId);
    }
}
