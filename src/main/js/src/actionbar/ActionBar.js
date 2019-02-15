import React, {Component} from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import AddIcon from "@material-ui/icons/Add";
import FilterIcon from "@material-ui/icons/Filter";
import IconButton from "@material-ui/core/IconButton";
import {Link} from "react-router-dom";

export class ActionBar extends Component {
  render() {
    return (
      <AppBar style={{position: "relative"}}>
        <Toolbar style={{justifyContent: "space-between"}}>
          <Typography variant="h6" color="inherit">
            Annotate
          </Typography>
          <Toolbar>
            <Link to="/itemset/new">
              <IconButton>
                <AddIcon style={{color: "white"}} />
              </IconButton>
            </Link>
            <Link to="/itemsets">
              <IconButton>
                <FilterIcon style={{color: "white"}} />
              </IconButton>
            </Link>
          </Toolbar>
        </Toolbar>
      </AppBar>
    );
  }
}
