import {
  itemAnnotation,
  updatePredicatesAction,
  startingAnnotationAction,
  DEFAULT_STATE,
  newItemForAnnotationAction,
  completedAnnotationAction,
  transformAnnotatorView
} from "./itemAnnotation.js";
import _ from "lodash";

function cloneDefaultState() {
  return _.cloneDeep(DEFAULT_STATE);
}

describe("Given a item is being annotated", () => {
  describe("When starting annotation", () => {
    const predicates = [];
    const itemSetId = "foo";
    const action = startingAnnotationAction(itemSetId);

    it("Then update state", () => {
      const expectedState = cloneDefaultState();
      expectedState.annotationCompleted = false;
      expectedState.itemSetId = itemSetId;
      const actualState = itemAnnotation(cloneDefaultState(), action);
      expect(actualState).toEqual(expectedState);
    });
  });

  describe("When predicates are updated", () => {
    const predicates = [];
    const action = updatePredicatesAction(predicates);

    it("Then update state", () => {
      const expectedState = cloneDefaultState();
      expectedState.predicates = [];
      const actualState = itemAnnotation(cloneDefaultState(), action);
      expect(actualState).toEqual(expectedState);
    });
  });

  describe("When a new item for annotation", () => {
    const predicates = {predicates: [], subject: "New Subject", nextItemId: 1};
    const action = newItemForAnnotationAction(predicates);

    it("Then update state", () => {
      const expectedState = cloneDefaultState();
      expectedState.predicates = _.cloneDeep(predicates.predicates);
      expectedState.subject = _.cloneDeep(predicates.subject);
      expectedState.nextItemId = _.cloneDeep(predicates.nextItemId);
      const actualState = itemAnnotation(cloneDefaultState(), action);
      expect(actualState).toEqual(expectedState);
    });
  });

  describe("When annotation has completed for a item set", () => {
    const action = completedAnnotationAction();

    it("Then update state", () => {
      const expectedState = cloneDefaultState();
      expectedState.predicates = [];
      expectedState.subject = "";
      expectedState.annotationCompleted = true;
      const actualState = itemAnnotation(cloneDefaultState(), action);
      expect(actualState).toEqual(expectedState);
    });
  });
});

describe("When item view is provided from the server", () => {
  it("Then transform it to ui state", () => {
    let spy = jest.fn();
    let annotatorView = {
      item: {id: "1", text: "I'm a man", itemId: 0},
      potentialFacts: [
        {predicate: "suitable for", object: "men", id: 0},
        {predicate: "suitable for", object: "women", id: 0},
        {predicate: "loved by", object: "women", id: 0}
      ],
      productFacts: [],
      nextItemId: 1
    };

    const expectedUiState = {
      subject: "I'm a man",
      predicates: [
        {
          predicate: "suitable for",
          objects: [{object: "men", id: 0}, {object: "women", id: 0}]
        },
        {
          predicate: "loved by",
          objects: [{object: "women", id: 0}]
        }
      ],
      nextItemId: 1
    };
    transformAnnotatorView(annotatorView, spy);
    expect(spy).toHaveBeenCalledWith(
      newItemForAnnotationAction(expectedUiState)
    );
  });
});
