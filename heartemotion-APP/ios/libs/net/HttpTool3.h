//
//  HttpTool3.h
//  lulu
//
//  Created by ns on 2019/11/22.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void (^HttpProgress)(NSProgress *progress);

@interface HttpTool3 : NSObject
+ (instancetype)shareHttpTool;
/**
 GET请求接口
 @param url 请求接口
 @param parameters 接口传入参数内容
 @param successful 成功Block返回
 @param failure 失败Block返回
 */

- (void)GETUrl:(NSString *)url
    parameters:(NSDictionary *)parameters
       success:(void (^)(id responseObject))success
       failure:(void (^)(NSError *error))failure;


/**
 POST请求接口
 @param url 请求接口
 @param parameters 接口传入参数
 @param successful 成功Block返回
 @param failure 失败Block返回
 */
- (void)POSTUrl:(NSString *)url
     parameters:(NSDictionary *)parameters
        success:(void (^)(id responseObject))success
        failure:(void (^)(NSError *error))failure;

/**
 PUT请求接口
 @param url 请求接口
 @param parameters 接口传入参数
 @param successful 成功Block返回
 @param failure 失败Block返回
 */
- (void)PUTUrl:(NSString *)url
     parameters:(NSDictionary *)parameters
        success:(void (^)(id responseObject))success
        failure:(void (^)(NSError *error))failure;

/**
 DELETE请求接口
 @param url 请求接口
 @param parameters 接口传入参数
 @param successful 成功Block返回
 @param failure 失败Block返回
 */
- (void)DELETEUrl:(NSString *)url
    parameters:(NSDictionary *)parameters
       success:(void (^)(id responseObject))success
       failure:(void (^)(NSError *error))failure;

/**
 下载文件接口
 
 @param url 请求接口
 @param progress 下载进度
 @param downloadFilePath 文件保存路径
 @param successful  返回路径内容
 @param failure 失败返回
 */
- (void)downloadUrl:(NSString *)url
           progress:(HttpProgress)progress
   downloadFilePath:(NSString *)downloadFilePath
            success:(void (^)(id responseObject))success
            failure:(void (^)(NSError *error))failure;

/**
 图片上传接口(上传音频与图片是一致的，需要更改的只是 mimeType类型，根据要求设置对应的格式即可)
 
 @param url 请求接口
 @param paramters 请求参数
 @param pictureData 图片数据
 @param pictureKey 与后台约定的 文件key
 @param progress 上传进度
 @param successful 成功返回
 @param failure 失败返回
 */
- (void)UploadUrl:(NSString *)url parameters:(NSDictionary *)paramters
            pictureData:(NSData *)pictureData
             pictureKey:(NSString *)pictureKey
               progress:(HttpProgress)progress
                success:(void (^)(id responseObject))success
                failure:(void (^)(NSError *error))failure;


@end


