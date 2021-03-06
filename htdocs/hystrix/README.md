# 介绍
在分布式环境中，不可避免地有许多服务依赖将失败。 Hystrix是一个库，可以通过添加延迟容错和容错逻辑来帮助您控制这些分布式服务之间的交互。
Hystrix通过隔离服务之间的接入点，阻止它们之间的级联故障，并提供备用选项，从而提高系统的整体弹性，从而实现这一点。在分布式环境中，不可避免地有许多服务依赖将失败。 
Hystrix是一个库，可以通过添加延迟容错和容错逻辑来帮助您控制这些分布式服务之间的交互。 Hystrix通过隔离服务之间的接入点，阻止它们之间的级联故障，并提供备用选项，从而提高系统的整体弹性，从而实现这一点。

# 可以带来好处

- 第三方客户端带来的延迟和失败保护
- 在复杂的分布式系统中停止级联故障。
- 故障快速恢复。
- 在可能的情况下，后备并优雅地降级。
-  启用近实时监控，警报和操作控制。
# 解决什么问题
复杂分布式架构中的应用有几十个依赖关系，每个依赖关系在某些时候将不可避免地失败。 如果主机应用程序没有与这些外部故障隔离，则可能会与主机产生影响。
例如，对于每个服务具有99.99％正常运行时间的30个服务的应用程序，结果：
```
99.9930 = 99.7% uptime
0.3% of 1 billion requests = 3,000,000 failures
2+ hours downtime/month even if all dependencies have excellent uptime. 
```

现实情况普遍较差。
如果您不设计整个系统的韧性。即使所有依赖关系表现良好，即使0.01％的停机时间,对数十个服务中的每一个服务的总体影响等同于每月停机的潜在时间。
一切正常时，请求过程像这样
![soa-1](https://github.com/Netflix/Hystrix/wiki/images/soa-1-640.png)

当某个依赖服务发生延迟，会阻塞整体用户请求
![soa-2](https://github.com/Netflix/Hystrix/wiki/images/soa-2-640.png)

对于高容量流量，单个后端依赖关系变为潜在可能导致所有资源在所有服务器上几秒钟内饱和。
应用程序中，每个网络请求的的每个点都是潜在故障的来源。 
更糟糕的是，这些应用程序也可能导致在服务之间增加延迟，进而产生级联效果。
