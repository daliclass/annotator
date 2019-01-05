package uk.daliclass.annotator.common;

import org.junit.After;
import org.junit.Test;
import uk.daliclass.annotator.common.domain.AnnotatorView;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.product.common.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AnnotatorServiceTest {

    private Path FACTS_FILE_PATH = Paths.get("./facts.log").toAbsolutePath();
    private Path ANNOTATIONS_FILE_PATH = Paths.get("./annotations.log").toAbsolutePath();

    @After
    public void after() throws IOException {
        Files.delete(FACTS_FILE_PATH);
        Files.delete(ANNOTATIONS_FILE_PATH);
    }
    @Test
    public void exampleOfApiUsageForAnnotator() {
        /* Potential facts are the information that a user of the API can map to a item
         */
        List<Fact> potentialFacts = new ArrayList<>() {{
            add(new Fact(Fact.Predicate.IS_A, Fact.Object.ELECTRONIC));
            add(new Fact(Fact.Predicate.IS_A, Fact.Object.SPORT));
        }};

        /* Products are the Item in this case as its the generic used by the rest of the library
         */
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

        AnnotatorService<Product> annotatorService = new AnnotatorService<>(
                FACTS_FILE_PATH,
                ANNOTATIONS_FILE_PATH,
                Product.class);

        /* Internally the service contains a list of products that can be annotated it keeps these in the order they are
         * provided.
         */
        annotatorService.addItemsToAnnotate(products);

        for (Fact fact: potentialFacts) {
            annotatorService.createFact(fact.getPredicate().name(), fact.getObject().name());
        }

        AnnotatorView<Product> productAnnotatorView = annotatorService.getItemToAnnotate("Mark", 1);

        annotatorService.addAnnotationsToItem(
                new ItemAnnotation(
                        new ArrayList<>(productAnnotatorView.getPotentialFacts()),
                        productAnnotatorView.getItem().getItemId(),
                        "Mark")
        );

        productAnnotatorView = annotatorService.getItemToAnnotate("Mark", 2);

        annotatorService.addAnnotationsToItem(
                new ItemAnnotation(
                        new ArrayList<>() {{add(new Fact(Fact.Predicate.IS_A, Fact.Object.SPORT));}},
                        productAnnotatorView.getItem().getItemId(),
                        "Mark")
        );

        assertNull(productAnnotatorView.getNextItemId());

        List<ItemFact> expectedItemFactsOne = new ArrayList<>() {{
            add(new ItemFact(1, new Fact(Fact.Predicate.IS_A, Fact.Object.ELECTRONIC), "Mark"));
            add(new ItemFact(1, new Fact(Fact.Predicate.IS_A, Fact.Object.SPORT), "Mark"));
        }};

        List<ItemFact> expectedItemFactsTwo = new ArrayList<>() {{
            add(new ItemFact(2, new Fact(Fact.Predicate.IS_A, Fact.Object.SPORT), "Mark"));
        }};

        List<ItemFact> actualItemFactsOne = annotatorService.getAnnotations(1);
        List<ItemFact> actualItemFactsTwo = annotatorService.getAnnotations(2);

        assertEquals(actualItemFactsOne, expectedItemFactsOne);
        assertEquals(actualItemFactsTwo, expectedItemFactsTwo);
    }
}
