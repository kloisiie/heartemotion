//底部透明的
import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import NormalModalNav from './NormalModalNav';
import MyDialog from '../common/MyDialog';
import TimePicker from '../common/TimePicker';
import SingleColumnPicker from '../common/SingleColumnPicker';
import EditDevice from '../home/EditDevice';
import ChooseFileType from '../test/ChooseFileType';
import ChooseAlgorithm from '../test/ChooseAlgorithm';
const Nav = createStackNavigator();
//透明的模态路由跳转
function TransParentModalNav() {
  return (
    <Nav.Navigator screenOptions={screenOptions} mode="modal" headerMode="none">
      <Nav.Screen
        name={'NormalModalNav'}
        component={NormalModalNav}
        options={{headerShown: false}}
      />
      <Nav.Screen
        name={'MyDialog'}
        component={MyDialog}
        options={{headerShown: false}}
      />
      <Nav.Screen
        name={'TimePicker'}
        component={TimePicker}
        options={{headerShown: false}}
      />
      <Nav.Screen
        name={'SingleColumnPicker'}
        component={SingleColumnPicker}
        options={{headerShown: false}}
      />
      <Nav.Screen
        name={'EditDevice'}
        component={EditDevice}
        options={{headerShown: false}}
      />
      <Nav.Screen
        name={'ChooseFileType'}
        component={ChooseFileType}
        options={{headerShown: false}}
      />
      <Nav.Screen
        name={'ChooseAlgorithm'}
        component={ChooseAlgorithm}
        options={{headerShown: false}}
      />
    </Nav.Navigator>
  );
}
const screenOptions = {
  cardStyle: {backgroundColor: 'transparent'},
  cardOverlayEnabled: true,
  cardStyleInterpolator: ({current: {progress}}) => ({
    cardStyle: {
      opacity: progress.interpolate({
        inputRange: [0, 0.5, 0.9, 1],
        outputRange: [0, 0.25, 0.7, 1],
      }),
    },
    overlayStyle: {
      opacity: progress.interpolate({
        inputRange: [0, 1],
        outputRange: [0, 0.5],
        extrapolate: 'clamp',
      }),
    },
  }),
};

export default TransParentModalNav;
