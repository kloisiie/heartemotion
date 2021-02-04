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
import HeartRateChart from '../bridgeViews/HeartRateChart';
import NetService from '../../service/NetService';
export default class ChooseLabelTime extends PureComponent {
  constructor(props) {
    super();
    let {date} = props.route.params;
    let date1 = new Date().format('yyyy-MM-dd hh:mm:ss');
    let arr = date1.split(' ');
    date = date
      ? date
      : {
          sDate: arr[0],
          sTime: null,
          eDate: null,
          eTime: null,
        };

    this.state = {...date, currentIndex: 0, data: {arr: [], timerArr: []}};
    props.navigation.setOptions({
      headerRight: () => (
        <TouchableWithoutFeedback onPress={this._onSure}>
          <View style={{marginRight: 16}}>
            <Text style={{fontSize: 14, color: '#333'}}>确定</Text>
          </View>
        </TouchableWithoutFeedback>
      ),
    });
  }
  render() {
    let {sDate, sTime, eDate, eTime, currentIndex} = this.state;
    let arr = ['开始日期', '开始时间', '结束日期', '结束时间'];
    // let arr1 = [];
    // for (let i = 0; i < 30; i++) {
    //   let temp = Math.random() * 20 + 80;
    //   arr1.push(temp);
    // }
    // let data = [
    //   {
    //     status: 3,
    //     tagType: 1,
    //     list: arr1,
    //   },
    //   {
    //     status: 3,
    //     tagType: 2,
    //     list: arr1,
    //   },
    //   {
    //     status: 3,
    //     tagType: 1,
    //     list: arr1,
    //   },
    // ];
    let data = this.state.data;
    return (
      <View style={styles.container}>
        <View>
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
                    <Text style={{fontSize: 14, color: temp ? '#333' : '#999'}}>
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
                  this.setState({sDate: date}, this._onChangDate);
                } else {
                  this.setState({eDate: date}, this._onChangDate);
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
                  this.setState({sTime: value}, this._onChangDate);
                } else {
                  this.setState({eTime: value}, this._onChangDate);
                }
              }}
            />
          )}
        </View>
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
            下方查看此时间段心率
          </Text>
        </View>
        <HeartRateChart
          type={2}
          data={data}
          style={{height: 150, marginTop: 40, backgroundColor: '#eee'}}
        />
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
  _onChangDate = async () => {
    let {sDate, sTime, eDate, eTime} = this.state;
    if (!sDate || !sTime || !eDate || !eTime) {
      return;
    }
    let labelTimeStart = `${sDate} ${sTime}`;
    let labelTimeEnd = `${eDate} ${eTime}`;
    if (this._compareDate(labelTimeStart, labelTimeEnd) == 1) {
      //开始时间大于结束时间直接返回空
      this.setState({data: {arr: [], timerArr: []}});
      return;
    }
    console.log('labelTimeStart ', labelTimeStart);
    console.log('labelTimeEnd ', labelTimeEnd);
    let date = new Date(Date.parse(labelTimeStart.replace(/-/g, '/')));
    console.log('datedate', date);
    date = new Date(date.getTime() - 2 * 60 * 1000);
    let labelTimeStart1 = date.format('yyyy-MM-dd hh:mm:ss');

    date = new Date(Date.parse(labelTimeEnd.replace(/-/g, '/')));
    date = new Date(date.getTime() + 2 * 60 * 1000);
    let labelTimeEnd1 = date.format('yyyy-MM-dd hh:mm:ss');

    console.log('labelTimeStart111 ', labelTimeStart1);
    console.log('labelTimeEnd111 ', labelTimeEnd1);
    let {deviceId} = this.props.route.params;
    let {res, error} = await NetService.chartLabelData(
      deviceId,
      labelTimeStart1,
      labelTimeEnd1,
    );
    let arr = [];
    let timerArr = [];
    if (!error && res.length > 0) {
      let length = res.length;
      let firstItem = res[0];
      let lastItem = res[length - 1];
      let startIndex = 0;
      let endIndex = length - 1;
      if (this._compareDate(labelTimeStart, firstItem.hrTime) == 1) {
        for (let i = 0; i < length; i++) {
          let result = this._compareDate(labelTimeStart, res[i].hrTime);
          if (result == 0) {
            startIndex = i;
            break;
          }
          if (result == -1) {
            startIndex = i - 1;
            break;
          }
        }
      }
      if (this._compareDate(lastItem.hrTime, labelTimeEnd)) {
        for (let i = length - 1; i > 0; i--) {
          let result = this._compareDate(res[i].hrTime, labelTimeEnd);
          if (result == 0) {
            endIndex = i;
            break;
          }
          if (result == -1) {
            endIndex = i + 1;
            break;
          }
        }
      }
      let arr1 = [];
      let arr2 = [];
      let arr3 = [];
      for (let i = 0; i < length; i++) {
        let item = res[i];
        if (i < startIndex) {
          arr1.push(item.hrValue);
        } else if (i <= endIndex) {
          arr2.push(item.hrValue);
        } else {
          arr3.push(item.hrValue);
        }
        timerArr.push(item.hrTime);
      }
      if (arr1.length > 0) {
        arr.push({
          labelType: 'ARITHMETIC',
          status: 3,
          tagType: 1,
          list: arr1,
        });
      }
      if (arr2.length > 0) {
        arr.push({
          labelType: 'ARITHMETIC',
          status: 3,
          tagType: 2,
          list: arr2,
        });
      }
      if (arr3.length > 0) {
        arr.push({
          labelType: 'ARITHMETIC',
          status: 3,
          tagType: 1,
          list: arr3,
        });
      }
    }
    let data = {arr, timerArr};
    console.log('arrrrrr', data);
    this.setState({data});
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

  _compareDate(firstDate, secondDdate) {
    let oDate1 = new Date(Date.parse(firstDate.replace(/-/g, '/')));
    let oDate2 = new Date(Date.parse(secondDdate.replace(/-/g, '/')));
    if (oDate1 > oDate2) {
      return 1;
    } else if (oDate1 == oDate2) {
      return 0;
    } else {
      return -1;
    }
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
});
