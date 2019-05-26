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
        return fetch('https://api.coo.ee/api/v0/todo',
            {
                headers: new Headers({
                    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsImV4cCI6MTU1NTkzMzU1NH0.eyJlbWFpbCI6Inl1cmlAY29vLmVlIiwibmFtZSI6Ill1cmkgU2NoaW1rZSJ9.vU792lp7BknhJ0qoB8DDadd-sSo4N1RRetUXP6RBCqI',
                }),
            })
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
