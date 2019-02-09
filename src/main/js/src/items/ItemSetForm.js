import React, {Component} from "react";
import {connect} from "react-redux";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

import SelectableTextField from "./SelectableTextField.js";
import FactSelector from "./FactSelector.js";
import { setNameAction, setTypeAction, setFactAction } from './itemSetCreator.js';

export class ItemSetForm extends Component {
  constructor(props) {
    super(props);
    this.onNameChange = this.onNameChange.bind(this);
    this.onFactChange = this.onFactChange.bind(this);
    this.onTypeChange = this.onTypeChange.bind(this);
    this.onDownload = this.onDownload.bind(this);
    this.onUpload = this.onUpload.bind(this);
    this.createItemSet = this.createItemSet.bind(this);
  }

  createItemSet() {
    this.props.dispatch({
      type: "CREATE_ITEM_SET",
      payload: undefined
    });
  }

  onUpload() {
    this.props.dispatch({
      type: "UPLOAD_TEMPLATE",
      payload: undefined
    });
  }

  onDownload() {
    this.props.dispatch({
      type: "DOWNLOAD_TEMPLATE",
      payload: undefined
    });
  }

  onTypeChange(option) {
    this.props.dispatch(setTypeAction(option.label, option.value))
  }

  onFactChange(fact) {
    this.props.dispatch(setFactAction(fact.id, fact.predicate, fact.objects))
  }

  onNameChange(change) {
    this.props.dispatch(setNameAction(change.target.value));
  }

  render() {
    return (
      <form noValidate autoComplete="off">
        <TextField
          required
          id="itemSetName"
          label="Set Name"
          margin="normal"
          onChange={this.onNameChange}
          value={this.props.name}
          fullWidth
        />
        <SelectableTextField
          id="typeSelector"
          onChange={this.onTypeChange}
          selectedOption={this.props.type}
          options={this.props.options}
        />
        {this.props.facts.map(fact => {
          return (
            <FactSelector
              id={fact.id}
              key={fact.id}
              onChange={this.onFactChange}
              predicate={fact.predicate}
              objects={fact.objects}
            />
          );
        })}
        <Button label="" id="downloadButton" onClick={this.onDownload}>
          Download upload template
        </Button>
        <Button id="uploadButton" onClick={this.onUpload}>
          Upload template
        </Button>
        <Button id="createItemSet" onClick={this.createItemSet}>
          Create item set
        </Button>
      </form>
    );
  }
}

const mapStateToProps = function(state) {
  console.log(state);
  return {
    name: state.name,
    type: state.type,
    options: state.options,
    facts: state.facts
  };
};

export default connect(mapStateToProps)(ItemSetForm);
