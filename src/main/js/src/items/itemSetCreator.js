import _ from "lodash";
import Papa from "papaparse";
import uuidv4 from "uuid/v4";
export const DEFAULT_STATE = {
  name: "",
  type: {label: "text", value: "text"},
  options: [{label: "text", value: "text"}],
  facts: [{id: 0, predicate: "", objects: []}],
  items: []
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

export function prepareItemSetToBeSent(state) {
  let copyOfState = _.cloneDeep(state);
  delete copyOfState.options;
  delete copyOfState.type;
  copyOfState.items = copyOfState.items.map(item => {
    item["@class"] = "uk.daliclass.text.common.Text";
    return item;
  });

  copyOfState.facts = copyOfState.facts.filter(fact => {
    return fact.predicate && fact.objects && fact.objects.length > 0;
  });

  copyOfState.facts = copyOfState.facts.reduce((acc, fact) => {
    fact.objects.forEach(object => {
      acc.push({predicate: fact.predicate, object: object, id: 0});
    });
    return acc;
  }, []);
  copyOfState.uuid = uuidv4();
  return copyOfState;
}

export function postItemSetAction() {
  return (dispatch, getState) => {
    fetch("http://localhost:8080/annotate/text", {
      method: "post",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(prepareItemSetToBeSent(getState().itemSetCreator))
    }).then(function(response) {
      return {
        type: ACTIONS.CREATE_ITEM_SET,
        payload: response
      };
    });
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
    Papa.parse(file, {
      complete: (results, parsedFile) => {
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
      copyOfState.facts.push({id: copyOfState.facts.length, predicate: "", objects: []});
      break;
    case ACTIONS.UPLOAD_TEMPLATE:
      if (action.payload === undefined) {
        copyOfState.items = [];
        break;
      }
      let items = [];
      const ID = 0;
      const TEXT = 1;
      action.payload.shift(); // REMOVE HEADERS
      action.payload.forEach(line => {
        if (line.length > 1) {
          if (line[ID] && line[TEXT]) {
            items.push({id: line[ID], text: line[TEXT]});
          }
        }
      });
      copyOfState.items = items;
      break;
    case ACTIONS.CREATE_ITEM_SET:
      break;
  }
  return copyOfState;
}

function getFact(facts, id) {
  let factsWithMatchingId = facts.filter(fact => fact.id === id);
  if (factsWithMatchingId.length > 0) {
    return factsWithMatchingId[0];
  } else {
    return null;
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
