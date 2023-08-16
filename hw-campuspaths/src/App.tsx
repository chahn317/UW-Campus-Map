/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import Edge from "./Edge";
import Map from './Map'
import Path from "./Paths";
import Distance from "./Distance";
import BuildingsList from "./BuildingsList";
import Colors from "./Colors";

/**
 * The state of the app. It stores the path drawn by the user.
 */
interface AppState {
    path: Edge[]
    color: string
    cost: number
    show: boolean
}

/**
 * A class representing the Campus Path Finder app.
 */
export default class App extends Component<{}, AppState> {

    /**
     * Constructs a new app
     * @param props - Properties for the app
     */
    constructor(props: any) {
        super(props);
        this.state = {
            path: [],
            color: "black",
            cost: 0,
            show: false
        };
    }

    /**
     * Renders the entire app
     */
    render() {
        return (
            <div>
                <h1 id="app-title">Campus Path Finder</h1>
                <Path show = {this.state.show}
                    onChange={(value: Edge[], userColor: string, distance: number, toggle: boolean) => {
                        this.setState({path: value, color: userColor, cost: distance, show: toggle})
                    }}
                />
                <Map edges={this.state.path} color = {this.state.color}/>
                <Distance cost={this.state.cost}/>
                <br/>
                <BuildingsList show = {this.state.show}
                               onChange={(toggle: boolean) => {
                                   this.setState({show: toggle})
                               }

                }/>
                <br/>
                <Colors/>
            </div>
        );
    }
}
