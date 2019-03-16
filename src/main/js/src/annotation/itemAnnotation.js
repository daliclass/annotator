import _ from "lodash";

export const DEFAULT_STATE = {
  subject: "subject from state",
  predicates: [
    {
      predicate: "Leaning",
      objects: [
        {object: "Left", id: 0, selected: false},
        {object: "Right", id: 0, selected: false},
        {object: "center", id: 0, selected: false}
      ]
    },
    {
      predicate: "Suitable for",
      objects: [
        {object: "0-25", id: 1, selected: false},
        {object: "25-50", id: 1, selected: false},
        {object: "51+", id: 0, selected: false}
      ]
    }
  ],
  annotationCompleted: undefined,
  itemSetId: undefined
};

const ACTIONS = {
  STARTING_ANNOTATION: "STARTNG_ANNOTATION",
  UPDATED_PREDICATES: "UPDATED_PREDICATES",
  ITEM_ANNOTATED: "ITEM_ANNOTATED",
  NEW_ITEM_FOR_ANNOTATION: "NEW_ITEM_FOR_ANNOTATION",
  COMPLETED_ANNOTATION: "COMPLETED_ANNOTATION"
};

export function startingAnnotationAction(itemSetId) {
  return {type: ACTIONS.STARTING_ANNOTATION, payload: {itemSetId: itemSetId}};
}

export function updatePredicatesAction(updatedPredicates) {
  return {
    type: ACTIONS.UPDATED_PREDICATES,
    payload: updatedPredicates
  };
}

export function itemAnnotatedAction(predicates) {
  return {
    type: ACTIONS.ITEM_ANNOTATED,
    payload: predicates
  };
}

export function newItemForAnnotationAction(payload) {
  return {type: ACTIONS.NEW_ITEM_FOR_ANNOTATION, payload: payload};
}

export function completedAnnotationAction(payload) {
  return {type: ACTIONS.COMPLETED_ANNOTATION, payload: payload};
}

export function itemAnnotation(state = DEFAULT_STATE, action) {
  const copyOfState = _.cloneDeep(state);
  // eslint-disable-next-line
  switch (action.type) {
    case ACTIONS.STARTING_ANNOTATION:
      copyOfState.itemSetId = action.payload.itemSetId;
      copyOfState.annotationCompleted = false;
      break;
    case ACTIONS.UPDATED_PREDICATES:
      copyOfState.predicates = action.payload;
      break;
    case ACTIONS.NEW_ITEM_FOR_ANNOTATION:
      copyOfState.predicates = action.payload.predicates;
      copyOfState.subject = action.payload.subject;
      break;
    case ACTIONS.COMPLETED_ANNOTATION:
      copyOfState.predicates = [];
      copyOfState.subject = "";
      copyOfState.annotationCompleted = true;
      break;
  }
  return copyOfState;
}
