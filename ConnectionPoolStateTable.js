import React, {Component} from 'react';
import {FlatList, StyleSheet, Text, View, Button, DeviceEventEmitter} from 'react-native';
import NetworkState from "./NetworkState";

export default class ConnectionPoolStateTable extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isLoading: true,
            connections: {}
        }

        this.onStateEvent = this.onStateEvent.bind(this);
    }

    onStateEvent(connections) {
        this.setState({
            isLoading: false,
            connections: connections,
        }, () => {});
    }
    
    componentDidMount() {
        NetworkState.getConnections().then(this.onStateEvent);

        this.subscription = DeviceEventEmitter.addListener('connectionPoolStateChanged', this.onStateEvent);
    }

    componentWillUnmount() {
        this.subscription.remove();
    }

    render() {
        if (this.state.isLoading) {
            return (
                <View style={{flex: 1, paddingTop: 20}}>
                    <Text>Loading...</Text>
                </View>
            )
        }

        return <View style={{flex: 1, paddingTop: 20}}>
            <Text>Connection Count: {this.state.connections.connectionsCount}</Text>
            <Text>Idle Connection Count: {this.state.connections.idleConnectionsCount}</Text>
            <Text>Connections</Text>
            <FlatList
                data={this.state.connections.connections}
                renderItem={({item}) => <Text>{item.destHost} {item.proxy} {item.host} {item.localAddress}</Text>}
                keyExtractor={({id}, index) => id}
            />
        </View>;
    }
}