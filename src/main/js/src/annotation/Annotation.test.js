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

describe("When component is mounted", () => {
  it("Then call dispatch with start annotation", () => {
    let dispatchSpy = jest.fn();
    let annotation = shallow(
      <Annotation
        annotationType={TextAnnotation}
        subject="subject"
        predicates={PREDICATES}
        dispatch={dispatchSpy}
        itemSetId="itemSetId"
      />
    );
    const expectedAction = {
      type: "STARTNG_ANNOTATION",
      payload: {itemSetId: "itemSetId"}
    };
    expect(dispatchSpy).toHaveBeenCalledWith(expectedAction);
  });
});

describe("When predicates change", () => {
  it("Then call dispatch with the predicates", () => {
    const expectedAction = {
      type: "UPDATED_PREDICATES",
      payload: PREDICATES
    };
    let dispatchSpy = jest.fn();
    let annotation = shallow(
      <Annotation
        annotationType={TextAnnotation}
        subject="subject"
        predicates={PREDICATES}
        dispatch={dispatchSpy}
      />,
      {disableLifecycleMethods: true}
    );
    annotation.find(TextAnnotation).simulate("change", PREDICATES);
    expect(dispatchSpy).toHaveBeenCalledWith(expectedAction);
  });
});

describe("When item has been annotated", () => {
  it("Then call and dispatch the output from onComplete", () => {
    const expectedAction = {
      type: "ITEM_ANNOTATED",
      payload: PREDICATES
    };
    let dispatchSpy = jest.fn();
    let onCompletedispatchSpy = jest.fn();
    let annotation = shallow(
      <Annotation
        annotationType={TextAnnotation}
        subject="subject"
        predicates={PREDICATES}
        dispatch={dispatchSpy}
        onComplete={onCompletedispatchSpy}
      />,
      {disableLifecycleMethods: true}
    );
    annotation.find(TextAnnotation).simulate("complete", PREDICATES);
    expect(onCompletedispatchSpy).toHaveBeenCalled();
    expect(dispatchSpy).toHaveBeenCalledWith(undefined);
  });
});

describe("When annotation is completed", () => {
  it("Then render information to the user", () => {
    let annotation = shallow(
      <Annotation
        annotationType={TextAnnotation}
        subject="subject"
        predicates={PREDICATES}
        annotationCompleted={true}
      />,
      {disableLifecycleMethods: true}
    );
    expect(annotation.find("#completedAnnotation").length).toEqual(1);
    expect(annotation.find(TextAnnotation).length).toEqual(0);
  });
});
