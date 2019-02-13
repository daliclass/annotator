import React, {Component} from "react";
import ItemSetForm from "./items/ItemSetForm.js";
import {parseTemplateAction} from "./items/itemSetCreator.js";

class App extends Component {
  render() {
    return (
      <div className="App">
        <ItemSetForm
          facts={{predicate: "suitable for", facts: ["men"]}}
          parseTemplateAction={parseTemplateAction}
        />
      </div>
    );
  }
}

export default App;
