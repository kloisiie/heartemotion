//
//  NetworkRequest.m
//  QSYRN
//
//  Created by 南杉科技 on 2018/1/22.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "NetworkRequest.h"
#import "HttpTool3.h"
#define BaseUrl @""
@implementation NetworkRequest

#pragma mark - 工程初始化配置接口
+(void)requestGailunInitConfigCallback:(Callback)callback  Failure:(Failure)failure{
   NSString* url = [NSString stringWithFormat:@"%@/api/v1/app/init",BaseUrl];
  [[HttpTool3 shareHttpTool] GETUrl:url parameters:nil success:^(id  _Nonnull responseObject) {
    callback(responseObject);
  } failure:^(NSError * _Nonnull error) {
    failure(error);
  }];
  
}


#pragma mark -工程初始化配置接口
+(void)requestMLVBUserSigCallback:(Callback)callback  Failure:(Failure)failure{
   NSString* url = [NSString stringWithFormat:@"%@/api/v1/anchor/studio/sign",BaseUrl];
  [[HttpTool3 shareHttpTool] GETUrl:url parameters:nil success:^(id  _Nonnull responseObject) {
     callback(responseObject);
   } failure:^(NSError * _Nonnull error) {
     failure(error);
   }];
}

+ (void)requestAuthorCodeWithDict:(NSDictionary *)dict Callback:(Callback)callback Failure:(Failure)failure{
   NSString* url = [NSString stringWithFormat:@"https://graph.qq.com/oauth2.0/authorize"];
  [[HttpTool3 shareHttpTool] GETUrl:url parameters:dict success:^(id  _Nonnull responseObject) {
    callback(responseObject);
  } failure:^(NSError * _Nonnull error) {
    failure(error);
  }];
 
}
  
@end
