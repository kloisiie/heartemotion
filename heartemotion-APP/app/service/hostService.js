import SharePreference from 'react-native-share-preference';
import Keys from '../constant/keys';
import {setBaseUrl} from 'react-native-request';

export default {
  getHost: async () => {
    let str = await SharePreference.getValue(Keys.host_data_key);
    console.log('设置的数据 ', str);
    let host = '';
    if (str && str.length > 0) {
      let data = JSON.parse(str);
      host = `${data.host}:${data.port}/`;
    }
    return host;
  },
};
