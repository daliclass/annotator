package uk.daliclass.annotator.common;

import uk.daliclass.annotator.annotation.*;
import uk.daliclass.annotator.annotation.get.GetItemForAnnotation;
import uk.daliclass.annotator.common.domain.Idable;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemFact;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.AnnotationsRequest;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.domain.views.AnnotatorView;
import uk.daliclass.annotator.common.domain.views.ItemSetView;
import uk.daliclass.annotator.common.storage.FactFileSystemStorage;
import uk.daliclass.annotator.common.storage.Log;

import java.nio.file.Path;
import java.util.List;

public class AnnotatorService<T extends Idable> {

    private final AnnotationController<T> annotationController;

    public AnnotatorService(
            Path itemSetFile,
            Path itemFactsFile,
            Class<T> clazz) {

        Log<ItemFact> itemFactLog =
                new FactFileSystemStorage<>(
                        itemFactsFile,
                        ItemFact.class);

        Log<ItemSet<T>> itemSetLog =
                new FactFileSystemStorage<>(
                        itemSetFile,
                        ItemSet.class);

        annotationController = new AnnotationController<T>(
                new GetItemForAnnotation<>(itemFactLog, itemSetLog), // DONE
                new AnnotateItem(itemFactLog),
                new GetFactsForItem(itemFactLog),
                new GetItemSets<>(itemSetLog),
                new AddItemsToAnnotate<T>(itemSetLog)
        );
    }

    public void addAnnotationsToItem(ItemAnnotation itemAnnotation) {
        annotationController.addAnnotationsToItem(itemAnnotation);
    }

    public List<ItemSetView> getItemSets() {
        return annotationController.getItemSetViews();
    }

    public AnnotatorView<T> getItemToAnnotate(ItemToAnnotateRequest itemToAnnotateRequest) {
        return annotationController.getItemToAnnotate(itemToAnnotateRequest);
    }

    public List<ItemFact> getAnnotations(AnnotationsRequest annotationsRequest) {
        return annotationController.getFactsForItem(annotationsRequest.getItemId());
    }

    public void addItemsToAnnotate(ItemSet<T> itemSet) {
        annotationController.addItemsToAnnotate(itemSet);
    }

}
