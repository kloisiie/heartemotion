import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
  ActivityIndicator,
} from 'react-native';
import imageRes from '../../../res/imgeRes';
export default class SearchDeviceItem extends PureComponent {
  render() {
    let {name, id, connectIng} = this.props.item;
    let isConnect = this.props.isConnect;
    return (
      <View style={styles.container}>
        <View style={{flexDirection: 'row'}}>
          <Image source={imageRes.icon_system2_24_perple} />
          <View style={{marginLeft: 8}}>
            <Text style={{fontSize: 15, color: '#333', fontWeight: 'bold'}}>
              {name}
            </Text>
            <Text style={{fontSize: 13, color: '#666', marginTop: 2}}>
              {id}
            </Text>
          </View>
        </View>
        {!connectIng ? null : (
          <View style={{flexDirection: 'row', alignItems: 'center'}}>
            <ActivityIndicator
              color={'#C8C9CC'}
              animating={true}
              style={{width: 14, height: 14}}
            />
            <Text style={{fontSize: 14, color: '#969799', marginLeft: 4}}>
              配对中...
            </Text>
          </View>
        )}
        {connectIng ? null : (
          <>
            {isConnect ? (
              <View
                style={{
                  width: 68,
                  height: 28,
                  borderRadius: 14,
                  backgroundColor: '#6066E9',
                  justifyContent: 'center',
                  alignItems: 'center',
                }}>
                <Text
                  style={{
                    fontSize: 14,
                    color: '#fff',
                    fontWeight: 'bold',
                  }}>
                  已配对
                </Text>
              </View>
            ) : (
              <TouchableWithoutFeedback onPress={this.props.onConnect}>
                <View
                  style={{
                    width: 68,
                    height: 28,
                    borderRadius: 14,
                    backgroundColor: '#f3f3f3',
                    justifyContent: 'center',
                    alignItems: 'center',
                  }}>
                  <Text
                    style={{
                      fontSize: 14,
                      color: '#6066E9',
                      fontWeight: 'bold',
                    }}>
                    配对
                  </Text>
                </View>
              </TouchableWithoutFeedback>
            )}
          </>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    height: 73,
    flexDirection: 'row',
    paddingHorizontal: 16,
    alignItems: 'center',
    justifyContent: 'space-between',
  },
});
