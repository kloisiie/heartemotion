import {NativeModules, Platform} from 'react-native';
let appCommonMoudle = NativeModules.APPCommonMoudle;
const APPNativeBridge = {
  //跳转客服
  conectService() {
    appCommonMoudle.conectService();
  },
  async checkBlueStatus() {
    let result = appCommonMoudle.checkBlueStatus();
    return result;
  },
  async openBlue() {
    let result = appCommonMoudle.openBlue();
    return result;
  },
};
export default APPNativeBridge;
