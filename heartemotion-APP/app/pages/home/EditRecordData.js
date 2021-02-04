import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  ScrollView,
  TouchableWithoutFeedback,
  TextInput,
} from 'react-native';
import imageRes from '../../res/imgeRes';
import {SafeAreaView} from 'react-native-safe-area-context';
import NetService from '../../service/NetService';
export default class EditRecordData extends PureComponent {
  constructor(props) {
    super();
    let {data} = props.route.params;
    this.state = {name: data.wearer, handleStr: '', status: null, date: null};
  }
  render() {
    let startTime = null;
    let endTime = null;
    let name = this.state.name;
    let {status, date} = this.state;
    if (date) {
      startTime = `${date.sDate} ${date.sTime}`;
      endTime = `${date.eDate} ${date.eTime}`;
    }
    return (
      <ScrollView style={styles.container}>
        <TouchableWithoutFeedback onPress={() => this._onTime(1)}>
          <View
            style={{
              flexDirection: 'row',
              alignItems: 'center',
              paddingHorizontal: 16,
              justifyContent: 'space-between',
              height: 50,
              borderBottomWidth: 0.5,
              borderColor: '#eee',
            }}>
            <Text style={{fontSize: 15, color: '#666'}}>开始时间</Text>
            <Text style={{fontSize: 15, color: startTime ? '#333' : '#999'}}>
              {startTime ? startTime : '选择开始时间'}
            </Text>
          </View>
        </TouchableWithoutFeedback>
        <TouchableWithoutFeedback onPress={() => this._onTime(2)}>
          <View
            style={{
              flexDirection: 'row',
              alignItems: 'center',
              paddingHorizontal: 16,
              justifyContent: 'space-between',
              height: 50,
              borderBottomWidth: 0.5,
              borderColor: '#eee',
            }}>
            <Text style={{fontSize: 15, color: '#666'}}>结束时间</Text>
            <Text style={{fontSize: 15, color: endTime ? '#333' : '#999'}}>
              {endTime ? endTime : '选择结束时间'}
            </Text>
          </View>
        </TouchableWithoutFeedback>
        <TouchableWithoutFeedback onPress={this._onEditStatus}>
          <View
            style={{
              flexDirection: 'row',
              alignItems: 'center',
              paddingHorizontal: 16,
              height: 50,
              borderBottomWidth: 0.5,
              borderColor: '#eee',
            }}>
            <Text style={{fontSize: 15, color: '#666'}}>标注情绪</Text>
            <View
              style={{
                width: 6,
                height: 6,
                backgroundColor: '#5A60E9',
                borderRadius: 3,
                marginLeft: 5,
              }}
            />
            <View style={{flex: 1}} />
            <Text style={{fontSize: 15, color: status ? '#333' : '#999999'}}>
              {!status ? '选择情绪状态' : status}
            </Text>
            <Image source={imageRes.icon_go_16_gray} />
          </View>
        </TouchableWithoutFeedback>
        <TouchableWithoutFeedback onPress={this._onEditHandle}>
          <View
            style={{
              flexDirection: 'row',
              alignItems: 'center',
              paddingHorizontal: 16,
              height: 50,
              borderBottomWidth: 0.5,
              borderColor: '#eee',
            }}>
            <Text style={{fontSize: 15, color: '#666'}}>标注应对手段</Text>
            <View
              style={{
                width: 6,
                height: 6,
                backgroundColor: '#5A60E9',
                borderRadius: 3,
                marginLeft: 5,
              }}
            />
            <TextInput
              style={{padding: 0, flex: 1, marginLeft: 20}}
              textAlign={'right'}
              placeholder={'填写应对手段'}
              value={this.state.handleStr}
              onChangeText={(handleStr) => this.setState({handleStr})}
            />
          </View>
        </TouchableWithoutFeedback>
        <View
          style={{
            flexDirection: 'row',
            alignItems: 'center',
            paddingHorizontal: 16,
            justifyContent: 'space-between',
            height: 50,
            borderBottomWidth: 0.5,
            borderColor: '#eee',
          }}>
          <Text style={{fontSize: 15, color: '#666'}}>佩戴者姓名</Text>
          <TextInput
            style={{
              fontSize: 15,
              color: '#333',
              padding: 0,
              width: 100,
              height: 50,
            }}
            editable={false}
            textAlign={'right'}
            value={name}
            onChangeText={(name) => this.setState({name})}
            placeholder={''}
            placeholderTextColor={'#999'}
          />
        </View>
        <TouchableWithoutFeedback onPress={this._onSure}>
          <View
            style={{
              marginTop: 48,
              marginHorizontal: 16,
              height: 40,
              borderRadius: 20,
              backgroundColor: '#5A60E9',
              justifyContent: 'center',
              alignItems: 'center',
              marginBottom: 20,
            }}>
            <Text style={{fontSize: 16, color: '#fff'}}>提交更改</Text>
          </View>
        </TouchableWithoutFeedback>
      </ScrollView>
    );
  }

  _onTime = (type) => {
    let {date} = this.state;
    let {data} = this.props.route.params;
    GBNavgator.navigate('ChooseLabelTime', {
      date,
      deviceId: data.deviceId,
      onSure: (date) => {
        this.setState({date});
      },
    });
  };
  _onEditStatus = () => {
    let index = 0;
    let arr = ['无情绪', '平稳', '高兴', '烦躁'];
    GBNavgator.navigate('SingleColumnPicker', {
      index,
      data: arr,
      onSure: (index) => {
        this.setState({status: arr[index]});
      },
    });
  };
  _onEditHandle = () => {
    let index = 0;
    let arr = [
      '如果睡不着，可以数100只羊',
      '如果睡不着，可以数100只羊如果睡不着，可以数100只羊如果睡不着，可以数100只羊',
      '如果睡不着，可以数100只羊1',
    ];
    GBNavgator.navigate('SingleColumnPicker', {index, data: arr});
  };
  _onSure = async () => {
    let {date, handleStr, status} = this.state;
    if (!date) {
      GBPrompt.showToast('请选择开始和结束时间');
      return;
    }
    if (!status) {
      GBPrompt.showToast('请选择情绪状态');
      return;
    }

    if (handleStr.length == 0) {
      GBPrompt.showToast('请填写应对手段');
      return;
    }

    GBPrompt.showLoading(true);
    let {data} = this.props.route.params;
    let labelTimeStart = `${date.sDate} ${date.sTime}`;
    let labelTimeEnd = `${date.eDate} ${date.eTime}`;
    let arr = ['无情绪', '平稳', '高兴', '烦躁'];
    let arr1 = ['NO_MOOD', 'CALMNESS', 'HAPPY', 'AGITATED'];
    let index = arr.indexOf(status);
    let param = {
      labelTimeStart,
      labelTimeEnd,
      means: handleStr,
      deviceId: data.deviceId,
      labelStatus: arr1[index],
      override: false,
    };
    let {res, error} = await NetService.addLabel(param);
    GBPrompt.showLoading(false);
    if (error) {
      return;
    }
    let exist = res.exist;
    if (exist === 'YES') {
      let content = '此时间段已存在标注，是否强制覆盖？';
      GBNavgator.navigate('MyDialog', {
        title: '提示',
        content,
        cancelText: '取消',
        sureText: '确定',
        sureAction: async () => {
          param.override = true;
          GBPrompt.showLoading(true);
          let {res, error} = await NetService.addLabel(param);
          GBPrompt.showLoading(false);
          if (!error) {
            let {callback} = this.props.route.params;
            callback && callback(date);
            GBNavgator.goBack(null);
          }
        },
      });
    } else {
      GBNavgator.goBack(null);
    }
  };
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    flex: 1,
  },
});
