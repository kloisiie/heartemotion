import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import PushNav from './PushNav';
const Nav = createStackNavigator();
//正常的模态路由跳转
function NormalModalNav() {
  return (
    <Nav.Navigator mode={'modal'}>
      <Nav.Screen
        name={'PushNav'}
        component={PushNav}
        options={{ headerShown: false }}
      />
    </Nav.Navigator>
  );
}

export default NormalModalNav;
