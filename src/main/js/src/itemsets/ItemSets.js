import React, {Component} from "react";
import {connect} from "react-redux";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";

export class ItemSets extends Component {
  componentDidMount() {
    this.props.dispatch(this.props.loadItemSets());
  }

  render() {
    return (
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>ID</TableCell>
            <TableCell>Number of items</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {this.props.itemSets.map(itemSet => {
            return (
              <TableRow>
                <TableCell>{itemSet.name}</TableCell>
                <TableCell>{itemSet.uuid}</TableCell>
                <TableCell>{itemSet.numberOfItems}</TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    );
  }
}

function mapStateToProps(state) {
  console.log(state);
  return {
    itemSets: state.itemSetViewer.itemSets
  };
}

export default connect(mapStateToProps)(ItemSets);
