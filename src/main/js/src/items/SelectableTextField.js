import React, {Component} from "react";
import TextField from "@material-ui/core/TextField";
import MenuItem from "@material-ui/core/MenuItem";

class SelectableTextField extends Component {
  constructor(props) {
    super(props);
    this.onChange = this.onChange.bind(this);
  }

  render() {
    return (
      <TextField
        required
        id="type"
        label="Type"
        margin="normal"
        fullWidth
        select
        onChange={this.onChange}
        value={this.props.selectedOption.label}
      >
        {this.props.options.map(option => {
          return (
            <MenuItem key={option.value} id={option.value} value={option.value}>
              {option.label}
            </MenuItem>
          );
        })}
      </TextField>
    );
  }

  onChange(change) {
    let selectedLabel = change.target.value;
    let selectedOption = this.props.options.filter((option) => {
      return option.label === selectedLabel;
    })[0];
    this.props.onChange(selectedOption);
  }
}

export default SelectableTextField;
