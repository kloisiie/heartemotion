import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
  Dimensions,
  ScrollView,
  ImageBackground,
  Alert,
  Clipboard,
} from 'react-native';
import {SafeAreaView} from 'react-native-safe-area-context';
import imageRes from '../../res/imgeRes';
import NetService from '../../service/NetService';
import statusHandle from '../../service/statusHandle';
import RNFetchBlob from 'rn-fetch-blob';
import SharePreference from 'react-native-share-preference';
import Keys from '../../constant/keys';
import DeviceInfo from 'react-native-device-info';
import Share from 'react-native-share';
import hostService from '../../service/hostService';
const {height, width} = Dimensions.get('screen');
export default class TestResult extends PureComponent {
  state = {data: null};
  render() {
    let rate = '';
    let arr = [];
    let list = [];
    let {data} = this.state;
    if (data) {
      rate = data.sumRate;
      arr = [
        data.happyRate,
        data.calmnessRate,
        data.agitatedRate,
        data.noMoodRate,
      ];
      let enters = data.enters;
      let length = enters ? enters.length : 0;
      let result = [];
      if (length > 0) {
        for (let i = 0; i < length; i++) {
          let item = enters[i];
          if (item.normal !== item.standard) {
            result.push(item);
          }
        }
      }
      list = result;
    }
    return (
      <View style={styles.container}>
        <View style={{height: 110, backgroundColor: '#5A60E9'}}>
          <SafeAreaView edges={['top']}>
            <View
              style={{
                flexDirection: 'row',
                alignItems: 'center',
                paddingHorizontal: 16,
                justifyContent: 'space-between',
                marginTop: 26,
              }}>
              <TouchableWithoutFeedback onPress={() => GBNavgator.goBack(null)}>
                <View>
                  <Image source={imageRes.icon_back_24_white} />
                </View>
              </TouchableWithoutFeedback>
              <Text style={{fontSize: 17, color: '#fff', fontWeight: 'bold'}}>
                调试结果
              </Text>
              <TouchableWithoutFeedback onPress={this._onExport}>
                <View>
                  <Image source={imageRes.icon_excel_24} />
                </View>
              </TouchableWithoutFeedback>
            </View>
          </SafeAreaView>
        </View>
        <View
          style={{
            position: 'absolute',
            left: 0,
            top: 100,
            width,
            height: height - 100,
            borderTopLeftRadius: 12,
            borderTopRightRadius: 12,
            overflow: 'hidden',
            backgroundColor: '#F7F7F7',
          }}>
          <ScrollView style={{flex: 1}}>
            <View
              style={{
                marginHorizontal: 16,
                marginTop: 12,
                height: 295,
                borderRadius: 8,
                overflow: 'hidden',
                backgroundColor: 'white',
                paddingHorizontal: 16,
                marginBottom: 12,
              }}>
              <View
                style={{
                  flexDirection: 'row',
                  alignItems: 'center',
                  justifyContent: 'space-between',
                  marginTop: 16,
                }}>
                <Text style={{fontSize: 14, color: '#333', fontWeight: 'bold'}}>
                  准确率统计
                </Text>
                <Text style={{fontSize: 12, color: '#999'}}>
                  百分比为准确率百分比
                </Text>
              </View>
              <ImageBackground
                source={imageRes.special_rightrate1_155}
                style={{
                  width: 155,
                  height: 155,
                  alignSelf: 'center',
                  marginTop: 20,
                  justifyContent: 'center',
                  alignItems: 'center',
                }}>
                <Text style={{fontSize: 30, color: '#333', fontWeight: 'bold'}}>
                  {parseInt(rate * 100.0)}
                  <Text
                    style={{fontSize: 16, color: '#333', fontWeight: 'bold'}}>
                    %
                  </Text>
                </Text>
                <Text style={{fontSize: 13, color: '#999'}}>总准确率</Text>
              </ImageBackground>
              <View
                style={{
                  flexDirection: 'row',
                  alignItems: 'center',
                  justifyContent: 'space-between',
                  marginTop: 24,
                }}>
                {arr.map((item, index) => {
                  let arr2 = ['高兴', '平稳', '烦躁', '无情绪'];
                  let arr1 = ['HAPPY', 'CALMNESS', 'AGITATED', 'NO_MOOD'];
                  return (
                    <View style={{alignItems: 'center'}}>
                      <View
                        style={{flexDirection: 'row', alignItems: 'center'}}>
                        <View
                          style={{
                            width: 9,
                            height: 9,
                            borderRadius: 2,
                            backgroundColor: statusHandle.getStatusColor(
                              arr1[index],
                            ),
                          }}
                        />
                        <Text
                          style={{marginLeft: 4, fontSize: 13, color: '#999'}}>
                          {arr2[index]}
                        </Text>
                      </View>
                      <Text
                        style={{
                          fontSize: 15,
                          color: '#20212A',
                          fontWeight: 'bold',
                          marginTop: 6,
                        }}>
                        {parseInt(arr[index] * 100.0)}%
                      </Text>
                    </View>
                  );
                })}
              </View>
            </View>
            {list.map((item, index) => {
              return <Item item={item} />;
            })}
          </ScrollView>
        </View>
      </View>
    );
  }
  componentDidMount() {
    GBPrompt.showLoading(true);
    this._loadData();
  }
  componentWillUnmount() {
    GBPrompt.showLoading(false);
    this.timer && clearTimeout(this.timer);
  }

  _loadData = async () => {
    let {taskId} = this.props.route.params;
    let {res, error} = await NetService.executeResult(taskId);
    let status = res.status;
    if (status == 1) {
      this.timer = setTimeout(this._loadData, 1000);
    } else if (status == 2) {
      GBPrompt.showLoading(false);
      this.timer && clearTimeout(this.timer);
      this.setState({data: res});
    } else {
      GBPrompt.showLoading(false);
      this.timer && clearTimeout(this.timer);
      GBPrompt.showToast('执行失败');
    }
  };

  _onExport = async () => {
    let {taskId} = this.props.route.params;
    let dirs = RNFetchBlob.fs.dirs;
    console.log('dirs ' + dirs.SDCardDir);
    let path =
      dirs.SDCardDir +
      `/Android/data/${DeviceInfo.getBundleId()}/files/${taskId}.xls`;
    let header = {Authorization: GBUser.token};
    // let url =
    //   'https://dev.api.beiru168.com/gragas/' +
    //   `api/access/v1/hr/debug/execute/export/${taskId}`;
    // let str = await SharePreference.getValue(Keys.host_data_key);
    // if (str && str.length > 0) {
    //let data = JSON.parse(str);
    let host = await hostService.getHost();
    let url = host + `api/access/v1/hr/debug/execute/export/${taskId}`;
    //}
    console.log('header ', header);
    console.log('url', url);
    GBPrompt.showLoading(true);
    RNFetchBlob.config({
      fileCache: true,
      path,
    })
      .fetch('GET', url, {
        ...header,
      })
      .then((res) => {
        GBPrompt.showLoading(false);
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
        GBPrompt.showLoading(false);
        console.log('下载异常 ', e);
        GBPrompt.showToast('导出文件失败');
      });
  };
}

const Item = ({item}) => {
  let time = item.hrTime;
  let answer = statusHandle.getStatusStr(item.standard);
  let result = statusHandle.getStatusStr(item.normal);
  return (
    <View
      style={{
        marginHorizontal: 16,
        height: 86,
        backgroundColor: 'white',
        borderRadius: 8,
        overflow: 'hidden',
        marginBottom: 12,
      }}>
      <Text
        style={{
          fontSize: 14,
          color: '#20212A',
          fontWeight: 'bold',
          marginLeft: 16,
          marginTop: 16,
        }}>
        {time}
      </Text>
      <View style={{flexDirection: 'row', alignItems: 'center', marginTop: 15}}>
        <Text style={{fontSize: 13, color: '#999', marginLeft: 16}}>
          标准结果{' '}
          <Text
            style={{
              color: statusHandle.getStatusColor(item.standard),
              fontWeight: 'bold',
            }}>
            {answer}
          </Text>
        </Text>
        <Text style={{fontSize: 13, color: '#999', marginLeft: 80}}>
          执行结果{' '}
          <Text
            style={{
              color: statusHandle.getStatusColor(item.normal),
              fontWeight: 'bold',
            }}>
            {result}
          </Text>
        </Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#F7F7F7',
    flex: 1,
  },
});
