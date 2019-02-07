export const DEFAULT_STATE = {
  name: "product rater",
  type: {label: "text", value: "text"},
  options: [{label: "text", value: "text"}, {label: "image", value: "image"}],
  facts: [
    {id: 1, predicate: "suitable_for", objects: ["MALES", "FEMALES"]},
    {id: 2, predicate: "is good value out of 5", objects: [1, 2, 3, 4, 5]}
  ]
}

export default function itemSetCreator(state=DEFAULT_STATE, action) {
  return state;
}
