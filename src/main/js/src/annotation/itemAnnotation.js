import _ from "lodash";

export const DEFAULT_STATE = {
  subject: "subject from state",
  predicates:[
    {"predicate": "Leaning", "objects": [{"object": "Left", "id": 0, "selected": false},{"object": "Right","id":0, "selected": false},{"object": "center", "id": 0, "selected": false}]},
    {"predicate": "Suitable for", "objects": [{"object": "0-25", "id": 1, "selected": false},{"object": "25-50","id":1, "selected": false},{"object": "51+", "id": 0, "selected": false}]}
  ]
};

const ACTIONS = {
  UPDATED_PREDICATES: "UPDATED_PREDICATES",
  ITEM_ANNOTATED: "ITEM_ANNOTATED",
  STARTING_ANNOTATION: "STARTNG_ANNOTATION"
};

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

export function startingAnnotationAction(payload) {
  return {type: ACTIONS.STARTING_ANNOTATION, payload: payload};
}

export function itemAnnotation(state = DEFAULT_STATE, action) {
  const copyOfState = _.cloneDeep(state);
  // eslint-disable-next-line
  switch (action.type) {
    case ACTIONS.UPDATED_PREDICATES:
      copyOfState.predicates = action.payload;
      break;
    case ACTIONS.STARTING_ANNOTATION:
      copyOfState.predicates = action.payload.predicates;
      copyOfState.subject = action.payload.subject;
      break;
  }
  return copyOfState;
}
