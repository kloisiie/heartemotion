//
//  AppDelegate+QY.m
//  Base
//
//  Created by ns on 2020/5/14.
//

#import "AppDelegate+QY.h"
#import <QYSDK/QYSDK.h>
@implementation AppDelegate (QY)
-(void)configQY:(NSDictionary*)launchOptions{
  NSString *appKey = @"your app key";
  QYSDKOption *option = [QYSDKOption optionWithAppKey:appKey];
  option.appName = @"your App name";
  [[QYSDK sharedSDK] registerWithOption:option];
}
@end
