import React, { Component } from 'react';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

configure({ adapter: new Adapter() });

class SelectableTextField extends Component {

    state = {
      value: 'EUR',
    };

  constructor(props) {
    super(props);
    this.options = props.options;
    this.onChange = this.onChange.bind(this);
  }

  render() {
    return(
      <TextField
        required
        id="type"
        label="Type"
        margin="normal"
        fullWidth
        select
        onChange={this.onChange}
        value={this.state.value}>
        {this.props.options.map((option) => {
          return <MenuItem key={option.value} id={option.value} value={option.value} >{option.label}</MenuItem>
        })}
      </TextField>
    )
  }

  onChange(menuItem) {
    let value = menuItem.target.value;
    console.log(value);
    this.setState({'value': value});
  }
}

export default SelectableTextField;