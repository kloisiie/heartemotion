# v1.0.2
* a3177d37(fix: 修改代码生成器不支持枚举属性的问题(解决方案:将$改为.))
* fff4afa5(fix: 修改swagger文档的包扫描路径错误)
* 38a08eb0(fix: 增加初始化项目的sql文件)
* 88c30806(fix: 修改支付模块，统一支付方式)
* 2fa8ff56(fix: 自动清理日志权限更新)
* 32afeaf3(feat: 日志清理权限修改)
* dc1075da(feat: 移除自动清理的配置的时间)
* 92d06bed(feat: log功能完善)
* a1dab2b0(feat: 再封装了下支付方式)
* 78844ca8(feat: 补上回调控制器,再封装了下控制器的方法)
* 6d0937ea(fix: 修改依赖)
* 8dc17cf0(feat: log数据清理功能开发)
* 9c25850d(feat: 完善支付模块)
* 8583d8ac(feat: 初次修改支付)
* 10224c3c(feat: 新增详情Result和功能列表Result分开，并可配置)
* ede9301f(feat：通过在Docker Hub中构建基础镜像来减少Docker Image打包的耗时)
* b8d5fb41(fix：解决CRUD生成Controller的url如果是驼峰命名使用的是下划线分割的问题，现在改为了中划线分割)
* 6106ce0b(fix: 修改oss模块的依赖;修改admin,api,swagger配置包名的问题)
* a98ef046(fix：增加Java8为必须的说明)
* 46f5dbbc(fix：解决无法生成枚举的问题)
* 586aaa20(fix: 重构配置,并增加支付宝支付的service)

# v1.0.1
该版本修复了一些bug，对代码生成工具进行优化  

* ***common-generate*** 解决field没有注解的情况下会出现识别不了的问题 *[fix]*  

* ***common-generate*** 将CRUD代码生成的POJO字段有public改为private *[fix]*  

* ***common-generate*** 将CRUD Service的分页查询方法更改为拓展性更强的QueryFactory的方式实现 *[feat]*  

![avatar](https://standard-kjrhc0jtvgfxzqnq.oss-cn-shenzhen.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20200805175955.png "QueryFactory实现")



---------------


# v1.0.0
基础架构版本