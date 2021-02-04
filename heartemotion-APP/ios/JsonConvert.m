//
//  JsonConvert.m
//  Base
//
//  Created by ns on 2020/6/4.
//

#import "JsonConvert.h"

@implementation JsonConvert
+(NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString
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

+(NSString *)toJSONString:(NSDictionary*)dic {
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
