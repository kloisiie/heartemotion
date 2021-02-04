import React from 'react';
import {
  View,
  StyleSheet,
  Text,
  Image,
  TouchableWithoutFeedback,
  FlatList,
  NativeEventEmitter,
  NativeModules,
  Alert,
  Clipboard,
  DeviceEventEmitter,
} from 'react-native';
import {SafeAreaView} from 'react-native-safe-area-context';
import imageRes from '../../res/imgeRes';
import HomeItem from './components/HomeItem';
import NetService from '../../service/NetService';
import {setToken} from 'react-native-request';
import SharePreference from 'react-native-share-preference';
import Keys from '../../constant/keys';
import WristbandBridge from '../../libs/shouhuan/WristbandBridge';
import RNFetchBlob from 'rn-fetch-blob';
import DeviceInfo from 'react-native-device-info';
import APPNativeBridge from '../../libs/common/APPNativeBridge';
export default class Home extends React.PureComponent {
  state = {
    data: [],
    managerStatus: false,
    isAll: false,
    selectArr: [],
    openDeviceId: null,
  };
  render() {
    let {data, managerStatus, isAll} = this.state;
    if (!data || data.length == 0) {
      return (
        <View
          style={{
            flex: 1,
            backgroundColor: '#f8f8f8',
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <Image source={imageRes.icon_empty3} />
          <Text style={{fontSize: 15, color: '#999', marginTop: 16}}>
            暂无设备
          </Text>
          <TouchableWithoutFeedback onPress={this._onAdd}>
            <View
              style={{
                width: 183,
                height: 44,
                borderRadius: 22,
                borderWidth: 0.5,
                borderColor: '#5A60E9',
                justifyContent: 'center',
                alignItems: 'center',
                backgroundColor: 'white',
                marginTop: 28,
              }}>
              <Text style={{fontSize: 16, color: '#5A60E9'}}>添加设备</Text>
            </View>
          </TouchableWithoutFeedback>
        </View>
      );
    }
    return (
      <SafeAreaView style={styles.container} edges={['top']}>
        <View
          style={{
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
            marginTop: 33,
            paddingHorizontal: 16,
          }}>
          <Text style={{fontSize: 24, color: '#333', fontWeight: 'bold'}}>
            当前设备
          </Text>
          {managerStatus ? (
            <Text
              style={{fontSize: 16, color: '#5A60E9'}}
              onPress={this._onManager}>
              完成
            </Text>
          ) : (
            <TouchableWithoutFeedback onPress={this._onManager}>
              <View style={{flexDirection: 'row', alignItems: 'center'}}>
                <Image source={imageRes.icon_management_16} />
                <Text style={{fontSize: 16, color: '#333', marginLeft: 4}}>
                  管理
                </Text>
              </View>
            </TouchableWithoutFeedback>
          )}
        </View>
        <FlatList
          style={{flex: 1, marginTop: 37}}
          data={data}
          renderItem={this._renderItem}
          extraData={this.state.selectArr}
        />
        {!managerStatus ? null : (
          <View
            style={{
              height: 50,
              flexDirection: 'row',
              alignItems: 'center',
              paddingHorizontal: 16,
              backgroundColor: 'white',
              justifyContent: 'space-between',
            }}>
            <TouchableWithoutFeedback onPress={this._onAll}>
              <View style={{flexDirection: 'row', alignItems: 'center'}}>
                <Image
                  source={
                    isAll
                      ? imageRes.icon_select_22_pre
                      : imageRes.icon_select_22_nor
                  }
                />
                <Text style={{marginLeft: 4, fontSize: 13, color: '#666'}}>
                  全选
                </Text>
              </View>
            </TouchableWithoutFeedback>
            <TouchableWithoutFeedback onPress={this._onDelete}>
              <View
                style={{
                  width: 83,
                  height: 34,
                  borderRadius: 17,
                  borderWidth: 0.5,
                  borderColor: '#FF4E56',
                  justifyContent: 'center',
                  alignItems: 'center',
                }}>
                <Text
                  style={{fontSize: 15, color: '#FF4E56', fontWeight: 'bold'}}>
                  删除
                </Text>
              </View>
            </TouchableWithoutFeedback>
          </View>
        )}
        {managerStatus ? null : (
          <TouchableWithoutFeedback onPress={this._onAdd}>
            <View
              style={{
                position: 'absolute',
                right: 16,
                bottom: 30,
                width: 66,
                height: 66,
              }}>
              <Image source={imageRes.icon_addfile_50} />
            </View>
          </TouchableWithoutFeedback>
        )}
      </SafeAreaView>
    );
  }
  async componentDidMount() {
    // if (!GBUser) {
    //   let str = await SharePreference.getValue(Keys.host_data_key);
    //   if (!str || str.length == 0) {
    //     return;
    //   }
    //   let {res, error} = await NetService.login();
    //   if (!error && res) {
    //     setToken(res.token);
    //     GBUser = res;
    //     SharePreference.saveValue(Keys.user_info_key, JSON.stringify(res));
    //   }
    // }

    this._unsubscribe = this.props.navigation.addListener('focus', async () => {
      this._loadList();
    });

    //监听上报数据
    const eventEmitter = new NativeEventEmitter(NativeModules.WristbandMoudle);
    this.eventEmitter1 = eventEmitter.addListener(
      'hrUpdateEvent',
      async (event) => {
        console.log('收到连接设备的数据 ', event);
        let hr = event.hr;
        if (hr == 0) {
          return;
        }
        let date = new Date().format('yyyy-MM-dd hh:mm:ss');
        if (this.state.openDeviceId) {
          let {res, error} = await NetService.sendHeartData(
            this.state.openDeviceId,
            date,
            hr,
          );
        }
      },
    );
    this.timeMark = null;
    this.timer = setInterval(async () => {
      let data = this.state.data;
      if (data.length == 0) {
        return;
      }
      let arr = [];
      for (let i = 0; i < data.length; i++) {
        let item = data[i];
        if (item.police === 'YES') {
          arr.push(item.deviceId);
        }
      }
      if (arr.length == 0) {
        return;
      }
      let {res, error} = await NetService.warnPoll(
        arr.join(','),
        this.timeMark,
      );
      if (!error) {
        let {polices, timeMark} = res;
        this.timeMark = timeMark;
        console.log('policespolicespolicespolices', polices);
        if (polices && polices.length > 0) {
          WristbandBridge.sendLocalNotify(polices);
        }
      }
    }, 5000);
    this.deEmitter = DeviceEventEmitter.addListener(
      'changeDeviceWarnStatus',
      (a) => {
        this._loadList();
      },
    );
  }

  componentWillUnmount() {
    this._unsubscribe && this._unsubscribe();
    this.eventEmitter1 && this.eventEmitter1.remove();
    this.timer && clearInterval(this.timer);
    this.deEmitter && this.deEmitter.remove();
  }
  _loadList = async () => {
    let {res, error} = await NetService.deviceList();
    if (!error) {
      this.setState({data: res});
    } else {
      this.setState({data: []});
    }
  };
  _onManager = () => {
    let {managerStatus} = this.state;
    managerStatus = !managerStatus;
    this.setState({managerStatus});
  };
  _onAdd = () => {
    GBNavgator.navigate('SearchDevice');
  };
  _onAll = () => {
    let {isAll, data, selectArr} = this.state;
    isAll = !isAll;
    if (isAll) {
      selectArr = [...data];
    } else {
      selectArr = [];
    }
    this.setState({isAll, selectArr});
  };
  _onDelete = async () => {
    let {selectArr} = this.state;
    if (selectArr.length == 0) {
      GBPrompt.showToast('未选择设备');
      return;
    }
    let ids = [];
    for (let i = 0; i < selectArr.length; i++) {
      let item = selectArr[i];
      ids.push(item.id);
    }
    ids = ids.join(',');
    Alert.alert('是否确认删除设备？', '', [
      {
        text: '取消',
        onPress: () => {},
      },
      {
        text: '确定',
        onPress: async () => {
          let {res, error} = await NetService.deleteDevices(ids);
          if (!error) {
            this.setState({selectArr: [], isAll: false});
            this._loadList();
          }
        },
      },
    ]);
  };
  _onEdit = (item) => {
    GBNavgator.navigate('EditDevice', {
      item,
      onSure: async (deviceName, wearer) => {
        let {res, error} = await NetService.updateDevice(item.id, {
          deviceName,
          wearer,
        });
        if (!error) {
          this._loadList();
        }
      },
    });
  };
  _onOpen = async (item) => {
    let result = await APPNativeBridge.checkBlueStatus();
    if (!result) {
      result = await APPNativeBridge.openBlue();
      if (!result) {
        GBPrompt.showToast('打开蓝牙失败');
        return;
      }
    }

    console.log('_onOpen', item);
    let {openDeviceId} = this.state;
    console.log('_onOpen111', openDeviceId);
    if (openDeviceId) {
      await WristbandBridge.disconnectDeivce(openDeviceId);
    }
    let {deviceId} = item;
    if (deviceId === openDeviceId) {
      this.setState({openDeviceId: null});
      return;
    }
    console.log('连接deviceId ', deviceId);
    try {
      let result = await WristbandBridge.connectDeivce(deviceId);
      this.setState({openDeviceId: deviceId});
    } catch (e) {
      this.setState({openDeviceId: null});
      GBPrompt.showToast('打开失败');
    }
  };
  _onItem = (item) => {
    let {managerStatus, selectArr, data, isAll, openDeviceId} = this.state;
    if (managerStatus) {
      let isSelect = selectArr.indexOf(item) != -1;
      let arr = [];
      if (isSelect) {
        arr = selectArr.filter((value) => value != item);
      } else {
        arr = [...selectArr, item];
      }
      isAll = arr.length == data.length;
      this.setState({selectArr: arr, isAll});
    } else {
      GBNavgator.navigate('DeviceDetail', {
        id: item.id,
        isOpen: item.deviceId == openDeviceId,
      });
    }
  };
  _renderItem = ({item, index}) => {
    let {managerStatus, selectArr, openDeviceId} = this.state;
    let isSelect = selectArr.indexOf(item) != -1;
    return (
      <HomeItem
        item={item}
        isOpen={item.deviceId == openDeviceId}
        managerStatus={managerStatus}
        isSelect={isSelect}
        onItem={() => this._onItem(item)}
        onOpen={() => this._onOpen(item)}
        onEdit={() => this._onEdit(item)}
      />
    );
  };
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f8f8f8',
  },
});
