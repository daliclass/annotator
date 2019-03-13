import React, {Component} from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import AddIcon from "@material-ui/icons/Add";
import FilterIcon from "@material-ui/icons/Filter";
import IconButton from "@material-ui/core/IconButton";
import {Link} from "react-router-dom";
import {CREATE_ITEM_SET_ROUTE, VIEW_ITEM_SETS_ROUTE} from '../routes.js';

export class ActionBar extends Component {
  render() {
    return (
      <AppBar style={{position: "relative"}}>
        <Toolbar style={{justifyContent: "space-between"}}>
          <Typography variant="h6" color="inherit">
            Annotate
          </Typography>
          <Toolbar>
            <Link to={CREATE_ITEM_SET_ROUTE}>
              <IconButton>
                <AddIcon style={{color: "white"}} />
              </IconButton>
            </Link>
            <Link to={VIEW_ITEM_SETS_ROUTE}>
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
