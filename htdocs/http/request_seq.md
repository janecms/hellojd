# HTTP Request 请求过程

## 1.DNS解析
当你在浏览器中输入一个地址时，例如www.baidu.com。首先根据域名查出IP地址，才能进行网络通信。
请求的目标地址实际上就是IP地址，
互联网上每一台计算机的唯一标识是它的IP地址。DNS服务器就是专门负责域名到IP地址解析。

### 1.1 DNS服务器根据域名的层级，进行分级查询。
一共有四级分别为：
- 根域名服务器（可以找到.com,.org,.cn)
- 顶级域名服务器(可以找到baidu.com)
- 次级域名服务器(可以找到www.baidu.com)
- 本地域名服务器
### 1.2 DNS的记录类型
域名与IP之间的对应关系，称为"记录"（record）
常见的DNS记录类型如下。
- (1) A：地址记录（Address），返回域名指向的IP地址。
- (2) NS：域名服务器记录（Name Server），返回保存下一级域名信息的服务器地址。该记录只能设置为域名，不能设置为IP地址。
- (3) MX：邮件记录（Mail eXchange），返回接收电子邮件的服务器地址。
- (4) CNAME：规范名称记录（Canonical Name），返回另一个域名，即当前查询的域名是另一个域名的跳转.
- (5) PTR：逆向查询记录（Pointer Record），只用于从IP地址查询域名，

可以使用dig命令。
### 1.3 DNS协议
DNS协议属于应用层协议，所以可支持UDP和TCP传输。 DNS报文由报头和正文段构成，DNS有四类正文段：查询段、应答段、授权段和附加段。
其具体构成见7-41所示
![DNS协议](http://img.voidcn.com/vcimg/000/004/845/322_1bd_fbc.gif)
### 1.4 DNS缓存
TTL参数,设置DNS默认的缓存时间.

[DNS解析原理](http://mufool.com/2016/10/17/dns-parse/)

[DNS协议详解](http://www.voidcn.com/blog/Primeprime/article/p-5772321.html)
## 2.构造HTTP请求报文
### 2.1请求报文
一个HTTP请求报文由请求行（request line）、请求头部（header）、空行和请求数据4个部分组成，下图给出了请求报文的一般格式。
![](http://pic002.cnblogs.com/images/2012/426620/2012072810301161.png)
请求行由请求方法字段、URL字段和HTTP协议版本字段3个字段组成，它们用空格分隔。例如，

```GET /index.html HTTP/1.1。```
HTTP协议的请求方法有GET、POST、HEAD、PUT、DELETE、OPTIONS、TRACE、CONNECT。
根据实际情况，和浏览器和操作系统配置，写入请求头部(head)信息，提前告诉服务器参数信息。如
- 浏览器版本(User-Agent)
- 是否支持压缩(Accept-Encoding)
- 是否是长连接(Connection)
- 可接受语言(Accept-Language)
- Referer(访问入口)
- Accept：客户端可识别的内容类型列表。
- Host：请求的主机名，允许多个域名同处一个IP地址，即虚拟主机。

[一次完整的HTTP请求所经历的7个步骤](http://www.cnblogs.com/jiu0821/p/5641600.html)
到现在,还没有IP的事，还没有发生网络活动。
## 2.TCP连接
前提：已经知道目的IP,目的端口(默认为80);HTTP请求报文已经构造好。
首先，浏览器会启动一个新的随机端口号(1024< 端口 < 65535),指定目标IP地址，目标端口(80),创建TCP,Socket连接。当然，可以设置TCP参数(TCP滑动窗口大小，超时,缓冲区大小)，影响TCP报文传输报文中的行为。
首先，要完成TCP的3次握手，握手过程中，要发送少量请求报文。
### 2.1 TCP报文流动路径

- 构造TCP报文，指定目标端口,源端口。将HTTP报文嵌入TCP数据包中。
- 构造IP报文，指定目标IP地址，源IP地址，将TCP报文嵌入I数据包中。
- 使用ARP协议，工作在网络层，获取得到目标主机的MAC地址.
- 构造链路层报文，指定目的主机MAC，源主机MAC

## 3.发送HTTP请求
## 4.服务器处理请求并返回HTTP报文
## 5.浏览器解析渲染页面
## 6.连接结束