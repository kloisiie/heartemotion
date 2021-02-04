//
//  AppDelegate+UM.m
//  Base
//
//  Created by ns on 2020/9/11.
//

#import "AppDelegate+UM.h"
#import <UMCommon/UMCommon.h>
#import <UMCommonLog/UMCommonLogHeaders.h>
#import <UMCommon/MobClick.h>
@implementation AppDelegate (UM)
-(void)configUM:(NSDictionary*)launchOptions{
  [UMConfigure initWithAppkey:@"Your appkey" channel:@"App Store"];
  [MobClick setAutoPageEnabled:true];
  [UMConfigure setLogEnabled:true];
  [UMCommonLogManager setUpUMCommonLogManager];
}
@end
