import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  FlatList,
  NativeEventEmitter,
  NativeModules,
} from 'react-native';
import SearchDeviceItem from './components/SearchDeviceItem';
import APPNativeBridge from '../../libs/common/APPNativeBridge';
import WristbandBridge from '../../libs/shouhuan/WristbandBridge';
import NetService from '../../service/NetService';
export default class SearchDevice extends PureComponent {
  state = {data: [], bindList: []};
  render() {
    return (
      <View style={styles.container}>
        <FlatList
          data={this.state.data}
          renderItem={this._renderItem}
          ItemSeparatorComponent={this._ItemSeparatorComponent}
          extraData={this.state.bindList}
        />
      </View>
    );
  }

  async componentDidMount() {
    try {
      let result = await APPNativeBridge.checkBlueStatus();
      if (result) {
        console.log('已打开蓝呀');
        this._find();
      } else {
        console.log('未打开蓝呀');
        GBNavgator.navigate('MyDialog', {
          content: '需要开启您的蓝牙才能进行配对',
          cancelText: '拒绝',
          sureText: '允许',
          sureAction: async () => {
            let result = await APPNativeBridge.openBlue();
            if (result) {
              console.log('已打开');
              this._find();
            } else {
              console.log('打开蓝牙失败');
              GBPrompt.showToast('打开蓝牙失败');
            }
          },
          cancelAction: () => {
            GBNavgator.goBack(null);
          },
        });
      }
    } catch (e) {
      GBPrompt.showToast('设备不支持蓝牙');
    }

    const eventEmitter = new NativeEventEmitter(NativeModules.WristbandMoudle);
    this.eventEmitter = eventEmitter.addListener('findDeivceEvent', (event) => {
      console.log('发现了设备', event); // "someValue"
      let device = event;
      device.connectIng = false;
      let {data} = this.state;
      data = [...data, event];
      this.setState({data});
    });

    let {res, error} = await NetService.deviceList();
    if (!error && res) {
      let arr = [];
      for (let i = 0; i < res.length; i++) {
        let item = res[i];
        arr.push(item.deviceId);
      }
      console.log('arr ', arr);
      this.setState({bindList: arr});
    }
  }

  componentWillUnmount() {
    WristbandBridge.stopFind();
    this.eventEmitter.remove();
  }

  _find = () => {
    WristbandBridge.findDevices();
  };
  _onConnect = async (item, index) => {
    let {id, name} = item;
    let {bindList} = this.state;
    item.connectIng = true;
    this.setState({bindList: [...bindList]});
    let {res, error} = await NetService.addDevice(id, name);
    if (!error) {
      item.connectIng = false;
      this.setState({bindList: [...bindList, id]});
    } else {
      item.connectIng = false;
      this.setState({bindList: [...bindList]});
    }
  };
  _renderItem = ({item, index}) => {
    let {bindList} = this.state;
    console.log('_renderItem1 ', bindList);
    console.log('_renderItem2 ', item);
    return (
      <SearchDeviceItem
        item={item}
        onConnect={() => this._onConnect(item, index)}
        isConnect={bindList.indexOf(item.id) != -1}
      />
    );
  };
  _ItemSeparatorComponent = () => {
    return (
      <View style={{height: 0.5, backgroundColor: '#eee', marginLeft: 50}} />
    );
  };
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    flex: 1,
  },
});
