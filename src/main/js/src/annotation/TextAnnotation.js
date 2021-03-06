import React, {Component} from "react";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Button from "@material-ui/core/Button";
import _ from "lodash";

export class TextAnnotation extends Component {
  constructor(props) {
    super(props);
    this.checkboxChanged = this.checkboxChanged.bind(this);
    this.submitAnnotation = this.submitAnnotation.bind(this);
  }

  render() {
    return (
      <section>
        <h1>{this.props.subject}</h1>
        {this.props.potentialFacts.map(potentialFact => {
          return (
            <section>
              <h2> {potentialFact.predicate} </h2>
              {potentialFact.objects.map(object => {
                return (
                  <FormControlLabel
                    control={
                      <Checkbox
                        onChange={this.checkboxChanged(this, object)}
                        value={object.object}
                        checked={object.selected}
                      />
                    }
                    label={object.object}
                  />
                );
              })}
            </section>
          );
        })}
        <Button onClick={this.submitAnnotation(this.props.potentialFacts)}>
          submit annotation
        </Button>
      </section>
    );
  }

  submitAnnotation(potentialFacts) {
    return () => {
      this.props.onComplete(potentialFacts);
    };
  }

  checkboxChanged(that, changedItem) {
    return (event, checked) => {
      let predicatesLatest = _.cloneDeep(that.props.potentialFacts).map(
        predicate => {
          predicate.objects = predicate.objects.map(object => {
            if (
              object.object === changedItem.object &&
              object.id === changedItem.id
            ) {
              object.selected = checked;
            }
            return object;
          });
          return predicate;
        }
      );
      that.props.onChange(predicatesLatest);
    };
  }
}
