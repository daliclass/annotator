package uk.daliclass.annotator.common;

import org.junit.After;
import org.junit.Test;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.AnnotationsRequest;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.domain.views.AnnotatorView;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.domain.views.ItemSetView;
import uk.daliclass.product.common.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AnnotatorServiceTest {

    private Path ITEM_SET_FILE_PATH = Paths.get("src/test/resources/item_set.log").toAbsolutePath();
    private Path ANNOTATIONS_FILE_PATH = Paths.get("src/test/resources/annotations.log").toAbsolutePath();

    @After
    public void after() throws IOException {
        Files.delete(ITEM_SET_FILE_PATH);
        Files.delete(ANNOTATIONS_FILE_PATH);
    }

    @Test
    public void exampleOfApiUsageForAnnotator() throws IOException {
        /* Potential facts are the information that a user of the API can map to a item
         */
        String IS_A = "IS_A";
        String ELECTRONIC = "ELECTRONIC";
        String SPORT = "SPORT";
        List<Fact> potentialFacts = new ArrayList<>() {{
            add(new Fact(IS_A, ELECTRONIC));
            add(new Fact(IS_A, SPORT));
        }};

        /* Products are the Item in this case as its the generic used by the rest of the library
         */
        List<Product> products = new ArrayList<>() {{
           add(new Product(0,
                   "Bush Mega Toaster",
                   "All about the bush mega toaster",
                   50.0,
                   "http://imageurl.com")
           );
            add(new Product(1,
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
        itemSet.setFacts(potentialFacts);

        AnnotatorService<Product> annotatorService = new AnnotatorService<>(
                ITEM_SET_FILE_PATH,
                ANNOTATIONS_FILE_PATH,
                Product.class);

        /* Internally the service contains a list of products that can be annotated it keeps these in the order they are
         * provided.
         */
        annotatorService.addItemsToAnnotate(itemSet);

        List<ItemSetView> itemSetViews = annotatorService.getItemSets();

        ItemSetView expectedItemSetView = new ItemSetView(itemSet.getName(), itemSet.getUuid(), products.size());

        ItemSetView actualItemSetView = itemSetViews.stream()
                .filter(itemSetView -> itemSetView.getUuid() != itemSet.getUuid())
                .findFirst()
                .orElseGet(() -> null);

        assertEquals(expectedItemSetView, actualItemSetView);

        AnnotatorView<Product> productAnnotatorView = annotatorService
                .getItemToAnnotate(new ItemToAnnotateRequest(actualItemSetView.getUuid(), "Mark", 0));

        annotatorService.addAnnotationsToItem(
                new ItemAnnotation(
                        new ArrayList<>(productAnnotatorView.getPotentialFacts()),
                        productAnnotatorView.getItem().getItemId(),
                        "Mark",
                        actualItemSetView.getUuid())
        );

        productAnnotatorView = annotatorService.
                getItemToAnnotate(new ItemToAnnotateRequest(
                        actualItemSetView.getUuid(),"Mark", productAnnotatorView.getNextItemId())
                );

        annotatorService.addAnnotationsToItem(
                new ItemAnnotation(
                        new ArrayList<>() {{add(new Fact(IS_A, SPORT));}},
                        productAnnotatorView.getItem().getItemId(),
                        "Mark",
                        actualItemSetView.getUuid())
        );

        assertNull(productAnnotatorView.getNextItemId());

        List<ItemFact> expectedItemFactsOne = new ArrayList<>() {{
            add(new ItemFact(0, new Fact(IS_A, ELECTRONIC), "Mark"));
            add(new ItemFact(0, new Fact(IS_A, SPORT), "Mark"));
        }};

        List<ItemFact> expectedItemFactsTwo = new ArrayList<>() {{
            add(new ItemFact(1, new Fact(IS_A, SPORT), "Mark"));
        }};

        List<ItemFact> actualItemFactsOne = annotatorService.getAnnotations(new AnnotationsRequest(0, actualItemSetView.getUuid()));
        List<ItemFact> actualItemFactsTwo = annotatorService.getAnnotations(new AnnotationsRequest(1, actualItemSetView.getUuid()));

        assertEquals(actualItemFactsOne, expectedItemFactsOne);
        assertEquals(actualItemFactsTwo, expectedItemFactsTwo);
    }
}
