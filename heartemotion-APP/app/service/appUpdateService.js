import {AppState} from 'react-native';
import codePush from 'react-native-code-push';
import APPDeviceInfo from '../libs/common/APPDeviceInfo';
import {getVersion, getUniqueId} from 'react-native-device-info';
import NetService from './NetService';
let lastStatus = null;
function compareVersion(currentVersion, newVersion) {
  let arr = currentVersion.split('.');
  let newArr = newVersion.split('.');
  let length = newArr.length;
  for (let i = 0; i < length; i++) {
    let temp = parseInt(newArr[i]) - parseInt(arr[i]);
    if (temp == 0) {
      continue;
    }
    if (temp < 0) {
      console.log('没有app更新');
      return false;
    }
    if (temp > 0) {
      console.log('有app更新');
      return true;
    }
  }
  console.log('没有app更新');
  return false;
}

function checkHotUPdate() {
  AppState.addEventListener('change', (newState) => {
    console.log('newState', newState);
    if (lastStatus === 'background' && newState === 'active') {
      //每次app进入前台时候，检查是否有热更新
      codePush.sync(
        {
          updateDialog: {
            appendReleaseDescription: true,
            descriptionPrefix: '更新内容',
            title: '更新',
            mandatoryUpdateMessage: '',
            mandatoryContinueButtonLabel: '立即更新',
            optionalIgnoreButtonLabel: '稍后',
          },
          mandatoryInstallMode: codePush.InstallMode.IMMEDIATE,
        },
        (syncStatus) => {
          console.log('syncStatus', syncStatus);
        },
        (DownloadProgress) => {
          console.log('DownloadProgress', DownloadProgress);
        },
      );
    }
    lastStatus = newState;
  });
  codePush.sync(
    {
      updateDialog: {
        appendReleaseDescription: true,
        descriptionPrefix: '更新内容',
        title: '更新',
        mandatoryUpdateMessage: '',
        mandatoryContinueButtonLabel: '更新',
        optionalIgnoreButtonLabel: '取消',
      },
      mandatoryInstallMode: codePush.InstallMode.IMMEDIATE,
    },
    (syncStatus) => {
      console.log('syncStatus', syncStatus);
    },
    (DownloadProgress) => {
      console.log('DownloadProgress', DownloadProgress);
    },
  );
}

async function appUPdate(version) {
  try {
    let {res, error} = await NetService.appInit();
    console.log('111111111');
    if (!error && res) {
      console.log('222222222');
      let desc = '';
      let versionUrl = '';
      if (APPDeviceInfo.isIos()) {
        let iosAppVersion = res.iosAppVersion;
        let newVersion = iosAppVersion.version;
        if (newVersion == -1) {
          checkHotUPdate();
          return;
        }
        let flag = compareVersion(version, newVersion);
        if (flag) {
          desc = iosAppVersion.versionMessage;
          versionUrl = iosAppVersion.versionUrl;
        } else {
          checkHotUPdate();
          return;
        }
      } else {
        let iosAppVersion = res.androidAppVersion;
        let newVersion = iosAppVersion.version;
        if (newVersion == -1) {
          checkHotUPdate();
          return;
        }
        let flag = compareVersion(version, newVersion);
        if (flag) {
          desc = iosAppVersion.versionMessage;
          versionUrl = iosAppVersion.versionUrl;
        } else {
          checkHotUPdate();
          return;
        }
      }
      GBNavgator.navigate('aPPUpdate', {desc, versionUrl});
    } else {
      checkHotUPdate();
    }
  } catch (e) {
    checkHotUPdate();
  }
}

export async function checkAppUpdate() {
  if (__DEV__) {
    return;
  }
  checkHotUPdate();
  // let version = getVersion();
  // if (version) {
  //   appUPdate(version);
  // } else {
  //   checkHotUPdate();
  // }
}

async function appUPdate2(version) {
  let {res, error} = await NetService.appInit();
  if (!error && res) {
    let desc = '';
    let versionUrl = '';
    if (APPDeviceInfo.isIos()) {
      let iosAppVersion = res.iosAppVersion;
      let newVersion = iosAppVersion.version;
      if (newVersion == -1) {
        GBPrompt.showToast('App已经是最新版本');
        return;
      }
      let flag = compareVersion(version, newVersion);
      if (flag) {
        desc = iosAppVersion.versionMessage;
        versionUrl = iosAppVersion.versionUrl;
      } else {
        GBPrompt.showToast('App已经是最新版本');
        return;
      }
    } else {
      let iosAppVersion = res.androidAppVersion;
      let newVersion = iosAppVersion.version;
      if (newVersion == -1) {
        GBPrompt.showToast('App已经是最新版本');
        return;
      }
      let flag = compareVersion(version, newVersion);
      if (flag) {
        desc = iosAppVersion.versionMessage;
        versionUrl = iosAppVersion.versionUrl;
      } else {
        GBPrompt.showToast('App已经是最新版本');
        return;
      }
    }
    GBNavgator.navigate('aPPUpdate', {desc, versionUrl});
  }
}

export async function checkAppUpdate2() {
  let version = getVersion();
  if (version) {
    appUPdate2(version);
  } else {
    GBPrompt.showToast('App已经是最新版本');
  }
}
