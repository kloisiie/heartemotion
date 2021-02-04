import {NativeModules, Platform} from 'react-native';
let WristbandMoudle = NativeModules.WristbandMoudle;
const WristbandBridge = {
  findDevices() {
    WristbandMoudle.findDevices();
  },
  stopFind() {
    WristbandMoudle.stopFind();
  },
  async connectDeivce(deviceId) {
    let result = await WristbandMoudle.connectDeivce(deviceId);
    return result;
  },
  async disconnectDeivce(deviceId) {
    let result = await WristbandMoudle.disconnectDeivce(deviceId);
    return result;
  },
  sendLocalNotify(arr) {
    WristbandMoudle.sendLocalNotify(arr);
  },
};
export default WristbandBridge;
