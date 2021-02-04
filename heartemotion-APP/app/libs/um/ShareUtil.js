/**
 * Created by wangfei on 17/8/28.
 */
import {NativeModules} from 'react-native';
const ShareModule = NativeModules.MobShareModule;
class ShareUtil {
  /**
   *直接分享
   * @param text 描述
   * @param icon 缩略图
   * @param link 链接
   * @param title 标题
   * @param platform 分享的平台：0, //新浪 1, //微信聊天 2,//微信朋友圈 3,//微信收藏 4,//QQ聊天页面 5,//qq空间
   * @param callback 分享结果回调 (code msg) code为200分享成功 msg为结果描述
   */
  static share(platform, title, content, url, images, callback) {
    ShareModule.share(platform, title, content, url, images, callback);
  }
}
export default ShareUtil;
