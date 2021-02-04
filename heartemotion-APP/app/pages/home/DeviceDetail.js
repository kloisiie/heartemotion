import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
  NativeEventEmitter,
  NativeModules,
  ScrollView,
  ImageBackground,
  Alert,
  Clipboard,
  DeviceEventEmitter,
} from 'react-native';
import imageRes from '../../res/imgeRes';
import HeartRateChart from '../bridgeViews/HeartRateChart';
import WristbandBridge from '../../libs/shouhuan/WristbandBridge';
import awaitAsyncGenerator from '@babel/runtime/helpers/esm/awaitAsyncGenerator';
import NetService from '../../service/NetService';
import statusHandle from '../../service/statusHandle';
import RNFetchBlob from 'rn-fetch-blob';
import SharePreference from 'react-native-share-preference';
import Keys from '../../constant/keys';
import DeviceInfo from 'react-native-device-info';
import Share from 'react-native-share';
import hostService from '../../service/hostService';
export default class DeviceDetail extends PureComponent {
  state = {
    index: 1,
    data: null,
    selectDate: null,
    chartData: {arr: [], timerArr: []},
  };
  render() {
    let name = '--';
    let count = '--';
    let userName = '--';
    let status = '--';
    let warnCount = '--';
    let handleStr = '--';
    let color = '#FFA300';
    let {index, selectDate, data} = this.state;
    if (data) {
      name = data.deviceName;
      count = data.hrValue;
      userName = data.wearer;
      status = statusHandle.getStatusStr(data.labelStatus);
      warnCount = data.policeCount;
      handleStr = data.means;
      color = statusHandle.getStatusColor(data.labelStatus);
    }
    // let arr = [];
    // for (let i = 0; i < 150; i++) {
    //   let temp = Math.random() * 20 + 80;
    //   arr.push(temp);
    // }
    // let data1 = [
    //   {
    //     status: 3,
    //     tagType: 1,
    //     list: arr,
    //   },
    // ];
    let data1 = this.state.chartData;
    return (
      <ScrollView style={styles.container}>
        <View style={{backgroundColor: 'white', paddingBottom: 10}}>
          <Text
            style={{
              fontSize: 18,
              color: '#333',
              fontWeight: 'bold',
              marginLeft: 16,
              marginTop: 12,
            }}>
            {name}
          </Text>
          <View
            style={{
              flexDirection: 'row',
              alignItems: 'center',
              paddingHorizontal: 16,
              marginTop: 12,
            }}>
            <TouchableWithoutFeedback onPress={() => this._onTimePicker()}>
              <View
                style={{
                  height: 40,
                  borderRadius: 8,
                  overflow: 'hidden',
                  backgroundColor: '#f8f8f8',
                  flex: 1,
                  marginRight: 31,
                  justifyContent: 'center',
                  paddingHorizontal: 5,
                }}>
                {selectDate ? (
                  <Text
                    style={{fontSize: 14, color: '#333', fontWeight: 'bold'}}>
                    {`${selectDate.sDate} ${selectDate.sTime} - ${selectDate.eDate} ${selectDate.eTime}`}
                  </Text>
                ) : (
                  <Text
                    style={{fontSize: 14, color: '#333', fontWeight: 'bold'}}>
                    当前显示实时数据
                    <Text style={{fontSize: 14, color: '#999'}}>
                      {' 点击选择时间段'}
                    </Text>
                  </Text>
                )}
              </View>
            </TouchableWithoutFeedback>
            <TouchableWithoutFeedback onPress={this._onRefresh}>
              <View style={{alignItems: 'center'}}>
                <Image source={imageRes.icon_refresh_20} />
                <Text style={{fontSize: 12, color: '#999'}}>实时</Text>
              </View>
            </TouchableWithoutFeedback>
          </View>

          <HeartRateChart
            type={1}
            data={data1}
            style={{
              height: 150,
              marginTop: 24,
              backgroundColor: 'red',
              marginLeft: 20,
            }}
          />
        </View>
        <View style={{flex: 1, backgroundColor: '#F8F8F8'}}>
          <View
            style={{
              marginHorizontal: 16,
              marginTop: 12,
              backgroundColor: 'white',
              borderRadius: 8,
              padding: 16,
            }}>
            <View
              style={{
                flexDirection: 'row',
                alignItems: 'center',
                justifyContent: 'space-between',
              }}>
              <View style={{flexDirection: 'row', alignItems: 'center'}}>
                <Image source={imageRes.icon_heartrate_12} />
                <Text style={{marginLeft: 4, fontSize: 14, color: '#333'}}>
                  {' 心率记录 '}
                </Text>
                <Text style={{fontSize: 13, color: '#999'}}>-{userName}</Text>
              </View>
              <TouchableWithoutFeedback onPress={this._onEdit}>
                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                  <Image source={imageRes.icon_tickling_20} />
                  <Text style={{marginLeft: 4, fontSize: 13, color: '#666'}}>
                    标注
                  </Text>
                </View>
              </TouchableWithoutFeedback>
            </View>
            <View
              style={{
                flexDirection: 'row',
                alignItems: 'center',
                marginTop: 20,
              }}>
              <Text style={{fontSize: 20, color: '#333', fontWeight: 'bold'}}>
                {count}
              </Text>
              <Text
                style={{
                  fontSize: 14,
                  color: '#333',
                  marginLeft: 4,
                  marginRight: 12,
                  flex: 1,
                }}>
                次/分钟
              </Text>
              <View
                style={{
                  width: 8,
                  height: 8,
                  borderRadius: 4,
                  backgroundColor: color,
                }}
              />
              <Text
                style={{
                  fontSize: 16,
                  color: color,
                  fontWeight: 'bold',
                  marginLeft: 6,
                }}>
                {status}
              </Text>
            </View>
            <Text
              style={{
                fontSize: 13,
                color: '#666',
                lineHeight: 18,
                marginTop: 16,
              }}>
              {handleStr}
            </Text>
          </View>
          <TouchableWithoutFeedback onPress={this._onWarn}>
            <View
              style={{
                marginTop: 12,
                marginHorizontal: 16,
                height: 50,
                backgroundColor: 'white',
                borderRadius: 8,
                flexDirection: 'row',
                alignItems: 'center',
                paddingHorizontal: 16,
              }}>
              <Text style={{fontSize: 15, color: '#333', flex: 1}}>
                报警记录
              </Text>
              <Text style={{fontSize: 15, color: '#FF4E56'}}>
                {warnCount}次
              </Text>
              <Image source={imageRes.icon_go_16_gray} />
            </View>
          </TouchableWithoutFeedback>
          <TouchableWithoutFeedback onPress={this._onExport}>
            <View
              style={{
                marginVertical: 12,
                marginHorizontal: 16,
                height: 50,
                backgroundColor: 'white',
                borderRadius: 8,
                flexDirection: 'row',
                alignItems: 'center',
                paddingHorizontal: 16,
              }}>
              <Text style={{fontSize: 15, color: '#333', flex: 1}}>
                导出文件
              </Text>
              <Image source={imageRes.icon_go_16_gray} />
            </View>
          </TouchableWithoutFeedback>
        </View>
      </ScrollView>
    );
  }
  async componentDidMount() {
    this.realFresh = true;
    this._setNavRight(false);
    await this._loadDetail();
    this._loadChartData();
    // let {isOpen} = this.props.route.params;
    // if (isOpen) {
    this.timer = setInterval(() => {
      this._loadDetail();
      if (this.realFresh) {
        this._loadChartData();
      }
    }, 5000);
    // }
  }
  componentWillUnmount() {
    this.timer && clearInterval(this.timer);
  }

  _loadDetail = async () => {
    let {id} = this.props.route.params;
    let {res, error} = await NetService.devicesDetail(id);
    if (!error) {
      this.setState({data: res});
      this._setNavRight(res.police === 'YES');
    }
  };

  _loadChartData = async () => {
    let {data, selectDate} = this.state;
    let hrTimeStart = null;
    let hrTimeEnd = null;
    if (selectDate) {
      let {sDate, sTime, eDate, eTime} = selectDate;
      hrTimeStart = `${sDate} ${sTime}`;
      hrTimeEnd = `${eDate} ${eTime}`;
    }
    // hrTimeStart = '2020-12-21 00:00:00';
    // hrTimeEnd = '2020-12-21 23:00:00';
    let {res, error} = await NetService.chartData(
      data.deviceId,
      hrTimeStart,
      hrTimeEnd,
    );
    let arr = [];
    let timerArr = [];
    if (!error) {
      for (let i = 0; i < res.length; i++) {
        let item = res[i];
        let status = 0;
        let temp = {tagType: 1};
        let labelStatus = item.labelStatus;
        let enters = item.enters;
        if (labelStatus === 'NO_MOOD') {
          status = 4;
        } else if (labelStatus === 'CALMNESS') {
          status = 2;
        } else if (labelStatus === 'AGITATED') {
          status = 3;
        } else if (labelStatus === 'HAPPY') {
          status = 1;
        }
        temp.status = status;
        temp.labelType = item.labelType;
        let list = [];
        for (let j = 0; j < enters.length; j++) {
          list.push(enters[j].hrValue);
          timerArr.push(enters[j].hrTime);
        }
        temp.list = list;
        arr.push(temp);
      }
    }
    console.log('图表数据', JSON.stringify(arr));
    this.setState({chartData: {arr, timerArr}});
  };

  _onIndex = (index) => {
    this.setState({index});
  };
  _setNavRight = (isOpen) => {
    let textColor = isOpen ? '#5A60E9' : '#666666';
    let source = isOpen
      ? imageRes.icon_link_pre_purple
      : imageRes.icon_link_nor_gray2;
    this.props.navigation.setOptions({
      headerRight: () => (
        <View
          style={{flexDirection: 'row', alignItems: 'center', marginRight: 16}}>
          <Text style={{fontSize: 15, color: textColor}}>情绪报警</Text>
          <TouchableWithoutFeedback onPress={this._onSet}>
            <View>
              <Image source={source} style={{marginLeft: 13}} />
            </View>
          </TouchableWithoutFeedback>
        </View>
      ),
    });
  };
  _onSet = async () => {
    let {data} = this.state;
    if (!data) {
      return;
    }
    let flag = data.police === 'YES';
    let police = flag ? 'NO' : 'YES';
    let {id} = this.props.route.params;
    let {res, error} = await NetService.updateDevice(id, {police});
    if (!error) {
      DeviceEventEmitter.emit('changeDeviceWarnStatus');
      this._loadDetail();
    }
  };

  _onTimePicker = () => {
    let {selectDate} = this.state;
    GBNavgator.navigate('TimePicker', {
      data: selectDate,
      type: 1,
      onSure: (selectDate) => {
        this.setState({selectDate}, () => {
          this.realFresh = false;
          this._loadChartData();
        });
      },
    });
  };

  _onWarn = () => {
    let {data} = this.state;
    if (!data) {
      return;
    }
    GBNavgator.navigate('AlarmRecord', {deviceId: data.deviceId});
  };
  _onEdit = () => {
    let {data} = this.state;
    if (!data) {
      return;
    }
    GBNavgator.navigate('EditRecordData', {
      data,
      callback: (selectDate) => {
        this.setState({selectDate}, () => {
          //this.realFresh = false;
          this._loadChartData();
        });
      },
    });
  };

  _onRefresh = () => {
    this.realFresh = true;
    this.setState({selectDate: null}, () => {
      this._loadChartData();
    });
  };

  _onTimeChage = (direction) => {
    if (direction == 1) {
      //时间减小
    } else {
    }
  };

  _onExport = () => {
    if (!this.state.data) {
      return;
    }
    let dirs = RNFetchBlob.fs.dirs;
    console.log('dirs ' + dirs.DocumentDir);
    GBNavgator.navigate('TimePicker', {
      data: null,
      type: 2,
      onSure: async (selectDate) => {
        let {sDate, sTime, eDate, eTime} = selectDate;
        let hrTimeStart = `${sDate} ${sTime}`;
        let hrTimeEnd = `${eDate} ${eTime}`;
        let timestamp = Date.parse(new Date());
        let path =
          dirs.SDCardDir +
          `/Android/data/${DeviceInfo.getBundleId()}/files/${timestamp}.json`;
        let header = {Authorization: GBUser.token};
        //let url =
        //   'https://dev.api.beiru168.com/gragas/' +
        //   `api/access/v1/hr/heart-rate/${this.state.data.deviceId}/export?hrTimeStart=${hrTimeStart}&hrTimeEnd=${hrTimeEnd}`;
        //let str = await SharePreference.getValue(Keys.host_data_key);
        //if (str && str.length > 0) {
        let host = await hostService.getHost();
        let url =
          host +
          `api/access/v1/hr/heart-rate/${this.state.data.deviceId}/export?hrTimeStart=${hrTimeStart}&hrTimeEnd=${hrTimeEnd}`;
        // }
        console.log('header ', header);
        console.log('url', url);

        RNFetchBlob.config({
          fileCache: true,
          path,
        })
          .fetch('GET', url, {
            ...header,
          })
          .then((res) => {
            console.log('The file saved to ', res.path());
            let path = res.path();
            if (path && path.length > 0) {
              Alert.alert('导出文件成功', path, [
                {
                  text: '分享',
                  onPress: () => {
                    if (path.indexOf('file://') == -1) {
                      path = 'file://' + path;
                    }
                    let options = {
                      type: 'text/plain',
                      url: path,
                      showAppsToView: true,
                    };
                    Share.open(options)
                      .then((res) => {
                        console.log(res);
                      })
                      .catch((err) => {
                        err && console.log(err);
                      });
                  },
                },
                {
                  text: '我知道了',
                  onPress: () => {},
                },
              ]);
            }
          })
          .catch((e) => {
            console.log('下载异常 ', e);
            GBPrompt.showToast('导出文件失败');
          });
      },
    });
  };
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#f8f8f8',
    flex: 1,
  },
});
