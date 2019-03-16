import React from "react";
import {Annotation} from "./Annotation.js";
import {TextAnnotation} from "./TextAnnotation.js";
import {shallow} from "enzyme";

const PREDICATES = [
  {
    predicate: "Leaning",
    objects: [
      {object: "Left", id: 0, selected: false},
      {object: "Right", id: 0, selected: false},
      {object: "center", id: 0, selected: false}
    ]
  },
  {
    predicate: "Suitable for",
    objects: [
      {object: "0-25", id: 1, selected: false},
      {object: "25-50", id: 1, selected: false},
      {object: "51+", id: 0, selected: false}
    ]
  }
];

describe("When mounted", () => {
  it("Then call dispatch with start annotation", () => {
    let spy = jest.fn();
    let annotation = shallow(
      <Annotation
        annotationType={TextAnnotation}
        subject="subject"
        predicates={PREDICATES}
        dispatch={spy}
        itemSetId="itemSetId"
      />
    );
    const expectedAction = {
      type: "STARTNG_ANNOTATION",
      payload: {itemSetId: "itemSetId"}
    };
    expect(spy).toHaveBeenCalledWith(expectedAction);
  });
});

describe("When predicates change", () => {
  it("Then update predicates", () => {
    const expectedAction = {
      type: "UPDATED_PREDICATES",
      payload: PREDICATES
    };
    let spy = jest.fn();
    let annotation = shallow(
      <Annotation
        annotationType={TextAnnotation}
        subject="subject"
        predicates={PREDICATES}
        dispatch={spy}
      />,
      {disableLifecycleMethods: true}
    );
    annotation.find(TextAnnotation).simulate("change", PREDICATES);
    expect(spy).toHaveBeenCalledWith(expectedAction);
  });
});

describe("When item has been annotated", () => {
  it("Then dispatch item annotated", () => {
    const expectedAction = {
      type: "ITEM_ANNOTATED",
      payload: PREDICATES
    };
    let spy = jest.fn();
    let annotation = shallow(
      <Annotation
        annotationType={TextAnnotation}
        subject="subject"
        predicates={PREDICATES}
        dispatch={spy}
      />,
      {disableLifecycleMethods: true}
    );
    annotation.find(TextAnnotation).simulate("complete", PREDICATES);
    expect(spy).toHaveBeenCalledWith(expectedAction);
  });
});
