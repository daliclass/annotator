import React, { Component } from 'react';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import ItemSetForm from './items/ItemSetForm.js'

class App extends Component {
  render() {
    const { classes } = this.props;

    return (
      <div className="App">
        <ItemSetForm />
      </div>
    );
  }
}

export default App;
