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
import Edge from "./Edge";

const start = "Replace this text with an edge in the following format on each " +
                     "line: \nx1 y1 x2 y2 color"

interface EdgeListProps {
    onChange(edges: Edge[]): void;  // called when a new edge list is ready
                                 // DONE: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

interface EdgeListState {
    text: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {text: start}
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    value={this.state.text}
                    onChange={(rawText ) => {
                        this.setState({text: rawText.target.value});
                    }}
                /> <br/>
                <button onClick={() => {
                    const edges: Edge[] = []
                    const lines: string[] = this.state.text.trim().split("\n")
                    for (let i = 0; i < lines.length; i++) {
                        const tokens: string[] = lines[i].trim().split(" ")

                        // If there are not exactly 5 tokens per line, the edges are not
                        // in the correct format, so an alert is shown to the user
                        if (tokens.length != 5) {
                            alert("Edges must be in the format: x1 y1 x2 y2 color")
                            return;
                        }
                        const coordinates: number[] = []
                        for (let i = 0; i < tokens.length - 1; i++) {
                            const num: number = parseInt(tokens[i])

                            // If the token is not a number, an alert is shown to the
                            // user
                            if (isNaN(num)) {
                                alert("Coordinates must be numbers")
                                return;
                            }

                            // If any of the coordinates are out of bounds, an alert is
                            // shown to the user
                            if (num < 0 || num > 4000) {
                                alert("Coordinates should be between 0 and 4000 inclusive")
                                return;
                            }
                            coordinates.push(num)
                        }
                        const edge: Edge = {x1: coordinates[0],
                            y1: coordinates[1],
                            x2: coordinates[2],
                            y2: coordinates[3],
                            color: tokens[4]}
                        edges.push(edge)
                    }
                    this.props.onChange(edges)
                }}>Draw</button>
                <button onClick={() => {
                    this.setState({text: ""})
                    this.props.onChange([])
                }}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
