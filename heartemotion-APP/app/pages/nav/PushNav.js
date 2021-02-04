import React from 'react';
import {
  Platform,
  Image,
  View,
  TouchableWithoutFeedback,
  StyleSheet,
} from 'react-native';
import {createStackNavigator} from '@react-navigation/stack';
import {TransitionPresets} from '@react-navigation/stack';
import Tabbar from './Tabbar';
import APPDeviceInfo from '../../libs/common/APPDeviceInfo';
import imageRes from '../../res/imgeRes';
import SearchDevice from '../home/SearchDevice';
import DeviceDetail from '../home/DeviceDetail';
import AlarmRecord from '../home/AlarmRecord';
import EditRecordData from '../home/EditRecordData';
import HostSetting from '../test/HostSetting';
import ImportData from '../test/ImportData';
import TestResult from '../test/TestResult';
import ChooseLabelTime from '../home/ChooseLabelTime';

const Nav = createStackNavigator();
//堆栈路由挑战
function PushNav() {
  return (
    <Nav.Navigator screenOptions={options}>
      <Nav.Screen
        name={'Tabbar'}
        component={Tabbar}
        options={{headerShown: false}}
      />
      <Nav.Screen
        name={'SearchDevice'}
        component={SearchDevice}
        options={{title: '搜索设备'}}
      />
      <Nav.Screen
        name={'DeviceDetail'}
        component={DeviceDetail}
        options={{title: ''}}
      />
      <Nav.Screen
        name={'AlarmRecord'}
        component={AlarmRecord}
        options={{title: '报警记录'}}
      />
      <Nav.Screen
        name={'EditRecordData'}
        component={EditRecordData}
        options={{title: '情绪数据'}}
      />
      <Nav.Screen
        name={'HostSetting'}
        component={HostSetting}
        options={{title: '服务器配置'}}
      />
      <Nav.Screen
        name={'ImportData'}
        component={ImportData}
        options={{title: '导入数据'}}
      />
      <Nav.Screen
        name={'TestResult'}
        component={TestResult}
        options={{
          title: '调试结果',
          headerShown: false,
        }}
      />
      <Nav.Screen
        name={'ChooseLabelTime'}
        component={ChooseLabelTime}
        options={{
          title: '选择标注时间段',
        }}
      />
    </Nav.Navigator>
  );
}

const options = {
  headerTitleStyle: {
    color: '#333333',
    fontSize: 17,
    fontWeight: 'bold',
  },
  headerStyle: {
    backgroundColor: '#fff',
    elevation: StyleSheet.hairlineWidth,
  },
  headerPressColorAndroid: 'transparent',
  headerBackImage: () => (
    <View
      style={{
        marginLeft: 16,
        width: 30,
        height: 30,
        justifyContent: 'center',
      }}>
      <Image source={imageRes.nav.back} />
    </View>
  ),
  headerBackTitleVisible: false,
  headerTitleAlign: 'center',
  ...TransitionPresets.SlideFromRightIOS,
};

export default PushNav;
