import React, {Component} from 'react';

interface BuildingListProps {
    show: boolean
    onChange(toggle: boolean): void;
}

interface BuildingListState {
    toggle: boolean
    buildings: string[]
}

/**
 * A class representing the list of all buildings on campus as well as the button
 * toggling it.
 */
export default class BuildingsList extends Component<BuildingListProps, BuildingListState> {

    constructor(props: any) {
        super(props)
        this.state = {toggle: this.props.show, buildings: []}
    }

    /**
     * Renders the list of buildings and the button toggling it so that if the list
     * is visible, there's the option to hide it, and when it's not visible, there's
     * the option to show it
     */
    render() {
        let button: string = this.props.show ? "Hide Campus Buildings" : "View Campus Buildings";
        return (
            <div id="list">
                <button onClick={
                    this.toggleShow
                }>{button}</button>

                <br/>

                {this.props.show && this.state.buildings.map(item => {
                    return <div>
                        <br/>
                        {item}
                    </div>;
                })}
            </div>
        )
    }

    /**
     * Sets the state of the BuildingList depending on the current boolean value of show
     */
    private readonly toggleShow = async () => {
        try {
            const response = await fetch("http://localhost:4567/buildings")
            if (!response.ok) {
                alert("The buildings cannot be found.")
                return
            }
            this.setState({toggle: !this.props.show, buildings: await response.json() as string[]})
            this.props.onChange(this.state.toggle)
        } catch (e) {
            alert("The buildings cannot be found.")
            return
        }
    }
}