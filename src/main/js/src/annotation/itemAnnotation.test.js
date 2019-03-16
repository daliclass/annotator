import {
  itemAnnotation,
  updatePredicatesAction,
  startingAnnotationAction,
  DEFAULT_STATE,
  newItemForAnnotationAction,
  completedAnnotationAction
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
    const predicates = {predicates: [], subject: "New Subject"};
    const action = newItemForAnnotationAction(predicates);

    it("Then update state", () => {
      const expectedState = cloneDefaultState();
      expectedState.predicates = _.cloneDeep(predicates.predicates);
      expectedState.subject = _.cloneDeep(predicates.subject);
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
