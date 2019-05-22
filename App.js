import React, {Component} from 'react';
import {FlatList, StyleSheet, Text, View, Button} from 'react-native';
import NetworkState from "./NetworkState";
import ToastExample from "./ToastExample";

type Props = {};
export default class App extends Component<Props> {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true
        }
    }

    componentDidMount() {
        console.log("mount XXX");
        return fetch('https://api.coo.ee/api/v0/todo',
            {
                headers: new Headers({
                    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsImV4cCI6MTU1NTkzMzU1NH0.eyJlbWFpbCI6Inl1cmlAY29vLmVlIiwibmFtZSI6Ill1cmkgU2NoaW1rZSJ9.vU792lp7BknhJ0qoB8DDadd-sSo4N1RRetUXP6RBCqI',
                }),
            })
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState({
                    isLoading: false,
                    todos: responseJson.todos,
                }, function () {
                });
            })
            .catch((error) => {
                console.error(error);
            });
    }

    handleBTNPressed(){
        console.log("toast XXX");
        console.log("xxx " + ToastExample);
        ToastExample.show('Awesome', ToastExample.SHORT);
    }

    render() {
        if (this.state.isLoading) {
            return (
                <View style={styles.container}>
                    <Text>Loading...</Text>
                </View>
            )
        }

        return (
            <View style={{flex: 1, paddingTop: 20}}>
                <NetworkState/>
                <FlatList
                    data={this.state.todos}
                    renderItem={({item}) => <Text>{item.message}</Text>}
                    keyExtractor={({line}, index) => line}
                />
                <Button title='toast' onPress={()=>this.handleBTNPressed()}/>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
});
