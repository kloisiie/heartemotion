import React, {PureComponent} from 'react';
import {StyleSheet, Text, View, Image} from 'react-native';
export default class StartAd extends PureComponent {
  render() {
    return <View style={styles.container} />;
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    flex: 1,
  },
});
