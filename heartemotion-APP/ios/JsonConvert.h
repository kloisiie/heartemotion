//
//  JsonConvert.h
//  Base
//
//  Created by ns on 2020/6/4.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface JsonConvert : NSObject
+(NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString;
+(NSString *)toJSONString:(NSDictionary*)dic;
@end

NS_ASSUME_NONNULL_END
