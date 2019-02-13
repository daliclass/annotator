import _ from "lodash";
import Papa from "papaparse";
export const DEFAULT_STATE = {
  name: "",
  type: {label: "text", value: "text"},
  options: [{label: "text", value: "text"}, {label: "image", value: "image"}],
  facts: [{id: 0, predicate: "", objects: []}],
  itemsToAnnotate: []
};

const ACTIONS = {
  SET_NAME: "SET_NAME",
  SET_TYPE: "SET_TYPE",
  SET_FACT: "SET_FACT",
  CREATE_ITEM_SET: "CREATE_ITEM_SET",
  UPLOAD_TEMPLATE: "UPLOAD_TEMPLATE",
  DOWNLOAD_TEMPLATE: "DOWNLOAD_TEMPLATE"
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

export function createItemSetAction() {
  return {
    type: ACTIONS.CREATE_ITEM_SET,
    payload: undefined
  };
}

export function downloadTemplateAction() {
  return {
    type: ACTIONS.DOWNLOAD_TEMPLATE,
    payload: undefined
  };
}

export function uploadTemplateAction(jsonCsv) {
  return {type: ACTIONS.UPLOAD_TEMPLATE, payload: jsonCsv};
}

export function parseTemplateAction(file) {
  return function(dispatch) {
    console.log("called");
    Papa.parse(file, {
      complete: (results, parsedFile) => {
        console.log(results);
        dispatch(uploadTemplateAction(results.data));
      }
    });
  };
}

export function itemSetCreator(state = DEFAULT_STATE, action) {
  const copyOfState = _.cloneDeep(state);
  // eslint-disable-next-line
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
      copyOfState.facts = removeEmptyFacts(copyOfState.facts);
      copyOfState.facts.push({id: Math.random(), predicate: "", objects: []});
      break;
    case ACTIONS.UPLOAD_TEMPLATE:
      if (action.payload === undefined) {
        copyOfState.itemsToAnnotate = [];
        break;
      }
      let itemsToAnnotate = [];
      const ID = 0;
      const TEXT = 1;
      action.payload.shift(); // REMOVE HEADERS
      action.payload.forEach(line => {
        if (line.length > 1) {
          if (line[ID] && line[TEXT]) {
            itemsToAnnotate.push({id: line[ID], text: line[TEXT]});
          }
        }
      });
      copyOfState.itemsToAnnotate = itemsToAnnotate;
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
