# JpushDemo
####一个服务器端使用极光推送的很简单的入门级demo，推送如此简单，可以很方便的植入到自己的web服务器中去实现推送功能。

####为方便管理，项目使用maven管理

#####分为一下几步：
- 申请你的appKey和masterSecret，去[极光推送官网](https://www.jpush.cn)注册，然后创建应用后就可以生成
- 使用极光推送总共需要两个依赖，一个是[Jpush的jar文件](https://github.com/jpush/jpush-api-java-client/releases/download/v3.2.5/jpush-client-3.2.5.zip)依赖，一个是Google的gson包依赖,Google使用maven来管理gson，添加如下maven依赖
> 		<dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.2.4</version>
        </dependency>
        
        
 - 在你的服务器端代码中模仿使用pushExample中提供的几个方法就可以，方法的名字很直观，可以直接使用