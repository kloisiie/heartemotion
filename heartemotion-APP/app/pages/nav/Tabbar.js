import * as React from 'react';
import {Image, Text, View} from 'react-native';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import Home from '../home/Home';
import {PureComponent} from 'react';
import Test from '../test/Test';
import imageRes from '../../res/imgeRes';
const Tab = createBottomTabNavigator();
const tabBarOptions = {
  activeTintColor: '#5A60E9',
  inactiveTintColor: '#666666',
  labelStyle: {fontSize: 11},
  showLabel: false,
};
function screenOptions({route}) {
  return {
    tabBarIcon: ({focused, color, size}) => {
      let name = route.name;
      let title = '';
      let source = null;
      if (name === 'Home') {
        source = focused ? imageRes.icon_tab_1_pre : imageRes.icon_tab_1_nor;
        title = '首页';
      } else if (name === 'Test') {
        source = focused ? imageRes.icon_tab_2_pre : imageRes.icon_tab_2_nor;
        title = '调试';
      }
      return <IconWithBadge name={title} source={source} color={color} />;
    },
  };
}

function IconWithBadge({name, source, color}) {
  let itemW = 24;
  let itemH = 24;
  return (
    <View style={{alignItems: 'center'}}>
      <Image
        source={source}
        style={{width: itemW, height: itemH, backgroundColor: 'white'}}
      />
      <Text style={{color, fontSize: 10, fontWeight: 'bold', marginTop: 4}}>
        {name}
      </Text>
    </View>
  );
}
//底部导航条
export default class Tabbar extends PureComponent {
  render() {
    return (
      <Tab.Navigator
        initialRouteName={'Home'}
        tabBarOptions={tabBarOptions}
        screenOptions={screenOptions}>
        <Tab.Screen
          name="Home"
          component={Home}
          listeners={{
            tabPress: (e) => {
              // 切换事件监听
              //console.log('home');
              //e.preventDefault(); //调用阻止切换
            },
          }}
        />
        <Tab.Screen name="Test" component={Test} />
      </Tab.Navigator>
    );
  }
  componentDidMount() {}
}
