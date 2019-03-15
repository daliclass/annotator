import React, {Component} from "react";
import {connect} from "react-redux";
import {TextAnnotation} from './TextAnnotation.js';
import {shallow} from "enzyme";
import {updatePredicatesAction, itemAnnotatedAction, startingAnnotationAction} from './itemAnnotation.js'

export class Annotation extends Component {

  constructor(props) {
    super(props)
    this.onPredicateChange = this.onPredicateChange.bind(this);
    this.onComplete = this.onComplete.bind(this);
  }

  onPredicateChange(predicates) {
    this.props.dispatch(updatePredicatesAction(predicates))
  }

  onComplete(predicates) {
    this.props.dispatch(itemAnnotatedAction(predicates))
  }

  componentDidMount() {
    this.props.dispatch(startingAnnotationAction())
  }

  render() {
    const AnnotationType = this.props.annotationType;
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

function mapStateToProps(state) {
  return {
    subject: state.itemAnnotation.subject,
    predicatesAndObjects: state.itemAnnotation.predicates
  };
}

export default connect(mapStateToProps)(Annotation);
