import {Dimensions, Platform, StatusBar} from 'react-native';
import {Header} from '@react-navigation/stack';
export default {
  //状态栏、底部标签栏、顶部导航栏高度
  isIPhoneX() {
    let dimen = Dimensions.get('window');
    return (
      Platform.OS === 'ios' &&
      !Platform.isPad &&
      !Platform.isTVOS &&
      (dimen.height >= 812 || dimen.width >= 812)
    );
  },

  //返回:状态栏的高度
  statusbarHeight() {
    if (Platform.OS === 'ios') {
      if (this.isIPhoneX()) {
        return 44;
      } else {
        return 20;
      }
    }
    return StatusBar.currentHeight;
  },

  //标题栏的高度
  navbarHeight() {
    return 44;
  },

  tabbarHeight() {
    if (Platform.OS === 'ios') {
      if (this.isIPhoneX()) {
        return 83;
      } else {
        return 49;
      }
    }
    return 49;
  },
  //获取beeshell的model从底部弹起的offsetY
  bottomOffsetY() {
    let dimen = Dimensions.get('window');
    if (Platform.OS === 'ios') {
      return dimen.height;
    }
    return dimen.height - StatusBar.currentHeight;
  },

  statusAndNavbarHeight() {
    return this.statusbarHeight() + this.navbarHeight();
  },

  isIos() {
    return Platform.OS === 'ios';
  },

  isAndroid() {
    return Platform.OS !== 'ios';
  },
};
