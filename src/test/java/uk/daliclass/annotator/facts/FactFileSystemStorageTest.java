package uk.daliclass.annotator.facts;

import org.junit.After;
import org.junit.Test;
import uk.daliclass.annotator.facts.domain.Fact;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class FactFileSystemStorageTest {

    private Path FILE_PATH = Paths.get("./facts.json").toAbsolutePath();

    @After
    public void after() throws IOException {
        Files.delete(FILE_PATH);
    }

    @Test
    public void whenProvidedAFactThenPersistToDisk() {
        Fact fact = new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN);
        FactStore factStore = new FactFileSystemStorage(FILE_PATH);
        assertTrue(factStore.create(fact));
    }

    @Test
    public void whenReadingFromTheFactsFileThenGetCorrectObjects() {
        Fact factOne = new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN);
        Fact factTwo = new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.FEMALE);
        Fact factThree = new Fact(Fact.Predicate.IS_A, Fact.Object.ELECTRONIC);
        FactStore factStore = new FactFileSystemStorage(FILE_PATH);
        factStore.create(factOne);
        factStore.create(factTwo);
        factStore.create(factThree);

        assertTrue(factStore.read().containsAll(new ArrayList() {{
            add(factOne);
            add(factTwo);
            add(factThree);
        }}));
    }
}
