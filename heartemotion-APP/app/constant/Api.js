import envConfig from '../config/envConfig';

let BaseUrl = envConfig.businessHost;
let H5BaseUrl = envConfig.h5Host;
let Api = {
  common: {
    advert: 'api/v1/advert/list',
    appInit: 'api/v1/app/init',
  },
  login: 'api/v1/client/login',
  deviceList: 'api/access/v1/hr/device/list',
  addDevice: 'api/access/v1/hr/device/add',
  updateDevice: 'api/v1/hr/device/update/',
  deleteDevices: 'api/access/v1/hr/device/del',
  devicesDetail: 'api/access/v1/hr/device/details/',
  warnList: 'api/access/v1/hr/police/list',
  chartData: 'api/access/v1/hr/heart-rate/',
  sendHeartData: 'api/access/v1/hr/heart-rate/',
  debugList: 'api/access/v1/hr/client-debug-file/list',
  deleteDebugFile: 'api/access/v1/hr/client-debug-file/del',
  arithmeticlist: 'api/access/v1/hr/arithmetic/list',
  arithmeticExecute: 'api/access/v1/hr/debug/execute',
  executeResult: 'api/access/v1/hr/debug/execute/result/',
  platformDebugList: 'api/access/v1/hr/platform-debug-file/list',
  addPlatformFile: 'api/access/v1/hr/client-debug-file/platform/add/',
  addLocalFile: 'api/access/v1/hr/client-debug-file/local/add',
  addLabel: 'api/access/v1/hr/label/add',
  chartLabelData: 'api/access/v1/hr/heart-rate/',
  warnPoll: 'api/access/v1/hr/police/poll',
};
export default Api;
