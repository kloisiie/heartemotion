import React, {PureComponent} from 'react';
import {StyleSheet, Text, View, Image} from 'react-native';
export default class AlarmRecordItem extends PureComponent {
  render() {
    let name = this.props.item.deviceId;
    let status = '烦躁';
    let time = this.props.item.policeTime;
    return (
      <View style={styles.container}>
        <Text
          style={{fontSize: 16, color: '#20212A', fontWeight: 'bold', flex: 1}}>
          {name}
        </Text>
        <Text
          style={{
            fontSize: 16,
            color: '#FF4E56',
            fontWeight: 'bold',
            marginRight: 32,
          }}>
          {status}
        </Text>
        <Text style={{fontSize: 14, color: '#666'}}>{time}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    height: 60,
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
  },
});
