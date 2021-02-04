import React from 'react';
import {
  View,
  StyleSheet,
  Dimensions,
  AppState,
  BackHandler,
  DeviceEventEmitter,
  Alert,
  Platform,
} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import TransParentModalNav from './TransParentModalNav';
import Toast, {DURATION} from 'react-native-easy-toast';
import LottieView from 'lottie-react-native';
import imageRes from '../../res/imgeRes';
import codePush from 'react-native-code-push';
import SharePreference from 'react-native-share-preference';
import Keys from '../../constant/keys';
import Splash from 'react-native-splash-screen';
import NotifyNames from '../../constant/NotifyNames';
import {
  setToken,
  setRequestFlag,
  setBaseUrl,
  setFlag,
} from 'react-native-request';
import {getVersion, getUniqueId} from 'react-native-device-info';
import OSS from 'react-native-oss';
import {getAppMetaData} from 'react-native-get-channel';
import APPEnv from '../../config/envConfig';
import Guide from '../guide/Guide';
import StartAd from '../ad/StartAd';
import AdService from '../../service/adService';
import {checkAppUpdate} from '../../service/appUpdateService';
import {AppUpdateView} from 'react-native-appupdate';
import hostService from '../../service/hostService';
const {width, height} = Dimensions.get('window');
let exitFlag = true;
function getCurrentRouteName(navigationState) {
  if (!navigationState) {
    return null;
  }
  const route = navigationState.routes[navigationState.index];

  // dive into nested navigators
  if (route.routes) {
    return getCurrentRouteName(route);
  }
  return route.routeName;
}

function getRouterDeep(navigationState) {
  if (!navigationState) {
    return;
  }
  let index = navigationState.index;
  if (index > 0) {
    exitFlag = false;
    return;
  }
  const route = navigationState.routes[index];
  if (route.routes) {
    return getRouterDeep(route);
  }
}

export default class Navgator extends React.PureComponent {
  state = {loading: false, initFinsh: false, updateData: null};
  firstComing = false;
  showGuide = false;
  render() {
    console.log('render');
    if (!this.state.initFinsh) {
      return <View />;
    }
    if (this.firstComing) {
      this.firstComing = false;
      if (this.showGuide) {
        return <Guide images={[]} callback={this._callback} />;
      } else {
        return <StartAd callback={this._callback} />;
      }
    }
    return (
      <View style={styles.container}>
        <NavigationContainer
          onNavigationStateChange={this._handleNavigationChange}
          ref={(nav) => {
            if (nav) {
              global.GBNavgator = nav;
            }
          }}>
          <TransParentModalNav />
        </NavigationContainer>
        <Loading loading={this.state.loading} />
        <Toast
          ref={(ref) => (this.toast = ref)}
          position="center"
          opacity={0.8}
          textStyle={{color: 'white'}}
        />
        {!this.state.updateData ? null : (
          <View style={{...StyleSheet.absoluteFill}}>
            <AppUpdateView
              {...this.state.updateData}
              sureCallback={() => {
                if (this.state.updateType == 1) {
                  this.setState({updateData: null});
                }
              }}
            />
          </View>
        )}
      </View>
    );
  }
  componentDidMount() {
    //setBaseUrl('https://dev.api.beiru168.com/gragas/');
    this._setPrompt();
    //this._configOSS();
    this._initBase();
    this._init();
    BackHandler.addEventListener('hardwareBackPress', this._onBackAndroid);
    DeviceEventEmitter.addListener(
      NotifyNames.logout_status_chang_notify,
      this._loginChang,
    );
    DeviceEventEmitter.addListener(
      NotifyNames.login_expired_notify,
      this._loginExpired,
    );
  }
  componentWillUnmount() {
    BackHandler.removeEventListener('hardwareBackPress', this._onBackAndroid);
    DeviceEventEmitter.removeListener(
      NotifyNames.logout_status_chang_notify,
      this._loginChang,
    );
    DeviceEventEmitter.removeListener(
      NotifyNames.login_expired_notify,
      this._loginExpired,
    );
  }

  _initBase = async () => {
    let host = await hostService.getHost();
    setBaseUrl(host);
  };

  _init = async () => {
    try {
      let user = await SharePreference.getValue(Keys.user_info_key);
      console.log('user', user);
      if (user) {
        user = JSON.parse(user);
        global.GBUser = user;
        setToken(user.token);
      }
      let requestFlag = {};
      requestFlag.os = Platform.OS === 'ios' ? 'IOS' : 'ANDROID';
      requestFlag.version = getVersion();
      requestFlag.machine = getUniqueId();
      requestFlag.clientId = getUniqueId();
      // let clientId = await SharePreference.getValue(Keys.clientId_key);
      // if (clientId) {
      //   requestFlag.clientId = clientId;
      // }
      if (Platform.OS === 'ios') {
        requestFlag.channel = 'appstore';
      } else {
        // let channel = await getAppMetaData('UMENG_CHANNEL');
        // if (channel) {
        //   requestFlag.channel = channel;
        // }
      }
      console.log('requestFlag', JSON.stringify(requestFlag));
      let request_flag = JSON.stringify(requestFlag); //encodeURI(JSON.stringify(requestFlag));
      setFlag(request_flag);
      SharePreference.saveValue(Keys.request_flag_key, request_flag);
      let showGuide = await SharePreference.getValue(Keys.show_guide_key);
      if (!showGuide) {
        this.showGuide = true;
        SharePreference.saveValue(Keys.show_guide_key, 'false');
        // AdService.startLoad();
      }
      checkAppUpdate();
    } catch (e) {
    } finally {
      this.setState({initFinsh: true});
      Splash.hide();
    }
  };

  _setPrompt = () => {
    global.GBPrompt = {
      showToast: (msg) => {
        if (!msg || msg.length == 0) {
          return;
        }
        this.setState({loading: false});
        this.toast && this.toast.show(msg);
      },
      showLoading: (loading) => {
        this.setState({loading});
      },
    };
  };
  _configOSS = () => {
    OSS.configWithHost(APPEnv.ossHost);
  };

  _handleNavigationChange = (prevState, newState, action) => {
    console.log('nav action', action);
    // console.log('newState',newState)
    // console.log('prevState',prevState)
    exitFlag = true;
    getRouterDeep(newState);
    const currentScreen = getCurrentRouteName(newState);
    const prevScreen = getCurrentRouteName(prevState);
    if (prevScreen !== currentScreen) {
      console.log('prevScreen', prevScreen);
      console.log('currentScreen', currentScreen);
    }
  };
  _onBackAndroid = () => {
    console.log('_onBackAndroid');
    if (exitFlag) {
      if (this.lastBackPressed && this.lastBackPressed + 2000 >= Date.now()) {
        BackHandler.exitApp();
        return false;
      }
      this.lastBackPressed = Date.now();
      this.toast && this.toast.show('再按一次退出程序');
      return true;
    }
    return false;
  };
  _loginChang = () => {
    if (GBUser) {
      setToken(GBUser.token);
    } else {
      setToken('');
    }
    this.setState({initFinsh: false}, () => {
      this.setState({initFinsh: true});
    });
  };
  _loginExpired = (type) => {
    if (GBUser) {
      global.GBUser = null;
      Alert.alert(
        type == 1 ? '登录过期' : '已在其他地方登录',
        '',
        [
          {
            //用于用户信息
            text: '确定',
            onPress: () => {
              this._loginChang();
            },
          },
        ],
        {cancelable: false},
      );
    }
  };
  _callback = () => {
    this.forceUpdate();
  };
}

const Loading = ({loading}) => {
  if (!loading) {
    return null;
  } else {
    return (
      <View
        style={{
          position: 'absolute',
          width: width,
          height: height,
          top: 0,
          left: 0,
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'transparent',
        }}>
        <View
          style={{
            width: 90,
            height: 80,
            justifyContent: 'center',
            alignItems: 'center',
            backgroundColor: 'black',
            borderRadius: 5,
            opacity: 0.8,
          }}>
          <LottieView
            style={{width: 110, height: 110}}
            autoPlay={true}
            loop={true}
            source={imageRes.loading}
          />
        </View>
      </View>
    );
  }
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F8F8F8',
  },
});
