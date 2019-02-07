import React, {Component} from "react";
import ItemSetForm from "./items/ItemSetForm.js";


class App extends Component {
  render() {
    return (
      <div className="App">
        <ItemSetForm
            facts={{predicate: "suitable for", facts: ["men"]}}
        />
      </div>
    );
  }
}

export default App;
