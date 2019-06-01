import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View, Button, DeviceEventEmitter } from 'react-native';
import NetworkState from "./NetworkState";

export default class RequestsTable extends Component {
    constructor(props) {
        super(props);

        this.state = {
            results: []
        }
    }

    handleExecutePressed() {
        return fetch('https://api.coo.ee/api/v0/todo')
            // return fetch('https://nghttp2.org/httpbin/get')
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
        return <View style={{ flex: 1 }}>
            <Text style={{fontWeight: 'bold'}}>Responses</Text>

            <FlatList
                ref = "flatList"
                data={this.state.results}
                renderItem={({ item }) => <Text>{item}</Text>}
                keyExtractor={({ }, index) => "" + index}
                onContentSizeChange={(contentWidth, contentHeight)=>{        
                    this.refs.flatList.scrollToEnd({animated: true});
                }}
            />

            <Button title='execute query' onPress={() => this.handleExecutePressed()} />
        </View>;
    }
}