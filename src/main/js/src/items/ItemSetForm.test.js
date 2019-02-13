import React from "react";

import {shallow} from "enzyme";

import {ItemSetForm, formStyles} from "./ItemSetForm.js";
import FactSelector from "./FactSelector.js";
import {uploadTemplateAction} from "./itemSetCreator.js";

function renderWrapper(spy) {
  return shallow(
    <ItemSetForm
      dispatch={spy}
      facts={[{id: 1, predicate: "", objects: []}]}
      classes={formStyles}
      parseTemplateAction={uploadTemplateAction}
    />
  );
}

describe("When a user changes the item set name", () => {
  let spy = jest.fn();
  let wrapper = shallow(
    <ItemSetForm dispatch={spy} facts={[]} classes={formStyles} />
  );

  wrapper.find("#itemSetName").simulate("change", {target: {value: "a"}});
  const EXPECTED_ACTION = {
    type: "SET_NAME",
    payload: {
      value: "a"
    }
  };
  it("then call on name change with a SET_NAME action", () => {
    expect(spy).toHaveBeenCalledWith(EXPECTED_ACTION);
  });
});

describe("when a user enters a fact then", () => {
  let spy = jest.fn();
  let wrapper = renderWrapper(spy);

  wrapper.find(FactSelector).simulate("change", {
    id: 1,
    predicate: "suitable_for",
    objects: ["foo", "bar", "baz"]
  });

  const EXPECTED_ACTION = {
    type: "SET_FACT",
    payload: {
      id: 1,
      predicate: "suitable_for",
      objects: ["foo", "bar", "baz"]
    }
  };

  it("then call on fact change with a SET_FACT action", () => {
    expect(spy).toHaveBeenCalledWith(EXPECTED_ACTION);
  });
});

describe("When a user selects a type", () => {
  let spy = jest.fn();
  let wrapper = renderWrapper(spy);

  wrapper.find("#typeSelector").simulate("change", {
    label: "b",
    value: "B"
  });

  const EXPECTED_ACTION = {
    type: "SET_TYPE",
    payload: {
      label: "b",
      value: "B"
    }
  };

  it("then call with a SET_TYPE action", () => {
    expect(spy).toHaveBeenCalledWith(EXPECTED_ACTION);
  });
});

describe("When a user downloads a template", () => {
  let spy = jest.fn();
  let wrapper = renderWrapper(spy);

  wrapper.find("#downloadButton").simulate("click");

  const EXPECTED_ACTION = {
    type: "DOWNLOAD_TEMPLATE",
    payload: undefined
  };

  it("then call onDownload with a download action", () => {
    expect(spy).toHaveBeenCalledWith(EXPECTED_ACTION);
  });
});

describe("When a user uploads a template", () => {
  let spy = jest.fn();
  let wrapper = renderWrapper(spy);

  wrapper.find("#csvUpload").simulate("change", [{}]);

  const EXPECTED_ACTION = {
    type: "UPLOAD_TEMPLATE",
    payload: {}
  };

  it("then call uploadTemplate with a upload action", () => {
    expect(spy).toHaveBeenCalledWith(EXPECTED_ACTION);
  });
});

describe("When a user cancels a template upload", () => {
  let spy = jest.fn();
  let wrapper = renderWrapper(spy);

  wrapper.find("#csvUpload").simulate("change", [undefined]);

  const EXPECTED_ACTION = {
    type: "UPLOAD_TEMPLATE",
    payload: undefined
  };

  it("then call uploadTemplate with undefined", () => {
    expect(spy).toHaveBeenCalledWith(EXPECTED_ACTION);
  });
});

describe("When a user creates the items set", () => {
  let spy = jest.fn();
  let wrapper = renderWrapper(spy);

  wrapper.find("#createItemSet").simulate("click");

  const EXPECTED_ACTION = {
    type: "CREATE_ITEM_SET",
    payload: undefined
  };

  it("then call onDownload with a download action", () => {
    expect(spy).toHaveBeenCalledWith(EXPECTED_ACTION);
  });
});
