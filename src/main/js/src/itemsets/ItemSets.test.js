import {ItemSets} from "./ItemSets.js";
import {shallow} from "enzyme";
import React from "react";
describe("When mounting the ItemSets", () => {
  it("Then dispatch a action", () => {
    let spy = jest.fn();
    let action = {action: "action"};
    let itemSets = shallow(<ItemSets loadItemSets={()=>action} itemSets={[]} dispatch={spy} />);
    expect(spy).toHaveBeenCalledWith(action);
  });
});
