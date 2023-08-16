import React, {Component} from 'react';

/**
 * A button that navigates to the list of available colors on another website
 */
export default class Colors extends Component<{}> {

    /**
     * Renders the button
     */
    render() {
        return (
            <div id="list">
                <button onClick={() => {
                    window.open("https://www.w3.org/wiki/CSS/Properties/color/keywords", "_blank");
                }}>View Colors</button>
            </div>
        )
    }
}