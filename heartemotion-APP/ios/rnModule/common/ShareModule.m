//
//  ShareModule.m
//  Base
//
//  Created by ns on 2020/9/11.
//

#import "ShareModule.h"
#import <ShareSDK/ShareSDK.h>
#import <ShareSDKUI/ShareSDK+SSUI.h>

@implementation ShareModule
RCT_EXPORT_MODULE(MobShareModule);
- (dispatch_queue_t)methodQueue{
  return dispatch_get_main_queue();
  
}

RCT_EXPORT_METHOD(share:(int)type title:(NSString*)title content:(NSString*)content url:(NSString*)url images:(NSArray*)images response:(RCTResponseSenderBlock)completion){
  NSMutableDictionary*shareParams = [NSMutableDictionary dictionary];
  [shareParams SSDKSetupShareParamsByText:content images:images url:[NSURL URLWithString:url] title:title type:SSDKContentTypeAuto];
  SSDKPlatformType paltType = SSDKPlatformTypeWechat;
  if(type ==0){
    type = SSDKPlatformTypeWechat;
  }
  [ShareSDK share:paltType parameters:shareParams onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) {
    switch (state) {
      case SSDKResponseStateSuccess:
        NSLog(@"成功");//成功
        completion(@[@(200)]);
        break;
      case SSDKResponseStateFail:
      {
        NSLog(@"--%@",error.description);
        //失败
        completion(@[@(0)]);
        break;
      }
      case SSDKResponseStateCancel:
        completion(@[@(0)]);
        break;
      default:
        completion(@[@(0)]);
        break;
    }
  }];
}
@end
