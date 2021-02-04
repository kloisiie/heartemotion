//
//  HttpTool3.m
//  lulu
//
//  Created by ns on 2019/11/22.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "HttpTool3.h"
#import <AFNetworking.h>
#define CODE 200
@implementation HttpTool3
static HttpTool3 *_instance;
static AFHTTPSessionManager *_sessionManager;

#pragma mark - methods

+ (instancetype)shareHttpTool
{
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    _instance = [[HttpTool3 alloc] init];
  });
  return _instance;
}

-(void)GETUrl:(NSString *)url parameters:(NSDictionary *)parameters success:(void (^)(id _Nonnull))success failure:(void (^)(NSError * _Nonnull))failure{
  [self configHeader];
  [_sessionManager GET:url parameters:parameters headers:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    [self handleRes:responseObject success:success failure:failure];
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    failure(error);
  }];
}

-(void)POSTUrl:(NSString *)url parameters:(NSDictionary *)parameters success:(void (^)(id _Nonnull))success failure:(void (^)(NSError * _Nonnull))failure{
  [self configHeader];
  [_sessionManager POST:url parameters:parameters headers:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    [self handleRes:responseObject success:success failure:failure];
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    failure(error);
  }];
}

-(void)PUTUrl:(NSString *)url parameters:(NSDictionary *)parameters success:(void (^)(id _Nonnull))success failure:(void (^)(NSError * _Nonnull))failure{
  [self configHeader];
  [_sessionManager PUT:url parameters:parameters headers:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    [self handleRes:responseObject success:success failure:failure];
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    failure(error);
  }];
}

-(void)DELETEUrl:(NSString *)url parameters:(NSDictionary *)parameters success:(void (^)(id _Nonnull))success failure:(void (^)(NSError * _Nonnull))failure{
  [self configHeader];
  [_sessionManager DELETE:url parameters:parameters headers:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    [self handleRes:responseObject success:success failure:failure];
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    failure(error);
  }];
}

-(void)downloadUrl:(NSString *)url progress:(HttpProgress)progress downloadFilePath:(NSString *)downloadFilePath success:(void (^)(id))success failure:(void (^)(NSError *))failure{
  [self configHeader];
  //下载地址
  NSURL *downloadURL = [NSURL URLWithString:url];
  //设置请求
  NSURLRequest *request = [NSURLRequest requestWithURL:downloadURL];
  //下载操作
  [_sessionManager downloadTaskWithRequest:request progress:^(NSProgress * _Nonnull downloadProgress) {
    //下载进度
    dispatch_sync(dispatch_get_main_queue(), ^{
      progress ? progress(downloadProgress) : nil;
    });
    
  } destination:^NSURL * _Nonnull(NSURL * _Nonnull targetPath, NSURLResponse * _Nonnull response) {
    //拼接缓存目录
    NSString *downloadPath = [[NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) lastObject] stringByAppendingPathComponent:downloadFilePath ? downloadFilePath : @"Download"];
    //打开文件管理器
    NSFileManager *fileManager = [NSFileManager defaultManager];
    //创建Download目录
    [fileManager createDirectoryAtPath:downloadPath withIntermediateDirectories:YES attributes:nil error:nil];
    //拼接文件路径
    NSString *filePath = [downloadPath stringByAppendingPathComponent:response.suggestedFilename];
    //返回文件位置的URL路径
    return [NSURL fileURLWithPath:filePath];
  } completionHandler:^(NSURLResponse * _Nonnull response, NSURL * _Nullable filePath, NSError * _Nullable error) {
    NSInteger responseCode = [self showResponseCode:response];
    if (responseCode != 200) {
     success ? success(filePath.absoluteString) : nil;
    }else {
      failure ? failure(error) : nil;
    }
  }];
}

-(void)UploadUrl:(NSString *)url parameters:(NSDictionary *)paramters pictureData:(NSData *)pictureData pictureKey:(NSString *)pictureKey progress:(HttpProgress)progress success:(void (^)(id))success failure:(void (^)(NSError *))failure{
  [_sessionManager POST:url parameters:paramters headers:nil constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
    //对上传完文件的配置
    //获取当前时间（int 时间戳转换）
    int nowDate = [[NSString stringWithFormat:@"%ld",(long)[[NSDate date] timeIntervalSince1970]]intValue];
    NSString *fileName = [NSString stringWithFormat:@"%d.jpg",nowDate];
    //参数介绍
    //fileData : 图片资源  name : 预定key   fileName  : 文件名  mimeType    : 资源类型(根据后台进行对应配置)
    [formData appendPartWithFileData:pictureData name:pictureKey fileName:fileName mimeType:@"image/jpeg"];
    
  } progress:^(NSProgress * _Nonnull uploadProgress) {
    //上传进度
    dispatch_sync(dispatch_get_main_queue(), ^{
      progress ? progress(uploadProgress) : nil;
    });
    
  } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    [self handleRes:responseObject success:success failure:failure];
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    failure(error);
  }];
}
#pragma mark - private

+ (void)initialize {
  _sessionManager = [AFHTTPSessionManager manager];
  _sessionManager.requestSerializer.timeoutInterval = 5.f;
  _sessionManager.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"application/json", @"text/html", @"text/json", @"text/plain", @"text/javascript", @"text/xml", @"image/*", nil];
  // 此处可添加对应的等待效果
}


+ (id)allocWithZone:(struct _NSZone *)zone
{
  //调用dispatch_once保证在多线程中也只被实例化一次
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    _instance = [super allocWithZone:zone];
  });
  return _instance;
}



- (id)copyWithZone:(NSZone *)zone
{
  return _instance;
}

- (id)init
{
  self = [super init];
  if (self)
  {
    
  }
  
  return self;
}
/**
 输出http响应的状态码
 
 @param response 响应数据
 */
- (NSUInteger)showResponseCode:(NSURLResponse *)response {
  NSHTTPURLResponse* httpResponse = (NSHTTPURLResponse*)response;
  NSInteger responseStatusCode = [httpResponse statusCode];
  return responseStatusCode;
}

-(void)handleRes:(id _Nonnull)dic success:(void (^)(id _Nonnull))success failure:(void (^)(NSError * _Nonnull))failure{
 // NSDictionary * dic = [NSJSONSerialization JSONObjectWithData:res options:NSJSONReadingMutableLeaves error:nil];
  int code = [dic[@"code"] intValue];
  if(code == CODE){
    success(dic);
  }else{
    NSError* error= [NSError errorWithDomain:dic[@"msg"] code:code userInfo:dic];
    failure(error);
  }

}

-(void)configHeader{
  NSString* userStr = [[NSUserDefaults standardUserDefaults] objectForKey:@"user_info_key"];
  NSDictionary * dic = [self dictionaryWithJsonString:userStr];
  if(dic){
    [_sessionManager.requestSerializer setValue:dic[@"token"] forHTTPHeaderField:@"Authorization"];
  }else{
     [_sessionManager.requestSerializer setValue:nil forHTTPHeaderField:@"Authorization"];
  }
  NSString* flag = [[NSUserDefaults standardUserDefaults] objectForKey:@"request_flag_key"];
  [_sessionManager.requestSerializer setValue:flag forHTTPHeaderField:@"request-flag"];
}


-(NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString
{
  if (jsonString == nil) {
    return nil;
  }
  
  NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
  NSError *err;
  NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:jsonData
                                                      options:NSJSONReadingMutableContainers
                                                        error:&err];
  if(err)
  {
    NSLog(@"json解析失败：%@",err);
    return nil;
  }
  return dic;
}

-(NSString *)toJSONString:(NSDictionary*)dic {
  NSData *data = [NSJSONSerialization dataWithJSONObject:dic
                                                 options:NSJSONReadingMutableLeaves | NSJSONReadingAllowFragments
                                                   error:nil];
  
  if (data == nil) {
    return nil;
  }
  
  NSString *string = [[NSString alloc] initWithData:data
                                           encoding:NSUTF8StringEncoding];
  return string;
}

@end


