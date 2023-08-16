import React, {Component} from 'react';

// Google Maps walking speed and biking speed is 3 mph and 10 mph respectively
// mph * 5280 / 60 gives us ft/min
const walkingSpeed: number = 264
const bikingSpeed: number = 880

interface DistanceProps {
    cost: number
}

/**
 * Text that shows the user the distance of the path, as well as walking/biking speed
 */
export default class Distance extends Component<DistanceProps> {

    /**
     * Constructs a new instance
     * @param props - Properties for the text
     */
    constructor(props: DistanceProps) {
        super(props);
    }

    /**
     * Renders the text
     */
    render() {

        if (this.props.cost == 0) {
            return ""
        } else {
            let feet: number = Math.round(this.props.cost)
            let miles: number = Math.round(this.props.cost / 5280 * 1000) / 1000
            let walk: number = Math.round(this.props.cost / walkingSpeed)
            let bike: number = Math.round(this.props.cost / bikingSpeed)

            // In case any of the numbers is equal to one
            let feetS: string = feet === 1 ? "foot" : "feet"
            let milesS: string = miles === 1 ? "mile" : "miles"
            let walkS: string = walk === 1 ? "minute" : "minutes"
            let bikeS: string = bike === 1 ? "minute" : "minutes"
            return (
                <div id="distance">
                    <h2>
                        Planning a Trip?
                    </h2>
                    This path is {feet} {feetS} long
                            ({miles} {milesS}).
                    <br/>
                    On foot, this will take {walk} {walkS}.
                    <br/>
                    On a bike, this will take {bike} {bikeS}.
                </div>
            )
        }
    }
}