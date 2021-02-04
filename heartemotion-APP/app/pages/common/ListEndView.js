import React from 'react';
import {View, StyleSheet, Text} from 'react-native';
export default class ListEndView extends React.PureComponent {
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.text}>- 到底啦 -</Text>
      </View>
    );
  }
}
const styles = StyleSheet.create({
  container: {
    backgroundColor: '#F8F8F8',
    height: 43,
    justifyContent: 'center',
    alignItems: 'center',
  },
  text: {
    fontSize: 13,
    color: '#999',
  },
});
