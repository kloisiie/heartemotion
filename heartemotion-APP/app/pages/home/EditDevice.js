import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TextInput,
  TouchableWithoutFeedback,
  ScrollView,
} from 'react-native';
export default class EditDevice extends PureComponent {
  constructor(props) {
    super();
    let {item} = props.route.params;
    this.state = {
      deviceName: item.deviceName,
      useName: item.wearer ? item.wearer : '',
    };
  }
  render() {
    let {deviceName, useName} = this.state;
    return (
      <View style={styles.container}>
        <View
          style={{
            width: 311,
            height: 247,
            backgroundColor: 'white',
            borderRadius: 8,
            overflow: 'hidden',
            marginTop: 120,
          }}>
          <Text
            style={{
              fontSize: 18,
              color: '#333',
              fontWeight: 'bold',
              marginTop: 20,
              alignSelf: 'center',
            }}>
            设备信息
          </Text>
          <View
            style={{
              marginHorizontal: 24,
              height: 43,
              backgroundColor: '#F8F8F8',
              borderRadius: 4,
              overflow: 'hidden',
              paddingHorizontal: 12,
              marginTop: 20,
            }}>
            <TextInput
              style={{
                flex: 1,
                height: 43,
                padding: 0,
                fontSize: 15,
                color: '#333',
              }}
              placeholderTextColor={'#999'}
              placeholder={'设备名称'}
              value={deviceName}
              onChangeText={(deviceName) => this.setState({deviceName})}
            />
          </View>
          <View
            style={{
              marginHorizontal: 24,
              height: 43,
              backgroundColor: '#F8F8F8',
              borderRadius: 4,
              overflow: 'hidden',
              paddingHorizontal: 12,
              marginTop: 8,
            }}>
            <TextInput
              style={{
                flex: 1,
                height: 43,
                padding: 0,
                fontSize: 15,
                color: '#333',
              }}
              placeholderTextColor={'#999'}
              placeholder={'佩戴者'}
              value={useName}
              onChangeText={(useName) => this.setState({useName})}
            />
          </View>
          <TouchableWithoutFeedback onPress={this._onSure}>
            <View
              style={{
                marginTop: 24,
                marginHorizontal: 24,
                height: 40,
                borderRadius: 20,
                backgroundColor: '#5A60E9',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <Text style={{fontSize: 16, color: '#fff'}}>提交更改</Text>
            </View>
          </TouchableWithoutFeedback>
        </View>
      </View>
    );
  }

  _onSure = () => {
    let {deviceName, useName} = this.state;
    if (deviceName.length == 0 || useName.length == 0) {
      GBPrompt.showToast('设备信息不能为空');
      return;
    }
    let {onSure} = this.props.route.params;
    onSure && onSure(deviceName, useName);
    GBNavgator.goBack(null);
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
  },
});
