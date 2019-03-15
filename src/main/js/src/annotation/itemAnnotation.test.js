import {itemAnnotation, updatePredicatesAction, startingAnnotationAction, DEFAULT_STATE} from "./itemAnnotation.js";
import _ from 'lodash';

function cloneDefaultState() {
  return _.cloneDeep(DEFAULT_STATE);
}

describe("Given a item is being annotated", () => {
  describe("When starting annotation", () => {
    const predicates = [];
    const action = startingAnnotationAction({predicates: [], subject: "is happy days"});

    it("Then update predicates", () => {
      const expectedState = cloneDefaultState();
      expectedState.predicates = [];
      expectedState.subject = "is happy days";
      const actualState = itemAnnotation({predicates: [], subject: ""}, action);
      expect(actualState).toEqual(expectedState);
    });
  });

  describe("When predicates are updated", () => {
    const predicates = [];
    const action = updatePredicatesAction(predicates);

    it("Then update predicates", () => {
      const expectedState = cloneDefaultState();
      expectedState.predicates = [];
      const actualState = itemAnnotation(cloneDefaultState(), action);
      expect(expectedState).toEqual(actualState);
    });
  });
});
