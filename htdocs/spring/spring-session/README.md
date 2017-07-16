## 1. Introduction
Spring Session提供了一个用于管理用户会话信息的API和实现。 
它还提供透明的集成：
 HttpSession - 允许在应用程序容器（即Tomcat）中以中性方式替换HttpSession。 其他功能包括：
 -  Clustered Sessions :Spring Session使得支持集群会话非常简单，而不会与应用程序容器特定的解决方案相关联。
 -  Multiple Browser Sessions Spring Session支持在单个浏览器实例中管理多个用户的会话（即与Google类似的多个经过身份验证的帐户）。
 -  RESTful API - Spring Session允许在headers中提供会话ID以使用RESTful API
 -  WebSocket - 提供了在接收WebSocket消息时保持HttpSession活动的能力
 ## 2. What’s New in 1.3
 ## 3. Samples and Guides (Start Here)
 ### 3.1 Table 1. Sample Applications using Spring Boot
 ### 3.2 Table 2. Sample Applications using Spring Java based configuration

	| Source 		|	Description 	|		Guide	|
	|	------		|	----------		|	-------	|
	|	HttpSession with Redis		|		|			|
	
	
-	(HttpSession with Redis)[https://github.com/spring-projects/spring-session/tree/2.0.0.M2/samples/javaconfig/redis]