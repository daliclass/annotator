import React, {Component} from "react";
import ItemSetForm from "./items/ItemSetForm.js";
import {ActionBar} from "./actionbar/ActionBar.js";
import ItemSets from "./itemsets/ItemSets.js";
import {Switch, Route} from "react-router-dom";
import {CREATE_ITEM_SET_ROUTE, VIEW_ITEM_SETS_ROUTE, ANNOTATE_ITEM_SET_ROUTE} from './routes.js';
import {
  parseTemplateAction,
  postItemSetAction
} from "./items/itemSetCreator.js";
import {TextAnnotation} from './annotation/TextAnnotation.js';
import {getItemSetAction} from "./itemsets/itemSetViewer.js";

class App extends Component {
  render() {
    return (
      <div className="App">
        <ActionBar />
        <Switch>
          <Route
            exact
            path={CREATE_ITEM_SET_ROUTE}
            render={props => {
              return (
                <ItemSetForm
                  parseTemplateAction={parseTemplateAction}
                  createItemSetAction={postItemSetAction}
                />
              );
            }}
          />
          <Route
            exact
            path={VIEW_ITEM_SETS_ROUTE}
            render={props => {
              return <ItemSets loadItemSets={getItemSetAction} />;
            }}
          />
          <Route
            exact
            path={ANNOTATE_ITEM_SET_ROUTE}
            render={props => {
              return <TextAnnotation itemSetId={props.match.params.id}/>
            }}
          />
        </Switch>
      </div>
    );
  }
}

export default App;
