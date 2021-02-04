import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
} from 'react-native';
import imageRes from '../../res/imgeRes';
export default class ChooseFileType extends PureComponent {
  render() {
    return (
      <View style={styles.container}>
        <View style={styles.bgView}>
          <TouchableWithoutFeedback onPress={() => this._onChoose(1)}>
            <View
              style={{
                width: 108,
                height: 108,
                backgroundColor: '#F8F8F8',
                borderRadius: 8,
                overflow: 'hidden',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <Image source={imageRes.icon_localfile_40} />
              <Text style={{marginTop: 4, fontSize: 13, color: '#333'}}>
                本地文件
              </Text>
            </View>
          </TouchableWithoutFeedback>
          <TouchableWithoutFeedback onPress={() => this._onChoose(2)}>
            <View
              style={{
                width: 108,
                height: 108,
                backgroundColor: '#F8F8F8',
                borderRadius: 8,
                overflow: 'hidden',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <Image source={imageRes.icon_cloudfile_40} />
              <Text style={{marginTop: 4, fontSize: 13, color: '#333'}}>
                云端文件
              </Text>
            </View>
          </TouchableWithoutFeedback>
        </View>
      </View>
    );
  }
  _onChoose = (type) => {
    GBNavgator.goBack(null);
    let {onChoose} = this.props.route.params;
    onChoose && onChoose(type);
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  bgView: {
    width: 311,
    height: 195,
    backgroundColor: 'white',
    borderRadius: 8,
    overflow: 'hidden',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-around',
  },
});
