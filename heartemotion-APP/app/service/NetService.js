import {DeviceEventEmitter} from 'react-native';
import {
  GET,
  POST,
  PUT,
  DELETE,
  setErrorCallBack,
  setBaseUrl,
} from 'react-native-request';
import Api from '../constant/Api';
import NotifyNames from '../constant/NotifyNames';
import envConfig from '../config/envConfig';
setErrorCallBack((error) => {
  if (error.code == 403) {
    if (
      error.desc === 'error_token_expired' ||
      error.desc === 'error_token_conflict'
    ) {
      let type = error.desc === 'error_token_expired' ? 1 : 2;
      DeviceEventEmitter.emit(NotifyNames.login_expired_notify, type);
    }
  } else {
    GBPrompt.showToast(error.msg);
  }
});
setBaseUrl(envConfig.businessHost);
const NetService = {
  startAD: function () {
    let url = Api.common.advert;
    return GET(url);
  },
  appInit: function () {
    return GET(Api.common.appInit);
  },
  login: function () {
    return POST(Api.login);
  },
  deviceList: function () {
    return GET(Api.deviceList);
  },
  addDevice: function (deviceId, deviceName) {
    return POST(Api.addDevice, {deviceId, deviceName});
  },
  updateDevice: function (id, param) {
    return POST(Api.updateDevice + id, param);
  },
  deleteDevices: function (ids) {
    return POST(Api.deleteDevices + `?ids=${ids}`);
  },
  devicesDetail: function (id) {
    return GET(Api.devicesDetail + id);
  },
  warnList: function (param) {
    return GET(Api.warnList, param);
  },
  chartData: function (deviceId, hrTimeStart, hrTimeEnd) {
    let param = {};
    if (hrTimeStart && hrTimeEnd) {
      param = {hrTimeStart, hrTimeEnd};
    }
    return GET(Api.chartData + deviceId + '/list', param);
  },
  sendHeartData: function (deviceId, hrTime, hrValue) {
    return POST(Api.sendHeartData + deviceId + '/post', {hrTime, hrValue});
  },
  debugList: function () {
    return GET(Api.debugList, {pageIndex: 1, pageSize: 50});
  },
  deleteDebugFile: function (clientDebugFileIds) {
    return DELETE(
      Api.deleteDebugFile + `?clientDebugFileIds=${clientDebugFileIds}`,
    );
  },
  arithmeticlist: function () {
    return GET(Api.arithmeticlist);
  },
  arithmeticExecute: function (arithmeticId, clientDebugFileIds) {
    let param = {arithmeticId, clientDebugFileIds};
    return POST(Api.arithmeticExecute, param);
  },
  executeResult: function (taskId) {
    return GET(Api.executeResult + taskId);
  },
  platformDebugList: function (params) {
    return GET(Api.platformDebugList, params);
  },
  addPlatformFile: function (platformDebugFileIds) {
    return POST(
      Api.addPlatformFile + `?platformDebugFileIds=${platformDebugFileIds}`,
    );
  },
  addLocalFile: function (content, fileName) {
    return POST(Api.addLocalFile, {content, fileName});
  },
  addLabel: function (params) {
    return POST(Api.addLabel, params);
  },
  chartLabelData: function (deviceId, hrTimeStart, hrTimeEnd) {
    return GET(Api.chartLabelData + deviceId + '/label/list', {
      hrTimeStart,
      hrTimeEnd,
    });
  },
  warnPoll: function (deviceIds, timeMark) {
    return GET(Api.warnPoll, {deviceIds, timeMark});
  },
};
export default NetService;
