package uk.daliclass.annotator.annotation.get;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.storage.Log;
import uk.daliclass.product.common.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetProductForAnnotatedItemTest {
    private static final String SUITABLE_FOR = "SUITABLE_FOR";
    private static final String MEN = "MEN";
    private static final String FEMALE = "FEMALE";
    private static final String USERNAME = "USERNAME";
    private static final Integer SUBJECT_ID = 5;
    private static final UUID SET_ID = UUID.fromString("1f0bb0dd-69a2-4c46-a080-c5569259c1e5");
    private static final UUID SET_ID2 = UUID.fromString("1f0bb0dd-69a2-4c46-a080-c5569259c1e6");
    private static final List<Fact> FACTS = new ArrayList<Fact>() {{
        add(new Fact(SUITABLE_FOR, MEN));
        add(new Fact(SUITABLE_FOR, FEMALE));
    }};
    private static final List<ItemFact> ITEM_FACTS = new ArrayList<ItemFact>() {{
        add(new ItemFact(5, new Fact(SUITABLE_FOR, MEN), USERNAME));
        add(new ItemFact(5, new Fact(SUITABLE_FOR, FEMALE), USERNAME));
        add(new ItemFact(10, new Fact(SUITABLE_FOR, FEMALE), USERNAME));
    }};
    private static final List<Fact> EXPECTED_ITEM_FACTS = new ArrayList<Fact>() {{
        add(new Fact(SUITABLE_FOR, MEN));
        add(new Fact(SUITABLE_FOR, FEMALE));
    }};
    private static final List<Product> PRODUCTS = new ArrayList<Product>() {{
        add(new Product(5, "Microwave", "A green Microwave", 30.0, "image.url"));
        add(new Product(10, "Toaster", "A green toaster", 20.0, "image.url"));
    }};

    @Mock
    Log<ItemFact> itemFactLog;

    @Mock
    Log<ItemSet<Product>> productLog;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedARequestThenPopulateThePotentialFacts() {
        mockItemSets();

        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(
                new ItemToAnnotateRequest(SET_ID2, USERNAME, SUBJECT_ID)
        );
        assertEquals(FACTS, productAnnotatedItem.potentialFacts);
    }

    @Test
    public void whenRequestThenPopulateTheProductFacts() {
        mockItemSets();
        when(itemFactLog.read()).thenReturn(ITEM_FACTS);

        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(
                new ItemToAnnotateRequest(SET_ID, USERNAME, SUBJECT_ID));
        assertEquals(EXPECTED_ITEM_FACTS, productAnnotatedItem.itemFacts);
    }

    @Test
    public void whenRequestThenPopulateProductAnnotation() {
        mockItemSets();

        Product expectedProduct =
                new Product(10, "Toaster", "A green toaster", 20.0, "image.url");
        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(
                new ItemToAnnotateRequest(SET_ID2, USERNAME, SUBJECT_ID));
        assertEquals(expectedProduct, productAnnotatedItem.item);
    }

    @Test
    public void whenProvidedARequestThenPopulateNextItem() {
        mockItemSets();

        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(
                new ItemToAnnotateRequest(SET_ID, USERNAME, 5));
        assertEquals(Integer.valueOf(10), productAnnotatedItem.nextItemId);
    }

    @Test
    public void whenProvidedARequestAndItsTheLastItemThenDoNotPopulateNextItemId() {
        mockItemSets();

        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(
                new ItemToAnnotateRequest(SET_ID2, USERNAME, SUBJECT_ID));
        assertEquals(null, productAnnotatedItem.nextItemId);
    }

    @Test
    public void whenProvidedANullItemIdThenProvideFirstItemId() {
        mockItemSets();
        GetItemForAnnotation<Product> getItemForAnnotation = new GetItemForAnnotation<>(itemFactLog, productLog);
        AnnotatedItem<Product> productAnnotatedItem = getItemForAnnotation.apply(
                new ItemToAnnotateRequest(SET_ID, USERNAME, null));
        assertEquals(Integer.valueOf(5), productAnnotatedItem.item.getItemId());

    }

    public void mockItemSets() {
        ItemSet<Product> itemSet = new ItemSet<>("set 1", PRODUCTS);
        itemSet.setSetId(SET_ID);
        itemSet.setFacts(FACTS);
        List<Product> productCopy = new ArrayList<>(PRODUCTS);
        productCopy.remove(0);
        ItemSet<Product> itemSetTwo = new ItemSet<>("set 1", productCopy);
        itemSetTwo.setSetId(SET_ID2);
        itemSetTwo.setFacts(FACTS);
        List<ItemSet<Product>> itemSets = new ArrayList<>() {{
            add(itemSet);
            add(itemSetTwo);
        }};
        when(productLog.read()).thenReturn(itemSets);
    }

}
