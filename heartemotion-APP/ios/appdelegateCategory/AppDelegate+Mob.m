//
//  AppDelegate+Mob.m
//  Base
//
//  Created by ns on 2020/9/11.
//

#import "AppDelegate+Mob.h"
#import <ShareSDK/ShareSDK.h>
#import <MOBFoundation/MobSDK+Privacy.h>
@implementation AppDelegate (Mob)
-(void)configMob:(NSDictionary*)launchOptions{
  [MobSDK uploadPrivacyPermissionStatus:YES onResult:^(BOOL success) {

  }];
  [ShareSDK registPlatforms:^(SSDKRegister *platformsRegister) {
    //QQ
    [platformsRegister setupQQWithAppId:@"100371282" appkey:@"aed9b0303e3ed1e27bae87c33761161d" enableUniversalLink:YES universalLink:@"https://ybpre.share2dlink.com/"];
    
    //更新到4.3.3或者以上版本，微信初始化需要使用以下初始化
    [platformsRegister setupWeChatWithAppId:@"wx617c77c82218ea2c" appSecret:@"c7253e5289986cf4c4c74d1ccc185fb1" universalLink:@"https://ybpre.share2dlink.com/"];
    
    
    //新浪
    [platformsRegister setupSinaWeiboWithAppkey:@"568898243" appSecret:@"38a4f8204cc784f81f9f0daaf31e02e3" redirectUrl:@"http://www.sharesdk.cn"];
    
    //Facebook
    [platformsRegister setupFacebookWithAppkey:@"1412473428822331" appSecret:@"a42f4f3f867dc947b9ed6020c2e93558" displayName:@"shareSDK"];
    
    //Twitter
    [platformsRegister setupTwitterWithKey:@"viOnkeLpHBKs6KXV7MPpeGyzE" secret:@"NJEglQUy2rqZ9Io9FcAU9p17omFqbORknUpRrCDOK46aAbIiey" redirectUrl:@"http://mob.com"];
    
    //Google+
    [platformsRegister setupGooglePlusByClientID:@"351114257251-isfr7cnt5gop930krntpf246d9ofv8j5.apps.googleusercontent.com" clientSecret:@"" redirectUrl:@"http://localhost"];
    
  }];
}
@end
