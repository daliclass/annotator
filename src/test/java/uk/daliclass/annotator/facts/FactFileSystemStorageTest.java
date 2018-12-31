package uk.daliclass.annotator.facts;

import org.junit.After;
import org.junit.Test;
import uk.daliclass.annotator.common.Fact;

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
        Log<Fact> log = new FactFileSystemStorage(FILE_PATH, Fact.class);
        assertTrue(log.create(fact));
    }

    @Test
    public void whenReadingFromTheFactsFileThenGetCorrectObjects() {
        Fact factOne = new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.MEN);
        Fact factTwo = new Fact(Fact.Predicate.SUITABLE_FOR, Fact.Object.FEMALE);
        Fact factThree = new Fact(Fact.Predicate.IS_A, Fact.Object.ELECTRONIC);
        Log<Fact> log = new FactFileSystemStorage(FILE_PATH, Fact.class);
        log.create(factOne);
        log.create(factTwo);
        log.create(factThree);

        assertTrue(log.read().containsAll(new ArrayList() {{
            add(factOne);
            add(factTwo);
            add(factThree);
        }}));
    }
}
