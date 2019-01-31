import React, {Component} from "react";
import TextField from "@material-ui/core/TextField";

import SelectableTextField from "./SelectableTextField.js";
import FactSelector from "./FactSelector.js";

class ItemSetForm extends Component {
  render() {
    return (
      <form noValidate autoComplete="off">
        <TextField
          required
          id="set-name"
          label="Set Name"
          margin="normal"
          fullWidth
        />
        <SelectableTextField
          options={[
            {
              label: "a",
              value: "A"
            },
            {
              label: "b",
              value: "B"
            },
            {
              label: "c",
              value: "C"
            },
            {
              label: "d",
              value: "D"
            },
            {
              label: "e",
              value: "E"
            }
          ]}
        />
        <FactSelector
          onChange={change => {
            console.log(change);
          }}
        />
      </form>
    );
  }
}

export default ItemSetForm;
