#import "AppDelegate.h"
#import "RootViewController.h"
#import <React/RCTBridge.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
#import <OSSManager.h>
#import <CodePush.h>
#import <RNSplashScreen.h>
#import <IQKeyboardManager.h>
#import <QYSDK/QYSDK.h>
#import "AppDelegate+QY.h"
#import "AppDelegate+Mob.h"
#import "AppDelegate+UM.h"
#import "JPUSHService.h"
// iOS10 注册 APNs 所需头文件
#ifdef NSFoundationVersionNumber_iOS_9_x_Max
#import <UserNotifications/UserNotifications.h>
#endif



#if DEBUG
#import <FlipperKit/FlipperClient.h>
#import <FlipperKitLayoutPlugin/FlipperKitLayoutPlugin.h>
#import <FlipperKitUserDefaultsPlugin/FKUserDefaultsPlugin.h>
#import <FlipperKitNetworkPlugin/FlipperKitNetworkPlugin.h>
#import <SKIOSNetworkPlugin/SKIOSNetworkAdapter.h>
#import <FlipperKitReactPlugin/FlipperKitReactPlugin.h>

static void InitializeFlipper(UIApplication *application) {
  FlipperClient *client = [FlipperClient sharedClient];
  SKDescriptorMapper *layoutDescriptorMapper = [[SKDescriptorMapper alloc] initWithDefaults];
  [client addPlugin:[[FlipperKitLayoutPlugin alloc] initWithRootNode:application withDescriptorMapper:layoutDescriptorMapper]];
  [client addPlugin:[[FKUserDefaultsPlugin alloc] initWithSuiteName:nil]];
  [client addPlugin:[FlipperKitReactPlugin new]];
  [client addPlugin:[[FlipperKitNetworkPlugin alloc] initWithNetworkAdapter:[SKIOSNetworkAdapter new]]];
  [client start];
}
#endif

@interface AppDelegate()<JPUSHRegisterDelegate>

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
#if DEBUG
  InitializeFlipper(application);
#endif
  
  RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:launchOptions];
  RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge
                                                   moduleName:@"Base"
                                            initialProperties:nil];
  
  rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
  
  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  RootViewController *rootViewController = [RootViewController new];
  rootViewController.view = rootView;
  UINavigationController* rootNav = [[UINavigationController alloc]
                                     initWithRootViewController:rootViewController];
  rootViewController.navigationController.navigationBar.translucent = NO;
  rootViewController.navigationController.navigationBar.hidden = YES;
  rootViewController.edgesForExtendedLayout = UIRectEdgeNone;
  self.window.rootViewController = rootNav;
  [self.window makeKeyAndVisible];
  if (@available(iOS 13.0, *)) {
    self.window.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
  }
  //键盘问题
  [IQKeyboardManager sharedManager].enable = YES;
  [IQKeyboardManager sharedManager].enableAutoToolbar = NO;

  [self configQY:launchOptions];
  [self configMob:launchOptions];
  [self registerJpush:launchOptions];
  [RNSplashScreen show];
  return YES;
}

#pragma mark - 配置极光推送
-(void)registerJpush:(NSDictionary *)launchOptions{
  //Required
  //notice: 3.0.0 及以后版本注册可以这样写，也可以继续用之前的注册方式
  JPUSHRegisterEntity * entity = [[JPUSHRegisterEntity alloc] init];
  entity.types = JPAuthorizationOptionAlert|JPAuthorizationOptionBadge|JPAuthorizationOptionSound|JPAuthorizationOptionProvidesAppNotificationSettings;
  if ([[UIDevice currentDevice].systemVersion floatValue] >= 8.0) {
    // 可以添加自定义 categories
    // NSSet<UNNotificationCategory *> *categories for iOS10 or later
    // NSSet<UIUserNotificationCategory *> *categories for iOS8 and iOS9
  }
  [JPUSHService registerForRemoteNotificationConfig:entity delegate:self];

  // Required
  // init Push
  // notice: 2.1.5 版本的 SDK 新增的注册方法，改成可上报 IDFA，如果没有使用 IDFA 直接传 nil
//  appKey
//  选择 Web Portal 上 的应用 ，点击“设置”获取其 appkey 值。请确保应用内配置的 appkey 与 Portal 上创建应用后生成的 appkey 一致。
//  channel
//  指明应用程序包的下载渠道，为方便分渠道统计，具体值由你自行定义，如：App Store。
//  apsForProduction
//  1.3.1 版本新增，用于标识当前应用所使用的 APNs 证书环境。
//  0（默认值）表示采用的是开发证书，1 表示采用生产证书发布应用。
//  注：此字段的值要与 Build Settings的Code Signing 配置的证书环境一致。
  [JPUSHService setupWithOption:launchOptions appKey:@""
                        channel:@"App Store"
               apsForProduction:0
          advertisingIdentifier:nil];
  //监听极光的应用内消息，即是透传
  NSNotificationCenter *defaultCenter = [NSNotificationCenter defaultCenter];
  [defaultCenter addObserver:self selector:@selector(networkDidReceiveMessage:) name:kJPFNetworkDidReceiveMessageNotification object:nil];
  
}


- (void)application:(UIApplication *)application
didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
  if (![deviceToken isKindOfClass:[NSData class]]) return;
  const unsigned *tokenBytes = (const unsigned *)[deviceToken bytes];
  NSString *hexToken = [NSString stringWithFormat:@"%08x%08x%08x%08x%08x%08x%08x%08x",
                        ntohl(tokenBytes[0]), ntohl(tokenBytes[1]), ntohl(tokenBytes[2]),
                        ntohl(tokenBytes[3]), ntohl(tokenBytes[4]), ntohl(tokenBytes[5]),
                        ntohl(tokenBytes[6]), ntohl(tokenBytes[7])];
  NSLog(@"deviceToken:%@",hexToken);
  [[NSUserDefaults standardUserDefaults] setObject:hexToken forKey:@"clientId_key"];
   [JPUSHService registerDeviceToken:deviceToken];
}

#pragma mark- JPUSHRegisterDelegate

// iOS 12 Support
- (void)jpushNotificationCenter:(UNUserNotificationCenter *)center openSettingsForNotification:(UNNotification *)notification{
  if (notification && [notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
    //从通知界面直接进入应用
  }else{
    //从通知设置界面进入应用
  }
}

// iOS 10 Support
- (void)jpushNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(NSInteger))completionHandler {
  // Required
  NSDictionary * userInfo = notification.request.content.userInfo;
  if([notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
    [JPUSHService handleRemoteNotification:userInfo];
  }
  completionHandler(UNNotificationPresentationOptionAlert); // 需要执行这个方法，选择是否提醒用户，有 Badge、Sound、Alert 三种类型可以选择设置
}

// iOS 10 Support
- (void)jpushNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler {
  // Required
  NSDictionary * userInfo = response.notification.request.content.userInfo;
  if([response.notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
    [JPUSHService handleRemoteNotification:userInfo];
  }
  completionHandler();  // 系统要求执行这个方法
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {

  // Required, iOS 7 Support
  [JPUSHService handleRemoteNotification:userInfo];
  completionHandler(UIBackgroundFetchResultNewData);
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {

  // Required, For systems with less than or equal to iOS 6
  [JPUSHService handleRemoteNotification:userInfo];
}

- (void)networkDidReceiveMessage:(NSNotification *)notification {
     NSDictionary * userInfo = [notification userInfo];
     NSString *content = [userInfo valueForKey:@"content"];
     NSString *messageID = [userInfo valueForKey:@"_j_msgid"];
     NSDictionary *extras = [userInfo valueForKey:@"extras"];
     NSString *customizeField1 = [extras valueForKey:@"customizeField1"]; //服务端传递的 Extras 附加字段，key 是自己定义的
 }

#pragma mark - RCTBridgeDelegate
- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
#if DEBUG
  return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];
#else
  return  [CodePush bundleURL];
#endif
}






@end
