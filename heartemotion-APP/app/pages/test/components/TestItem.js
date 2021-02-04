import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
} from 'react-native';
import imageRes from '../../../res/imgeRes';
export default class TestItem extends PureComponent {
  render() {
    let {fileName, startTime, endTime} = this.props.item;
    let name = fileName;
    let isSelect = this.props.isSelect;
    let time = `${startTime} - ${endTime}`;
    return (
      <TouchableWithoutFeedback onPress={this.props.onItem}>
        <View style={styles.container}>
          <View
            style={{
              flexDirection: 'row',
              alignItems: 'center',
              justifyContent: 'space-between',
              marginLeft: 8,
              marginTop: 16,
              marginRight: 16,
            }}>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <Image
                source={
                  isSelect
                    ? imageRes.icon_select_22_pre
                    : imageRes.icon_select_22_nor
                }
              />
              <Text
                style={{
                  fontSize: 16,
                  color: '#333',
                  fontWeight: 'bold',
                  marginLeft: 6,
                }}>
                {name}
              </Text>
            </View>
          </View>
          <Text
            style={{
              marginLeft: 36,
              fontSize: 13,
              color: '#666',
              marginTop: 12,
              marginRight: 8,
            }}>
            {time}
          </Text>
        </View>
      </TouchableWithoutFeedback>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    marginHorizontal: 16,
    marginBottom: 14,
    height: 84,
    borderRadius: 8,
    overflow: 'hidden',
  },
});
