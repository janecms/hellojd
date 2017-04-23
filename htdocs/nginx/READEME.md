## upstream 参数说明
```
upstream backend {
    server backend1.example.com       weight=5;
    server backend2.example.com:8080;
    server unix:/tmp/backend3;

    server backup1.example.com:8080   backup;
    server backup2.example.com:8080   backup;
}
```
- 设备的状态有: 
	- 1.down 表示单前的server暂时不参与负载 
	- 2.weight 权重,默认为1。 weight越大，负载的权重就越大。 
	- 5.backup 备用服务器, 其它所有的非backup机器down或者忙的时候，请求backup机器。所以这台机器压力会最轻。
	- max_conns=number :限制与代理服务器的最大并发活动连接数.默认值为零，意味着没有限制	
	-resolve:监视对应于服务器域名的IP地址的更改，并自动修改上游配置，而无需重新启动.

###失败重试

- max_fails 允许请求失败的次数默认为1。当超过最大次数时，返回proxy_next_upstream 模块定义的错误 
- fail_timeout max_fails次失败后，暂停的时间。 	

### 负载均衡	
```
Syntax:	hash key [consistent];
Default:	 —
Context:	upstream
This directive appeared in version 1.7.2.

```
- 指定服务器组的负载平衡方法，其中客户端 - 服务器映射基于散列键值。 
密钥可以包含文本，变量及其组合。
```
Syntax:	ip_hash;
Default:	 —
Context:	upstream

```
- 指定一个组应该使用根据客户端IP地址在服务器之间分配请求的负载平衡方法。

### 健康检查
```
Syntax:	health_check [parameters];
Default:	 —
Context:	location
```
- 商业版支持;
- 社区版可以集成 nginx_upstream_check_module
- TCP心跳检查
- HTTP心跳检查


### 长连接
```
Syntax:	keepalive connections;
Default:	 —
Context:	upstream
This directive appeared in version 1.1.4.
```
connections参数设置保存在每个工作进程的缓存中的上游服务器的最大空闲保持连接数。 超过时，关闭最近使用的最近连接。
- 建议只对小报文开启长连接