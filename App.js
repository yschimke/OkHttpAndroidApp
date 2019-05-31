import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View, Button } from 'react-native';
import NetworkStateTable from "./NetworkStateTable";
import ConnectionPoolStateTable from "./ConnectionPoolStateTable";

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            results: []
        }
    }

    handleExecutePressed() {
        return fetch('https://nghttp2.org/httpbin/get')
            .then((response) => {
                this.setState({
                    results: this.state.results.concat("Success " + response.status)
                }, function () {
                });
            })
            .catch((error) => {
                this.setState({
                    results: this.state.results.concat("Failed " + error)
                }, function () {
                });

                console.log(error);
            });
    }

    render() {
        return (
            <View style={{ flex: 1, paddingTop: 20 }}>
                <NetworkStateTable />

                <ConnectionPoolStateTable />

                <Text>Responses</Text>

                <FlatList
                    data={this.state.results}
                    renderItem={({item}) => <Text>{item}</Text>}
                    keyExtractor={({}, index) => "" + index}
                />

                <Button title='execute query' onPress={() => this.handleExecutePressed()} />
            </View>
        );
    }
}
