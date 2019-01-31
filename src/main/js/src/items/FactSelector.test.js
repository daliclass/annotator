import {shallow} from "enzyme";
import React from "react";
import FactSelector from "./FactSelector.js";

describe("Given the fact selector has no options", () => {
  describe("When the user adds 1 predicate and 3 objects then", () => {
    let spy = jest.fn();
    let wrapper = shallow(<FactSelector onChange={spy} />).dive();

    wrapper.find("#predicate").simulate("change", {
      target: {
        value: "suitable for"
      }
    });
    wrapper.find("#facts").simulate("change", ["men", "women", "children"]);

    it("Then onChange is called with the predicate and 3 objects", () => {
      expect(spy).toHaveBeenCalledWith({
        predicate: "suitable for",
        facts: ["men", "women", "children"]
      });
    });
  });

  describe("When the user tries to set 3 objects without a predicate", () => {
    let spy = jest.fn();
    let wrapper = shallow(<FactSelector onChange={spy} />).dive();

    wrapper.find("#facts").simulate("change", ["men", "women", "children"]);

    it("Then onChange is called with the predicate and 3 objects", () => {
      expect(spy).not.toHaveBeenCalled();
    });
  });
});
