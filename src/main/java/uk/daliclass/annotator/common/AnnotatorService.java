package uk.daliclass.annotator.common;

import uk.daliclass.annotator.annotation.*;
import uk.daliclass.annotator.annotation.get.GetItemForAnnotation;
import uk.daliclass.annotator.common.domain.*;
import uk.daliclass.annotator.common.storage.InMemoryItemStore;
import uk.daliclass.annotator.facts.FactController;
import uk.daliclass.annotator.facts.FactCreation;
import uk.daliclass.annotator.common.storage.FactFileSystemStorage;
import uk.daliclass.annotator.common.storage.Log;

import java.nio.file.Path;
import java.util.List;

public class AnnotatorService<T extends Idable> {

    private final FactController factController;
    private final AnnotationController<T> annotationController;

    public AnnotatorService(
            Path factsFile,
            Path itemFactsFile,
            Class<T> clazz) {
        Log<Fact> factLog =
                new FactFileSystemStorage<Fact>(
                        factsFile,
                        Fact.class);

        Log<ItemFact> itemFactLog =
                new FactFileSystemStorage<ItemFact>(
                        itemFactsFile,
                        ItemFact.class);

        Log<T> productLog =
                new InMemoryItemStore<>();

        factController = new FactController(new FactCreation(factLog));

        annotationController = new AnnotationController<>(
                new GetItemForAnnotation<>(factLog, itemFactLog, productLog),
                new AnnotateItem(itemFactLog),
                new AddItemsToAnnotate<>(productLog),
                new GetFactsForItem(itemFactLog));
    }

    public CreationStatus createFact(String predicate, String object) {
        return this.factController.createFact(predicate, object);
    }

    public AnnotatorView<T> getItemToAnnotate(String username, Integer itemId) {
        return this.annotationController.getItemToAnnotate(username, itemId);
    }

    public void addAnnotationsToItem(ItemAnnotation itemAnnotation) {
        this.annotationController.addAnnotationsToItem(itemAnnotation);
    }

    public void addItemsToAnnotate(List<T> items) {
        this.annotationController.addItemsToAnnotate(items);
    }

    public List<ItemFact> getAnnotations(Integer itemId) {
       return this.annotationController.getFactsForItem(itemId);
    }
}
