import React, {Component} from "react";
import {connect} from "react-redux";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {DropzoneArea} from "material-ui-dropzone";
import {withStyles} from "@material-ui/core/styles";

import SelectableTextField from "./SelectableTextField.js";
import FactSelector from "./FactSelector.js";
import {
  setNameAction,
  setTypeAction,
  setFactAction,
  uploadTemplateAction,
  downloadTemplateAction
} from "./itemSetCreator.js";

export const formStyles = {
  text: {
    "font-size": "1rem",
    "font-weight": 500,
    "font-family": '"Roboto", "Helvetica", "Arial", sans-serif'
  },
  uploadBox: {
    "min-height": "150px"
  }
};

const styles = theme => formStyles;

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
    this.props.dispatch(this.props.createItemSetAction());
  }

  onUpload(file) {
    if (file.length === 0) {
      this.props.dispatch(uploadTemplateAction(undefined));
    } else {
      this.props.dispatch(this.props.parseTemplateAction(file[0]));
    }
  }

  onDownload() {
    this.props.dispatch(downloadTemplateAction());
  }

  onTypeChange(option) {
    this.props.dispatch(setTypeAction(option.label, option.value));
  }

  onFactChange(fact) {
    this.props.dispatch(setFactAction(fact.id, fact.predicate, fact.objects));
  }

  onNameChange(change) {
    this.props.dispatch(setNameAction(change.target.value));
  }

  render() {
    const MB = 1000000;
    const {classes} = this.props;
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
        <Button
          label=""
          id="downloadButton"
          onClick={this.onDownload}
          href={process.env.PUBLIC_URL + "/text_annotation_template.csv"}
          download
        >
          DOWNLOAD TEMPLATE
        </Button>
        <DropzoneArea
          id="csvUpload"
          onChange={this.onUpload}
          filesLimit={1}
          maxFileSize={10 * MB}
          dropzoneText="Upload CSV of Items to Annotate"
          showAlerts={false}
          dropzoneParagraphClass={classes.text}
          dropZoneClass={classes.uploadBox}
          acceptedFiles={["text/csv"]}
        />
        <Button id="createItemSet" onClick={this.createItemSet}>
          Create item set
        </Button>
      </form>
    );
  }
}

const mapStateToProps = function(state) {
  return {
    name: state.itemSetCreator.name,
    type: state.itemSetCreator.type,
    options: state.itemSetCreator.options,
    facts: state.itemSetCreator.facts
  };
};

export default withStyles(styles)(connect(mapStateToProps)(ItemSetForm));
