import React, {Component} from "react";
import {connect} from "react-redux";
import {
  updatePredicatesAction,
  startingAnnotationAction,
  getFirstItemToAnnotate
} from "./itemAnnotation.js";

export class Annotation extends Component {
  constructor(props) {
    super(props);
    this.onPredicateChange = this.onPredicateChange.bind(this);
    this.onComplete = this.onComplete.bind(this);
  }

  componentDidMount() {
    this.props.dispatch(startingAnnotationAction(this.props.itemSetId));
    this.props.dispatch(getFirstItemToAnnotate(this.props.itemSetId));
  }

  onPredicateChange(predicates) {
    this.props.dispatch(updatePredicatesAction(predicates));
  }

  onComplete(predicates) {
    this.props.dispatch(this.props.onComplete());
  }

  render() {
    const AnnotationType = this.props.annotationType;
    if (this.props.annotationCompleted) {
      return (
        <h2 id="completedAnnotation">
          {" "}
          Good Job! Navigate back to the main menu to annotate something else!{" "}
        </h2>
      );
    } else {
      return (
        <AnnotationType
          subject={this.props.subject}
          potentialFacts={this.props.predicatesAndObjects}
          onChange={this.onPredicateChange} // TODO when state has changed
          onComplete={this.onComplete} // TODO when a item is finished annotation
        />
      );
    }
  }
}

function mapStateToProps(state) {
  return {
    subject: state.itemAnnotation.subject,
    predicatesAndObjects: state.itemAnnotation.predicates,
    annotationCompleted: state.itemAnnotation.annotationCompleted
  };
}

export default connect(mapStateToProps)(Annotation);
