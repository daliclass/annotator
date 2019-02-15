import React, {Component} from "react";
import ItemSetForm from "./items/ItemSetForm.js";
import {ActionBar} from "./actionbar/ActionBar.js";
import {ItemSets} from "./itemsets/ItemSets.js";
import {Switch, Route} from "react-router-dom";
import {
  parseTemplateAction,
  postItemSetAction
} from "./items/itemSetCreator.js";

class App extends Component {
  render() {
    return (
      <div className="App">
        <ActionBar />
        <Switch>
          <Route
            exact
            path="/itemset/new"
            render={props => {
              return (
                <ItemSetForm
                  parseTemplateAction={parseTemplateAction}
                  createItemSetAction={postItemSetAction}
                />
              );
            }}
          />
          <Route exact path="/itemsets" component={ItemSets} />; />
        </Switch>
      </div>
    );
  }
}

export default App;
