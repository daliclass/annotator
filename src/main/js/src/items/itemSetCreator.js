import _ from "lodash";

export const DEFAULT_STATE = {
  name: "",
  type: {label: "text", value: "text"},
  options: [{label: "text", value: "text"}, {label: "image", value: "image"}],
  facts: [{id: 0, predicate: "", objects: []}]
};

const ACTIONS = {
  SET_NAME: "SET_NAME",
  SET_TYPE: "SET_TYPE",
  SET_FACT: "SET_FACT"
};

export function setNameAction(name) {
  return {
    type: ACTIONS.SET_NAME,
    payload: {value: name}
  };
}

export function setTypeAction(label, value) {
  return {
    type: ACTIONS.SET_TYPE,
    payload: {label: label, value: value}
  };
}

export function setFactAction(id, predicate, objects) {
  return {
    type: ACTIONS.SET_FACT,
    payload: {
      id: id,
      predicate: predicate,
      objects: objects
    }
  };
}

export function itemSetCreator(state = DEFAULT_STATE, action) {
  const copyOfState = _.cloneDeep(DEFAULT_STATE);
  switch (action.type) {
    case ACTIONS.SET_NAME:
      copyOfState.name = action.payload.value;
      break;
    case ACTIONS.SET_TYPE:
      copyOfState.type = action.payload;
      break;
    case ACTIONS.SET_FACT:
      let selectedFact = getFact(copyOfState.facts, action.payload.id);
      selectedFact.predicate = action.payload.predicate;
      selectedFact.objects = action.payload.objects;
      copyOfState.facts = removeEmptyFacts(copyOfState.facts)
      copyOfState.facts.push({id: Math.random(), predicate: "", objects: []});

      break;
  }
  return copyOfState;
}

function getFact(facts, id) {
  let factsWithMatchingId = facts.filter(fact => fact.id === id);
  if (factsWithMatchingId.length > 0) {
    return factsWithMatchingId[0];
  } else {
    return null; // creating a new fact
  }
}

function removeEmptyFacts(facts) {
  return facts.reduce((acc, fact) => {
    if (fact.predicate !== "") {
      acc.push(fact);
    }
    return acc;
  }, []);
}
