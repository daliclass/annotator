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
  state = {
    predicate: "",
    facts: [],
    factsDisabled: true
  };

  constructor(props) {
    super(props);
    this.onChange = props.onChange;
    this.currentPredicate = null;
    this.onTextFieldChange = this.onTextFieldChange.bind(this);
    this.onFactChange = this.onFactChange.bind(this);
    this.EMPTY_VALUE = "";
  }

  onTextFieldChange(change) {
    this.setState({
      factsDisabled: change.target.value === this.EMPTY_VALUE,
      predicate: change.target.value
    });
  }

  onFactChange(facts) {
    this.setState({
      facts: facts
    });

    if (this.state.predicate !== this.EMPTY_VALUE) {
      let onChangeMessage = {
        predicate: this.state.predicate,
        facts: this.state.facts
      };
      this.onChange(onChangeMessage);
    }
  }

  render() {
    const {classes} = this.props;
    return (
      <section className={classes.root}>
        <TextField
          id="predicate"
          onChange={this.onTextFieldChange}
          label="Predicate and facts *"
          fullWidth
        />
        <ChipInput
          id="facts"
          onChange={chips => this.onFactChange(chips)}
          disabled={false}
          fullWidth
        />
      </section>
    );
  }
}

export default withStyles(styles)(FactSelector);
