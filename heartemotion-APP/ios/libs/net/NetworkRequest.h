//
//  NetworkRequest.h
//  QSYRN
//
//  Created by 南杉科技 on 2018/1/22.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
typedef void (^Callback)(id obj);
typedef void(^Failure)(NSError* error);
@interface NetworkRequest : NSObject




#pragma mark -工程初始化配置接口
+(void)requestGailunInitConfigCallback:(Callback)callback  Failure:(Failure)failure;

#pragma mark -工程初始化配置接口
+(void)requestMLVBUserSigCallback:(Callback)callback  Failure:(Failure)failure;

+ (void)requestAuthorCodeWithDict:(NSDictionary *)dict Callback:(Callback)callback Failure:(Failure)failure;
@end
