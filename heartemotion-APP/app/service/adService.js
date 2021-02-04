import RNFetchBlob from 'rn-fetch-blob';
import SharePreference from 'react-native-share-preference';
import Keys from '../constant/keys';
import NetService from './NetService';
const AdService = {
  startLoad: async function () {
    let {res, error} = await NetService.startAD();
    console.log('11111111111');
    if (!error && res && res.length > 0) {
      console.log('222222222');
      let count = 0;
      let length = res.length;
      for (let i = 0; i < length; i++) {
        let item = res[i];
        console.log('3333333  ', item.images);
        RNFetchBlob.config({
          // add this option that makes response data to be stored as a file,
          // this is much more performant.
          fileCache: true,
          appendExt: 'png',
          key: item.images,
        })
          .fetch('GET', item.images)
          .then((result) => {
            // the temp file path
            console.log('The file saved to ', result.path());
            item.path = result.path();
            count++;
            if (count == length) {
              SharePreference.saveValue(
                Keys.advert_list_key,
                JSON.stringify(res),
              );
            }
          })
          .catch((error) => {
            count++;
          });
      }
    }
  },
};

export default AdService;
