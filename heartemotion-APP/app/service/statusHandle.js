import imageRes from '../res/imgeRes';

const statusHandle = {
  getImage: (status) => {
    let image = null;
    if (status === 'NO_MOOD') {
      image = imageRes.icon_nomotion_52;
    } else if (status === 'CALMNESS') {
      image = imageRes.icon_mediumreview_52;
    } else if (status === 'AGITATED') {
      image = imageRes.icon_negativecomment_52;
    } else if (status === 'HAPPY') {
      image = imageRes.icon_goodreputation_52;
    }
    return image;
  },
  getStatusStr: (status) => {
    let str = null;
    if (status === 'NO_MOOD') {
      str = '无情绪';
    } else if (status === 'CALMNESS') {
      str = '平稳';
    } else if (status === 'AGITATED') {
      str = '烦躁';
    } else if (status === 'HAPPY') {
      str = '高兴';
    }
    return str;
  },
  getStatusColor: (status) => {
    let str = null;
    if (status === 'NO_MOOD') {
      str = '#707C93';
    } else if (status === 'CALMNESS') {
      str = '#2EBA80';
    } else if (status === 'AGITATED') {
      str = '#FF4E56';
    } else if (status === 'HAPPY') {
      str = '#FFA300';
    }
    return str;
  },
};

export default statusHandle;
