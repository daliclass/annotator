import React, {Component} from "react";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import Button from "@material-ui/core/Button";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import {ANNOTATE_ITEM_SET} from "../routes.js";

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
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {this.props.itemSets.map(itemSet => {
            return (
              <TableRow>
                <TableCell>{itemSet.name}</TableCell>
                <TableCell>{itemSet.uuid}</TableCell>
                <TableCell>{itemSet.numberOfItems}</TableCell>
                <TableCell>
                  <Link to={ANNOTATE_ITEM_SET(itemSet.uuid)}>
                    <Button>Annotate {itemSet.numberOfItems} items</Button>
                  </Link>
                </TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    );
  }
}

function mapStateToProps(state) {
  return {
    itemSets: state.itemSetViewer.itemSets
  };
}

export default connect(mapStateToProps)(ItemSets);
