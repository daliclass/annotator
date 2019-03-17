package uk.daliclass.annotator.annotation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.annotation.get.AnnotatedItem;
import uk.daliclass.annotator.annotation.get.GetItemForAnnotation;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.domain.views.AnnotatorView;
import uk.daliclass.annotator.common.domain.views.ItemSetView;
import uk.daliclass.product.common.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnnotatedItemControllerTest {

    private static final String USERNAME = "USERNAME";
    private static final Integer SUBJECT_ID = 1;
    private static final String SUITABLE_FOR = "SUITABLE_FOR";
    private static final String MEN = "MEN";
    private static final String FEMALE = "FEMALE";
    private static final String IS_A = "IS_A";
    private static final String SPORT = "SPORT";
    private static final UUID uuid = UUID.randomUUID();
    private static final List<Fact> POTENTIAL_FACTS = new ArrayList<>() {{
        add(new Fact(SUITABLE_FOR, MEN));
        add(new Fact(SUITABLE_FOR, FEMALE));
    }};
    private static final List<Fact> ACTUAL_FACTS = new ArrayList<>() {{
        add(new Fact(SUITABLE_FOR, MEN));
    }};

    @Mock
    GetItemForAnnotation<Product> getItemForAnnotation;

    @Mock
    AnnotateItem annotateItem;

    @Mock
    AddItemsToAnnotate<Product> addItemsToAnnotate;

    @Mock
    GetFactsForItem getFactsForItem;

    @Mock
    GetItemSets<Product> getItemSets;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedARequestThenPopulatePotentialObjectsAndPredicates() {
        AnnotationController<Product> productAnnotationController = createAnnotationController();
        AnnotatedItem<Product> productAnnotatedItem = new AnnotatedItem<>();
        productAnnotatedItem.potentialFacts = POTENTIAL_FACTS;
        productAnnotatedItem.itemFacts = ACTUAL_FACTS;
        when(getItemForAnnotation.apply(new ItemToAnnotateRequest(uuid, USERNAME, SUBJECT_ID))).thenReturn(productAnnotatedItem);
        AnnotatorView<Product> annotatorView =
                productAnnotationController.getItemToAnnotate(new ItemToAnnotateRequest(uuid, USERNAME, SUBJECT_ID));
        assertTrue(annotatorView.getProductFacts().containsAll(ACTUAL_FACTS));
        assertTrue(annotatorView.getPotentialFacts().containsAll(POTENTIAL_FACTS));
    }

    @Test
    public void whenProvidedAUsernameAndARequestThenPopulateNextSubjectId() {
        AnnotationController<Product> productAnnotationController = createAnnotationController();
        AnnotatedItem<Product> productAnnotatedItem = new AnnotatedItem<>();

        productAnnotatedItem.nextItemId = 1;

        when(getItemForAnnotation.apply(new ItemToAnnotateRequest(uuid, USERNAME, SUBJECT_ID))).thenReturn(productAnnotatedItem);
        AnnotatorView<Product> annotatorView =
                productAnnotationController.getItemToAnnotate(new ItemToAnnotateRequest(uuid, USERNAME, SUBJECT_ID));

        assertEquals(Integer.valueOf(1), annotatorView.getNextItemId());
    }

    @Test
    public void whenProvidedARequestThenPopulateProduct() {
        AnnotationController<Product> productAnnotationController = createAnnotationController();
        AnnotatedItem<Product> productAnnotatedItem = new AnnotatedItem<>();
        Product productItem = new Product();
        productAnnotatedItem.item = productItem;

        when(getItemForAnnotation.apply(new ItemToAnnotateRequest(uuid, USERNAME, SUBJECT_ID))).thenReturn(productAnnotatedItem);
        AnnotatorView<Product> annotatorView =
                productAnnotationController.getItemToAnnotate(new ItemToAnnotateRequest(uuid, USERNAME, SUBJECT_ID));

        assertEquals(productItem, annotatorView.getItem());
    }

    @Test
    public void whenAddingAnnotationsToAItemThenAddAnnotationsToAItem() {
        String annotatorName = "mark-h";
        AnnotationController<Product> productAnnotationController = createAnnotationController();
        ItemAnnotation itemAnnotation = new ItemAnnotation(ACTUAL_FACTS, SUBJECT_ID, annotatorName, uuid);
        productAnnotationController.addAnnotationsToItem(itemAnnotation);
        verify(annotateItem, times(1)).accept(itemAnnotation);
    }

    @Test
    public void whenGettingItemFactsForAItemId() {
        List<ItemFact> expectedItemFacts = new ArrayList<>() {{
            add(new ItemFact(1, new Fact(IS_A, SPORT), "mark"));
        }};
        when(getFactsForItem.apply(1)).thenReturn(expectedItemFacts);
        AnnotationController<Product> productAnnotationController = createAnnotationController();
        List<ItemFact> actualProductsToAnnotate = productAnnotationController.getFactsForItem(1);
        verify(getFactsForItem, times(1)).apply(1);
        assertEquals(expectedItemFacts, actualProductsToAnnotate);
    }

    @Test
    public void whenGettingItemSetViewsThenConvertThemCorrectlyFromItemSets() {
        List<Product> products = new ArrayList<>() {{
            add(new Product(1,
                    "Bush Mega Toaster",
                    "All about the bush mega toaster",
                    50.0,
                    "http://imageurl.com")
            );
            add(new Product(2,
                    "Sports Toaster",
                    "All about the Sports toaster",
                    50.0,
                    "http://imageurl.com")
            );
        }};

        ItemSet<Product> itemSet = new ItemSet<>();
        itemSet.setName("Sample Set");
        itemSet.setItems(products);
        itemSet.setSetId(UUID.fromString("1f0bb0dd-69a2-4c46-a080-c5569259c1e5"));
        itemSet.setFacts(POTENTIAL_FACTS);

        ItemSet<Product> itemSet2 = new ItemSet<>();
        itemSet2.setName("Sample Set2");
        itemSet2.setItems(products);
        itemSet2.setSetId(UUID.fromString("1f0bb0dd-69a2-4c46-a080-c5569259c1e5"));
        itemSet2.setFacts(POTENTIAL_FACTS);

        List<ItemSet<Product>> itemSets = new ArrayList<>() {{
            add(itemSet);
            add(itemSet2);
        }};

        when(getItemSets.get()).thenReturn(itemSets);

        AnnotationController<Product> productAnnotationController = createAnnotationController();

        List<ItemSetView> expectedItemSetViews = new ArrayList<>() {{
            add(new ItemSetView(itemSet.getName(), itemSet.getUuid(), itemSet.getItems().size()));
            add(new ItemSetView(itemSet2.getName(), itemSet2.getUuid(), itemSet2.getItems().size()));
        }};

        assertEquals(expectedItemSetViews, productAnnotationController.getItemSetViews());
    }

    public AnnotationController<Product> createAnnotationController() {
        return new AnnotationController<>(
                getItemForAnnotation,
                annotateItem,
                getFactsForItem,
                getItemSets,
                addItemsToAnnotate);
    }
}
