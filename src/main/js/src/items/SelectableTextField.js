import React, {Component} from "react";
import TextField from "@material-ui/core/TextField";
import MenuItem from "@material-ui/core/MenuItem";

class SelectableTextField extends Component {
  constructor(props) {
    super(props);
    this.options = props.options;
    this.onChange = this.onChange.bind(this);
    this.state = {
      value: "value"
    };
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
        value={this.state.value}
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

  onChange(menuItem) {
    let value = menuItem.target.value;
    this.setState({
      value: value
    });
  }
}

export default SelectableTextField;
