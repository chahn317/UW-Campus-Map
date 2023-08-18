import React, {Component} from 'react';

import Edge from "./Edge";

/**
 * The function called when a path is sent to the parent
 */
interface PathProps {
    show: boolean
    onChange(path: Edge[], color: string, cost: number, toggle: boolean): void;
}

/**
 * The state of the text box, where the text written by the user is stored.
 */
interface PathState {
    text: string;
}

/**
 * The text box and buttons allowing the user to draw a path
 */
export default class Path extends Component<PathProps, PathState> {
    /**
     * Constructs a new text box/buttons
     * @param props - Properties for the text box/buttons
     */
    constructor(props: PathProps) {
        super(props);
        this.state = {text: ""}
    }

    /**
     * Renders the text box and its surrounding buttons
     */
    render() {
        return (
            <div id="path">
                Please enter two buildings and an optional color, separated by a comma.
                <br/>
                Ex: "HUB (West Food), CSE, red"
                <br/>
                <br/>
                Lists of campus buildings and colors are available at the bottom of the page.
                <br/>
                <br/>

                <textarea
                    rows={2}
                    cols={40}
                    value={this.state.text}
                    onChange={(rawText ) => {
                        this.setState({text: rawText.target.value});
                    }}
                />
                <br/>

                <button onClick={async() => {
                    const tokens: string[] = this.state.text.trim().split(",")
                    try {
                        const start: string = tokens[0].trim()
                        const end: string = tokens[1].trim()
                        let color: string = "black"
                        if (tokens.length >= 3) {
                            color = tokens[2].trim()
                            // If the color is invalid, an alert is shown to the user
                            if (!this.isValidColor(color)) {
                                alert("The color is not valid. There is a list available at the bottom of the page.")
                                return
                            }
                        }
                        let response = await fetch(`http://localhost:4567/paths?start=${start}&end=${end}`)
                        let response2 = await fetch(`http://localhost:4567/cost?start=${start}&end=${end}`)
                        if (!response.ok) {
                            // If invalid buildings are entered, an alert is shown to the user
                            alert("Please enter two valid buildings using proper formatting. There is a list available at the bottom of the page.")
                            return
                        }
                        this.props.onChange(await response.json() as Edge[], color, await response2.json() as number, this.props.show)
                    } catch (e) {
                        // If an exception is somehow thrown, an alert is shown to the user
                        alert("Please enter two valid buildings using proper formatting. There is a list available at the bottom of the page.")
                    }
                }}>Path</button>

                <button onClick={() => {
                    this.setState({text: ""})
                    this.props.onChange([], "black", 0, false)
                }}>Clear</button>
            </div>
        )
    }

    private isValidColor(strColor:string) {
        let s = new Option().style;
        s.color = strColor;

        // return 'false' if color wasn't assigned
        return s.color == strColor.toLowerCase();
    }
}