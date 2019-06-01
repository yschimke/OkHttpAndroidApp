import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View, Button, DeviceEventEmitter } from 'react-native';
import NetworkState from "./NetworkState";

export default class EventsTable extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isLoading: true,
            events: []
        }

        this.onStateEvent = this.onStateEvent.bind(this);
    }

    onStateEvent(networks) {
        this.setState({
            isLoading: false,
            events: networks.events,
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
            return <View style={{ flex: 1 }}>
                    <Text style={{fontWeight: 'bold'}}>Events</Text>
                    <Text>Loading...</Text>
                </View>;
        }

        return <View style={{ flex: 1 }}>
            <View>
                <Text style={{fontWeight: 'bold'}}>Events</Text>
                <FlatList
                    ref = "flatList"
                    data={this.state.events}
                    renderItem={({ item }) => <Text>{item.networkId} {item.event}</Text>}
                    keyExtractor={({ id }, index) => id}
                    onContentSizeChange={(contentWidth, contentHeight)=>{        
                        this.refs.flatList.scrollToEnd({animated: true});
                    }}
                />
            </View>
        </View>;
    }
}