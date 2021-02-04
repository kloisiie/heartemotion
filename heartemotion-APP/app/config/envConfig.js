/**
 * dev 测试环境配置
 * release 正式环境配置
 * isDevEnv  设置是否是测试环境
 *
 */
const config = {
  //测试
  dev: {
    businessHost: 'http://dev.blitzcrank.beiru168.com/',
    h5Host: 'http://dev.h5.beiru168.com/galio/index.html#/',
    ossHost: 'http://dev.blitzcrank.beiru168.com',
  },
  //正式
  release: {
    businessHost: 'https://app-v1.51dongwo.com/',
    h5Host: 'http://h5.51dongwo.com/index.html#/',
    ossHost: 'http://dev.blitzcrank.beiru168.com',
  },
  //环境控制变量
  isDevEnv: true,
};
const APPEnv = config.isDevEnv ? config.dev : config.release;
export default APPEnv;
