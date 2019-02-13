import {shallow} from "enzyme";
import React from "react";
import FactSelector from "./FactSelector.js";

const OBJECTS = ["MEN", "WOMEN", "CHILDREN"];
const PREDICATE = "SUITABLE_FOR";
const FACT_ID = 0;

function createWrapper(onChange) {
  return shallow(
    <FactSelector
      onChange={onChange}
      objects={OBJECTS}
      predicate={PREDICATE}
      id={FACT_ID}
    />
  ).dive();
}

describe("Given the fact selector is provided 3 OBJECTS and  PRREDICATE", () => {
  describe("When the user changes the text field", () => {
    let spy = jest.fn();
    let wrapper = createWrapper(spy);

    const UPDATED_PREDICATE = "unsuitable_for";

    wrapper.find("#predicate").simulate("change", {
      target: {
        value: UPDATED_PREDICATE
      }
    });

    it("Then onChange is called with the updated predicate and 3 objects", () => {
      expect(spy).toHaveBeenCalledWith({
        id: FACT_ID,
        predicate: UPDATED_PREDICATE,
        objects: OBJECTS
      });
    });

    describe("When the user changes the chip input", () => {
      let spy = jest.fn();
      let wrapper = createWrapper(spy);

      const UPDATED_OBJECTS = ["FOO", "BAR", "BAZ"];

      wrapper.find("#objects").simulate("change", UPDATED_OBJECTS);

      it("Then onChange is called with the updated predicate and 3 objects", () => {
        expect(spy).toHaveBeenCalledWith({
          id: FACT_ID,
          predicate: PREDICATE,
          objects: UPDATED_OBJECTS
        });
      });
    });

    describe("When the removed a object", () => {
      let spy = jest.fn();
      let wrapper = createWrapper(spy);

      const REMOVED_OBJECT = "MEN";
      const UPDATED_OBJECTS = ["WOMEN", "CHILDREN"];
      wrapper.find("#objects").simulate("delete", REMOVED_OBJECT);

      it("Then onChange is called with the updated predicate and 2 objects", () => {
        expect(spy).toHaveBeenCalledWith({
          id: FACT_ID,
          predicate: PREDICATE,
          objects: UPDATED_OBJECTS
        });
      });
    });
  });

  describe("When provided a predicate and objects", () => {
    let spy = jest.fn();
    let wrapper = createWrapper(spy);
    it("Then updates view to display objects", () => {
      expect(wrapper.find("#objects").props().value).toEqual(OBJECTS);
    });
    it("Then updates view to display predicate", () => {
      expect(wrapper.find("#predicate").props().value).toEqual(PREDICATE);
    });
  });
});
