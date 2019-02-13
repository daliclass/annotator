import React, {Component} from "react";

import TextField from "@material-ui/core/TextField";
import ChipInput from "material-ui-chip-input";
import {withStyles} from "@material-ui/core/styles";

const styles = theme => ({
  root: {
    display: "flex"
  }
});

class FactSelector extends Component {
  constructor(props) {
    super(props);
    this.onTextFieldChange = this.onTextFieldChange.bind(this);
    this.onFactChange = this.onFactChange.bind(this);
    this.onObjectDelete = this.onObjectDelete.bind(this);
  }

  onTextFieldChange(change) {
    this.props.onChange({
      id: this.props.id,
      predicate: change.target.value,
      objects: this.props.objects
    });
  }

  onFactChange(objects) {
    this.props.onChange({
      id: this.props.id,
      predicate: this.props.predicate,
      objects: objects
    });
  }

  onObjectDelete(objectToDelete) {
    let objects = this.props.objects.reduce((acc, object) => {
      if (object !== objectToDelete) {
        acc.push(object);
      }
      return acc;
    }, []);
    this.onFactChange(objects);
  }

  render() {
    const {classes} = this.props;
    return (
      <section className={classes.root}>
        <TextField
          id="predicate"
          onChange={this.onTextFieldChange}
          label="Predicate and objects *"
          fullWidth
          value={this.props.predicate}
        />
        <ChipInput
          id="objects"
          onChange={chips => this.onFactChange(chips)}
          disabled={false}
          fullWidth
          value={this.props.objects}
          onDelete={this.onObjectDelete}
        />
      </section>
    );
  }
}

export default withStyles(styles)(FactSelector);
