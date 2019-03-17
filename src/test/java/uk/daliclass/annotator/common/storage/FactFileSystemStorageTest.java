package uk.daliclass.annotator.common.storage;

import org.junit.After;
import org.junit.Test;
import uk.daliclass.annotator.common.domain.Fact;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.product.common.Product;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FactFileSystemStorageTest {

    private static final String IS_A = "IS_A";
    private static final String ELECTRONIC = "ELECTRONIC";
    private static final String SUITABLE_FOR = "SUITABLE_FOR";
    private static final String MEN = "MEN";
    private static final String FEMALE = "FEMALE";

    private Path FILE_PATH = Paths.get("./facts.json").toAbsolutePath();

    @After
    public void after() throws IOException {
        new FactFileSystemStorage(FILE_PATH, Fact.class).refresh();
    }

    @Test
    public void whenProvidedAFactThenPersistToDisk() {
        Fact fact = new Fact(SUITABLE_FOR, MEN);
        Log<Fact> log = new FactFileSystemStorage<>(FILE_PATH, Fact.class);
        assertTrue(log.create(fact));
    }

    @Test
    public void whenReadingFromTheFactsFileThenGetCorrectObjects() {
        Fact factOne = new Fact(SUITABLE_FOR, MEN);
        Fact factTwo = new Fact(SUITABLE_FOR, FEMALE);
        Fact factThree = new Fact(IS_A, ELECTRONIC);
        Log<Fact> log = new FactFileSystemStorage<>(FILE_PATH, Fact.class);
        log.create(factOne);
        log.create(factTwo);
        log.create(factThree);

        assertTrue(log.read().containsAll(new ArrayList() {{
            add(factOne);
            add(factTwo);
            add(factThree);
        }}));
    }

    @Test
    public void writingAndReadingGenerics() {
        List<Product> products = new ArrayList<>() {{
            add(new Product(1, "str", "Str", 0.0, "str"));
        }};
        ItemSet<Product> factOne = new ItemSet<Product>("set1", products);
        Log<ItemSet> log = new FactFileSystemStorage<>(FILE_PATH, ItemSet.class);
        log.create(factOne);


        assertTrue(log.read().containsAll(new ArrayList() {{

        }}));
    }
}
