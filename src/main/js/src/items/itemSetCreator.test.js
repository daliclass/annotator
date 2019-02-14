import {
  itemSetCreator,
  DEFAULT_STATE,
  setNameAction,
  setTypeAction,
  setFactAction,
  uploadTemplateAction
} from "./itemSetCreator.js";
import _ from "lodash";

const RANDOM_NUMBER = 0.5;
const mockMath = Object.create(global.Math);
mockMath.random = () => {
  return RANDOM_NUMBER;
};
global.Math = mockMath;

function cloneDefaultState() {
  return _.cloneDeep(DEFAULT_STATE);
}

describe("Given item set is being created", () => {
  describe("When setting name", () => {
    const name = "name";
    const action = setNameAction(name);

    it("Then update name state", () => {
      const EXPECTED_STATE = cloneDefaultState();
      EXPECTED_STATE.name = name;
      const ACTUAL_STATE = itemSetCreator(cloneDefaultState(), action);
      expect(EXPECTED_STATE).toEqual(ACTUAL_STATE);
    });
  });

  describe("When setting type", () => {
    const label = "image";
    const value = "imageValue";
    const action = setTypeAction(label, value);

    it("Then update type state", () => {
      const EXPECTED_STATE = cloneDefaultState();
      EXPECTED_STATE.type = {label: label, value: value};
      const ACTUAL_STATE = itemSetCreator(cloneDefaultState(), action);
      expect(EXPECTED_STATE).toEqual(ACTUAL_STATE);
    });
  });

  describe("When setting fact", () => {
    const EXPECTED_STATE = cloneDefaultState();
    const id = EXPECTED_STATE.facts[0].id;
    const predicate = "suitable for";
    const objects = ["Men"];
    const action = setFactAction(id, predicate, objects);

    it("Then update fact state and render a additional fact field", () => {
      const expected_predicate = "suitable for";
      EXPECTED_STATE.facts = [
        {id: id, predicate: expected_predicate, objects: objects},
        {id: RANDOM_NUMBER, predicate: "", objects: []}
      ];
      const ACTUAL_STATE = itemSetCreator(cloneDefaultState(), action);
      expect(ACTUAL_STATE).toEqual(EXPECTED_STATE);
    });
  });

  describe("When removing a fact", () => {
    const START_STATE = cloneDefaultState();
    const ID = START_STATE.facts[0].id;
    START_STATE.facts = [
      {id: ID, predicate: "foo", objects: ["bar"]},
      {id: RANDOM_NUMBER, predicate: "", objects: []}
    ];
    const action = setFactAction(ID, "", []);
    it("Then don't create additional blank fact state", () => {
      const EXPECTED_STATE = cloneDefaultState();
      EXPECTED_STATE.facts = [{id: RANDOM_NUMBER, predicate: "", objects: []}];
      const ACTUAL_STATE = itemSetCreator(START_STATE, action);
      expect(ACTUAL_STATE).toEqual(EXPECTED_STATE);
    });
  });

  describe("When parsing a CSV and type text selected", () => {
    const START_STATE = cloneDefaultState();
    const CSV = [
      ["id", "text"],
      ["1", "Its turkish I love it", ""],
      ["2", "Whys a washing machine in the pub i need another drink"],
      ["3", ""],
      ["", "Its morocan I love it"],
      ["", ""],
      [""]
    ];
    it("Then should convert to text type", () => {
      const ACTION = uploadTemplateAction(CSV);
      const EXPECTED_TEXT_OBJECTS = [
        {
          id: "1",
          text: "Its turkish I love it"
        },
        {
          id: "2",
          text: "Whys a washing machine in the pub i need another drink"
        }
      ];
      const expectedState = cloneDefaultState();
      expectedState.items = EXPECTED_TEXT_OBJECTS;
      const ACTUAL_STATE = itemSetCreator(START_STATE, ACTION);
      expect(ACTUAL_STATE).toEqual(expectedState);
    });
  });

  describe("When reciving a undefined upload", () => {
    const startState = cloneDefaultState();
    const EXPECTED_TEXT_OBJECTS = [
      {
        id: "1",
        text: "Its turkish I love it"
      },
      {
        id: "2",
        text: "Whys a washing machine in the pub i need another drink"
      }
    ];
    startState.items = EXPECTED_TEXT_OBJECTS;

    it("Then remove items", () => {
      const ACTION = uploadTemplateAction(undefined);
      const expectedState = cloneDefaultState();
      expectedState.items = [];
      const ACTUAL_STATE = itemSetCreator(startState, ACTION);
      expect(ACTUAL_STATE).toEqual(expectedState);
    });
  });
});
