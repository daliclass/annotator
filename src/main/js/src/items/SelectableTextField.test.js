import {shallow} from "enzyme";
import SelectableTextField from "./SelectableTextField.js";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import React from "react";

describe("Given the SelectableTextField", () => {
  let wrapper = null;
  let spy = jest.fn();
  const OPTIONS = [{
      label: "a",
      value: "A"
    },
    {
      label: "b",
      value: "B"
    },
    {
      label: "c",
      value: "C"
    },
    {
      label: "d",
      value: "D"
    },
    {
      label: "e",
      value: "E"
    }
  ];

  beforeEach(() => {
    wrapper = shallow(
      <SelectableTextField
        options={OPTIONS}
        selectedOption={OPTIONS[0]}
        onChange={spy} />
    );
  });

  describe("When provided rendering the 5 options", () => {
    it("Then render TextField with 5 children", () => {
      expect(wrapper.find(TextField).children().length).toEqual(5);
    });

    it("Then renders the correct items", () => {
      expect(
        wrapper.containsAllMatchingElements([
          <MenuItem> a </MenuItem>,
          <MenuItem> b </MenuItem>,
          <MenuItem> c </MenuItem>,
          <MenuItem> d </MenuItem>,
          <MenuItem> e </MenuItem>
        ])
      ).toEqual(true);
    });
  });

  describe("When selecting a MenuItem", () => {
    it("Then set the TextField value to the selected value", () => {
      expect(wrapper.find(TextField).props().value).toEqual("a");
    });
  });

  describe("When selecting a MenuItem", () => {
    it("Then call on change with the option", () => {
      wrapper.find(TextField).simulate("change", {
        target: {
          value: "b"
        }
      });
      expect(spy).toHaveBeenCalledWith(OPTIONS[1]);
    });
  });
});
