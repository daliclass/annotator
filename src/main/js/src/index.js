import React from "react";
import ReactDOM from "react-dom";
import {Provider} from "react-redux";
import {applyMiddleware, createStore, combineReducers} from "redux";
import {BrowserRouter} from "react-router-dom";
import logger from "redux-logger";
import thunk from "redux-thunk";

import {MuiThemeProvider, createMuiTheme} from "@material-ui/core/styles";

import "./index.css";
import App from "./App";
import {itemSetCreator} from "./items/itemSetCreator";
import {itemSetViewer} from "./itemsets/itemSetViewer";
import {itemAnnotation} from "./annotation/itemAnnotation";

const theme = createMuiTheme({
  typography: {
    fontSize: 16
  }
});
const store = createStore(
  combineReducers({
    itemSetViewer: itemSetViewer,
    itemSetCreator: itemSetCreator,
    itemAnnotation: itemAnnotation
  }),
  applyMiddleware(logger, thunk)
);

ReactDOM.render(
  <BrowserRouter>
    <MuiThemeProvider theme={theme}>
      <Provider store={store}>
        <App />
      </Provider>
    </MuiThemeProvider>
  </BrowserRouter>,
  document.getElementById("root")
);
