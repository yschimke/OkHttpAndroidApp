import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View, Button } from 'react-native';
import NetworkStateTable from "./NetworkStateTable";
import ConnectionPoolStateTable from "./ConnectionPoolStateTable";
import EventsTable from './EventsTable';
import RequestsTable from './RequestsTable';

export default class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <View style={{ flex: 1, flexDirection: 'column', padding: 10, justifyContent: 'space-between' }}>
            <NetworkStateTable />
            <ConnectionPoolStateTable />
            <EventsTable />
            <RequestsTable />
        </View>;
    }
}
