import React from "react";
import ReactDOM from "react-dom";
import {Provider} from "react-redux";
import {applyMiddleware, createStore} from "redux";
import logger from "redux-logger";
import thunk from "redux-thunk";

import {MuiThemeProvider, createMuiTheme} from "@material-ui/core/styles";

import "./index.css";
import App from "./App";
import {itemSetCreator} from "./items/itemSetCreator";

const theme = createMuiTheme({
  typography: {
    fontSize: 16
  }
});
const store = createStore(itemSetCreator, applyMiddleware(logger, thunk));

ReactDOM.render(
  <MuiThemeProvider theme={theme}>
    <Provider store={store}>
      <App />
    </Provider>
  </MuiThemeProvider>,
  document.getElementById("root")
);
