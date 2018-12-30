package uk.daliclass.annotator.facts;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.daliclass.annotator.facts.domain.Fact;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FactFileSystemStorage implements FactStore {

    private final Path filePath;
    private static final  ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String LINE_BREAK = "\n";
    private static final Logger LOGGER = Logger.getLogger(FactFileSystemStorage.class.getName());

    public FactFileSystemStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Boolean create(Fact fact) {
        try {
            if (filePath.toFile().exists()) {
                Files.write(this.filePath,
                        OBJECT_MAPPER.writeValueAsString(fact).concat("\n").getBytes(),
                        StandardOpenOption.APPEND);
            } else {
                Files.write(this.filePath,
                        OBJECT_MAPPER.writeValueAsString(fact).concat("\n").getBytes(),
                        StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public List<Fact> read() {
        List<Fact> facts = new ArrayList();
        try {
            Files.readAllLines(filePath);
            for(String line: Files.readAllLines(filePath)) {
                facts.add(OBJECT_MAPPER.readValue(line.replace(LINE_BREAK, ""), Fact.class));
            }
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
        return facts;
    }
}
