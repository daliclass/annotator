import _ from "lodash";

const DEFAULT_STATE = {
  itemSets: []
};

const ACTIONS = {
  GET_ITEM_SETS: "GET_ITEM_SETS"
};

export function getItemSetAction() {
  return (dispatch, getState) => {
    fetch("http://localhost:8080/itemsets", {
      method: "get",
      headers: {"Content-Type": "application/json"}
    })
      .then(resp => resp.json())
      .then(json => {
        dispatch({
          type: ACTIONS.GET_ITEM_SETS,
          payload: json
        });
      });
  };
}

export function itemSetViewer(state = DEFAULT_STATE, action) {
  let copyOfState = _.cloneDeep(state);
  switch (action.type) {
    case ACTIONS.GET_ITEM_SETS:
      copyOfState.itemSets = action.payload;
      break;
  }
  return copyOfState;
}
