import React, {Component} from "react";
import ItemSetForm from "./items/ItemSetForm.js";
import {
  parseTemplateAction,
  postItemSetAction
} from "./items/itemSetCreator.js";

class App extends Component {
  render() {
    return (
      <div className="App">
        <ItemSetForm
          parseTemplateAction={parseTemplateAction}
          createItemSetAction={postItemSetAction}
        />
      </div>
    );
  }
}

export default App;
