package uk.daliclass.annotator.facts;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FactFileSystemStorage<T> implements Log<T> {

    private final Path filePath;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String LINE_BREAK = "\n";
    private static final Logger LOGGER = Logger.getLogger(FactFileSystemStorage.class.getName());
    private final Class<T> clazz;

    public FactFileSystemStorage(Path filePath, Class<T> clazz) {
        this.filePath = filePath;
        this.clazz = clazz;
    }

    @Override
    public Boolean create(T type) {
        try {
            if (filePath.toFile().exists()) {
                Files.write(this.filePath,
                        OBJECT_MAPPER.writeValueAsString(type).concat("\n").getBytes(),
                        StandardOpenOption.APPEND);
            } else {
                Files.write(this.filePath,
                        OBJECT_MAPPER.writeValueAsString(type).concat("\n").getBytes(),
                        StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public List<T> read() {
        List<T> types = new ArrayList();
        try {
            Files.readAllLines(filePath);
            for (String line : Files.readAllLines(filePath)) {
                types.add(OBJECT_MAPPER.readValue(line.replace(LINE_BREAK, ""), clazz));
            }
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
        return types;
    }
}
