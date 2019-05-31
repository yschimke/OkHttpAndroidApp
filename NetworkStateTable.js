import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View, Button, DeviceEventEmitter } from 'react-native';
import NetworkState from "./NetworkState";

export default class NetworkStateTable extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isLoading: true,
            networks: {}
        }

        this.onStateEvent = this.onStateEvent.bind(this);
    }

    onStateEvent(networks) {
        this.setState({
            isLoading: false,
            networks: networks,
        }, () => { });
    }

    componentDidMount() {
        NetworkState.getNetworks().then(this.onStateEvent);

        this.subscription = DeviceEventEmitter.addListener('networkStateChanged', this.onStateEvent);
    }

    componentWillUnmount() {
        this.subscription.remove();
    }

    render() {
        if (this.state.isLoading) {
            return (
                <View style={{ flex: 1, paddingTop: 20 }}>
                    <Text>Loading...</Text>
                </View>
            )
        }

        // let events = null;

        // if (this.props.showEvents) {
        //     events = <Div>
        //         <Text>Active Network: {this.state.networks.activeNetwork}</Text>
        //         <Text>Events</Text>
        //         <FlatList
        //             data={this.state.networks.events}
        //             renderItem={({ item }) => <Text>{item.networkId} - {item.event}</Text>}
        //             keyExtractor={({ id }, index) => id}
        //         />
        //     </Div>;
        // }

        return <View style={{ flex: 1, paddingTop: 20 }}>
                <Text>Networks</Text>
                <FlatList
                    data={this.state.networks.networks}
                    renderItem={({ item }) => <Text>
                        {item.networkId} {item.name} {item.active} {item.connected ? "Connected" : "Disconnected"} {item.type} {item.active ? "Active" : "Not Active"} {item.state} {item.localAddress}
                    </Text>}
                    keyExtractor={({ networkId }, index) => networkId}
                />
        </View>;
    }
}