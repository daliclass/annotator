import React from "react";
import ReactDOM from "react-dom";
import {Provider} from "react-redux";
import {applyMiddleware, createStore} from "redux";
import logger from 'redux-logger'
import "./index.css";
import App from "./App";

const store = createStore(
  (
    state = {
      name: "product rater",
      type: {label: "text", value: "text"},
      options: [
        {label: "text", value: "text"},
        {label: "image", value: "image"}
      ],
      facts: [
        {id: 1, predicate:"suitable_for", objects: ["MALES", "FEMALES"]},
        {id: 2, predicate:"is good value out of 5", objects: [1, 2, 3, 4, 5 ]}
      ]
    },
    action
  ) => {
    return state;
  }, applyMiddleware(logger)
);

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);
