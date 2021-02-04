//
//  APPCommonMoudle.m
//  Base
//
//  Created by ns on 2020/5/14.
//

#import "APPCommonMoudle.h"
#import <QYSDK/QYSDK.h>
#import "JsonConvert.h"

@implementation APPCommonMoudle
RCT_EXPORT_MODULE();
- (dispatch_queue_t)methodQueue{
  return dispatch_get_main_queue();
}
RCT_EXPORT_METHOD(conectService){
  NSString* userStr = [[NSUserDefaults standardUserDefaults] objectForKey:@"user_info_key"];
  NSString* flag = [[NSUserDefaults standardUserDefaults] objectForKey:@"request_flag_key"];
  if(flag){
    NSString* config = [flag stringByRemovingPercentEncoding];
    if(config){
      NSDictionary* dic = [JsonConvert dictionaryWithJsonString:config];
      QYUserInfo *userInfo = [[QYUserInfo alloc] init];
      userInfo.userId = dic[@"machine"];
      if(userStr){
        NSDictionary* userDic = [JsonConvert dictionaryWithJsonString:userStr];
        NSMutableArray* mutArr = [NSMutableArray array];
        for (NSString* key in userDic) {
          [mutArr addObject:@{@"key":key,@"value":userDic[key]}];
        }
        userInfo.data = [JsonConvert toJSONString:[mutArr copy]];
      }
      [[QYSDK sharedSDK] setUserInfo:userInfo];
    }
  }
  QYCustomUIConfig* config =  [[QYSDK sharedSDK] customUIConfig];
  config.autoShowKeyboard =false;
  QYSource *source = [[QYSource alloc] init];
  source.title = @"七鱼客服";
  source.urlString = @"https://qiyukf.com/";
  QYSessionViewController *sessionViewController = [[QYSDK sharedSDK] sessionViewController];
  sessionViewController.sessionTitle = @"七鱼客服";
  sessionViewController.source = source;
  sessionViewController.hidesBottomBarWhenPushed = YES;
  UIBarButtonItem *leftItem= [[UIBarButtonItem alloc] initWithImage:[[UIImage imageNamed:@"icon_back_24"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal] style:UIBarButtonItemStyleDone target:self action:@selector(onBack)];
  sessionViewController.navigationItem.leftBarButtonItem = leftItem;
  UINavigationController* nav = (UINavigationController*)[UIApplication sharedApplication].keyWindow.rootViewController;
  [nav pushViewController:sessionViewController animated:YES];
}

-(void)onBack{
  UINavigationController* nav = (UINavigationController*)[UIApplication sharedApplication].keyWindow.rootViewController;
  [nav popViewControllerAnimated:YES];
  
}

@end
