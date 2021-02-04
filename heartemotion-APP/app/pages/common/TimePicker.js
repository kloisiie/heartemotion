import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
} from 'react-native';
import {Scrollpicker, Datepicker, Timepicker} from 'beeshell';
import imageRes from '../../res/imgeRes';
import {SafeAreaView} from 'react-native-safe-area-context';

export default class TimePicker extends PureComponent {
  constructor(props) {
    super();
    let {data, type} = props.route.params;
    let date = new Date().format('yyyy-MM-dd hh:mm:ss');
    let arr = date.split(' ');
    data = data
      ? data
      : {
          sDate: arr[0],
          sTime: null,
          eDate: null,
          eTime: null,
        };
    this.state = {...data, currentIndex: 0};
    this.type = type;
  }
  render() {
    let {sDate, sTime, eDate, eTime, currentIndex} = this.state;
    let arr = ['开始日期', '开始时间', '结束日期', '结束时间'];
    return (
      <View style={styles.container}>
        <View
          style={{
            backgroundColor: 'white',
            borderTopLeftRadius: 20,
            borderTopRightRadius: 20,
            overflow: 'hidden',
          }}>
          <View>
            <View
              style={{
                flexDirection: 'row',
                height: 50,
                justifyContent: 'space-between',
                alignItems: 'center',
                paddingHorizontal: 16,
              }}>
              <Text style={{fontSize: 15, color: '#333', fontWeight: 'bold'}}>
                时间段选择
              </Text>
              <TouchableWithoutFeedback onPress={() => GBNavgator.goBack(null)}>
                <View>
                  <Image source={imageRes.icon_close_22_666} />
                </View>
              </TouchableWithoutFeedback>
            </View>
            <View
              style={{
                flexDirection: 'row',
                alignItems: 'flex-end',
                justifyContent: 'space-around',
                height: 44,
                backgroundColor: '#F8F8F8',
              }}>
              {arr.map((value, index) => {
                let temp = null;
                if (index == 0) {
                  temp = sDate;
                } else if (index == 1) {
                  temp = sTime;
                } else if (index == 2) {
                  temp = eDate;
                } else {
                  temp = eTime;
                }
                return (
                  <TouchableWithoutFeedback
                    onPress={() => this._onChangeIndex(index)}>
                    <View style={{alignItems: 'center'}}>
                      <Text
                        style={{fontSize: 14, color: temp ? '#333' : '#999'}}>
                        {temp ? temp : value}
                      </Text>
                      <View
                        style={{
                          width: 40,
                          height: 3,
                          borderRadius: 1.5,
                          marginTop: 9,
                          backgroundColor:
                            currentIndex == index ? '#5A60E9' : '#f8f8f8',
                        }}
                      />
                    </View>
                  </TouchableWithoutFeedback>
                );
              })}
            </View>
          </View>
          <View style={{height: 200}}>
            {currentIndex == 1 || currentIndex == 3 ? null : (
              <Datepicker
                proportion={[1, 1, 1]}
                numberOfYears={5}
                date={currentIndex == 0 ? sDate : eDate}
                onChange={(date) => {
                  console.log(date);
                  if (currentIndex == 0) {
                    this.setState({sDate: date});
                  } else {
                    this.setState({eDate: date});
                  }
                }}
              />
            )}
            {currentIndex == 0 || currentIndex == 2 ? null : (
              <Timepicker
                proportion={[1, 1, 1]}
                hourStep={1}
                minuteStep={1}
                secondStep={1}
                value={currentIndex == 1 ? sTime : eTime}
                onChange={(value) => {
                  console.log(value);
                  if (currentIndex == 1) {
                    this.setState({sTime: value});
                  } else {
                    this.setState({eTime: value});
                  }
                }}
              />
            )}
          </View>
          <SafeAreaView edges={['bottom']}>
            <TouchableWithoutFeedback onPress={this._onSure}>
              <View
                style={{
                  marginTop: 20,
                  marginHorizontal: 16,
                  height: 40,
                  borderRadius: 20,
                  backgroundColor: '#5A60E9',
                  justifyContent: 'center',
                  alignItems: 'center',
                  marginBottom: 20,
                }}>
                <Text style={{fontSize: 16, color: '#fff'}}>
                  {this.type == 1 ? '确认选择' : '确认导出文件'}
                </Text>
              </View>
            </TouchableWithoutFeedback>
          </SafeAreaView>
        </View>
      </View>
    );
  }
  _onChangeIndex = (index) => {
    let {currentIndex} = this.state;
    if (currentIndex == index) {
      return;
    }
    this.setState({currentIndex: index});
  };
  _onSure = () => {
    let {sDate, sTime, eDate, eTime} = this.state;
    if (sDate == null || sTime == null || eDate == null || eTime == null) {
      return;
    }
    let {onSure} = this.props.route.params;
    onSure && onSure({sDate, sTime, eDate, eTime});
    GBNavgator.goBack(null);
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-end',
    backgroundColor: 'rgba(0,0,0,0.5)',
  },
});
