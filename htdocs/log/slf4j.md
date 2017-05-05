# Simple Logging Facade for Java (SLF4J) 

## 介绍
简单的Logging for Java（SLF4J）作为简单的外观或抽象，可用于各种日志记录框架（例如java.util.logging，logback，log4j），允许最终用户在部署时插入所需的日志记录框架。

在开始使用SLF4J之前，强烈建议您阅读两页SLF4J用户手册。

请注意，SLF4J启用您的库意味着只添加一个强制依赖关系，即slf4j-api.jar。 如果在类路径上没有找到绑定，则SLF4J将默认为无操作实现。

如果您希望将Java源文件迁移到SLF4J，请考虑我们的迁移工具，可以在短短几分钟内将项目迁移到使用SLF4J API。
如果您依赖的外部维护组件使用除SLF4J之外的日志记录API（如commons logging，log4j或java.util.logging），请查看SLF4J对旧版API的二进制支持。
## SLF4J user manual
SINCE 1.6.0如果在类路径上没有找到绑定，则SLF4J将默认为无操作实现。

SINCE 1.7.0 Logger界面中的打印方式现在提供接受varargs而不是Object []的变体。这种变化意味着SLF4J需要JDK 1.5或更高版本。
在Java引擎下，Java编译器将方法中的varargs部分转换为Object []。因此，编译器生成的Logger接口与1.6.x对应的1.7.x是不可区分的。因此，SLF4J版本1.7.x完全是100％的无符号或兼容SLF4J版本1.6.x。

SINCE1.7.5开始，记录仪检索时间显着提高。鉴于改进的程度，用户被高度鼓励迁移到SLF4J 1.7.5或更高版本。

SINCE 1.7.9通过将slf4j.detectLoggerNameMismatch系统属性设置为true，SLF4J可以自动发现错误命名的记录器。
### Hello World
像编程传统中的习惯一样，这里举例说明使用SLF4J输出“Hello world”的最简单方法。 首先得到一个名为“HelloWorld”的记录器。 这个记录器又用于记录消息“Hello World”。
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```
编译并运行HelloWorld将导致以下输出被打印在控制台上。
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```
打印此警告，因为您的类路径上没有找到slf4j绑定。
一旦你添加1个binding到你的类路径，警告就会消失。假设您添加slf4j-simple-1.8.0-alpha2.jar，以便您的类路径包含：
- slf4j-api-1.8.0-alpha2.jar
- slf4j-simple-1.8.0-alpha2.jar

最后的结果:
```
0 [main] INFO HelloWorld - Hello World
```

### 典型使用模式
下面的示例代码说明了SLF4J的典型使用模式。 请注意在第15行使用{} - 占位符。查看问题“What is the fastest way of logging?” 
在常见问题解答中了解更多详情。
```
 1: import org.slf4j.Logger;
 2: import org.slf4j.LoggerFactory;
 3: 
 4: public class Wombat {
 5:  
 6:   final Logger logger = LoggerFactory.getLogger(Wombat.class);
 7:   Integer t;
 8:   Integer oldT;
 9:
10:   public void setTemperature(Integer temperature) {
11:    
12:     oldT = t;        
13:     t = temperature;
14:
15:     logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);
16:
17:     if(temperature.intValue() > 50) {
18:       logger.info("Temperature has risen above 50 degrees.");
19:     }
20:   }
21: } 
```

### Binding with a logging framework at deployment time

如前所述，SLF4J支持各种日志记录框架。 SLF4J发行版附带了几个称为“SLF4J绑定”的jar文件，每个绑定对应于支持的框架。

-----
- slf4j-log4j12-1.8.0-alpha2.jar

	绑定log4j版本1.2，广泛使用的日志框架。 您还需要在您的类路径上放置log4j.jar。
-----
- slf4j-jdk14-1.8.0-alpha2.jar
	
	绑定java.util.logging，也称为JDK 1.4日志记录

-----
- slf4j-nop-1.8.0-alpha2.jar

	绑定NOP，静默地丢弃所有日志记录。
-----
- slf4j-simple-1.8.0-alpha2.jar

	绑定简单实现，将所有事件输出到System.err。 只打印级别INFO和更高的消息。 这种绑定在小应用程序的上下文中可能是有用的。
----
- slf4j-jcl-1.8.0-alpha2.jar

	绑定Jakarta Commons Logging。 此绑定将所有SLF4J日志记录委托给JCL。
----
- logback-classic-1.0.13.jar (requires logback-core-1.0.13.jar)


要切换日志记录框架，只需在类路径上替换slf4j绑定。 
例如，要从java.util.logging切换到log4j，只需用slf4j-log4j12-1.8.0-alpha2.jar替换slf4j-jdk14-1.8.0-alpha2.jar。
SLF4J不依赖任何特殊的装载机械。 实际上，每个SLF4J绑定在编译时都是硬连线的，只能使用一个唯一的特定日志框架。 例如，slf4j-log4j12-1.8.0-alpha2.jar绑定在编译时绑定到使用log4j。 
在你的代码中，除了slf4j-api-1.8.0-alpha2.jar之外，你只需选择一个绑定放在适当的类路径位置上。 不要在类路径上放置多个绑定。 这是一个总体思路的图解说明。

![concrete-bindings](https://www.slf4j.org/images/concrete-bindings.png)
SLF4J接口及其各种适配器非常简单。 熟悉Java语言的大多数开发人员应该能够在不到一个小时内阅读并完全了解代码。 不需要类加载器的知识，因为SLF4J不使用，也不直接访问任何类加载器。 因此，SLF4J没有受到Jakarta Commons Logging（JCL）观察到的类加载器问题或内存泄漏。
鉴于SLF4J接口及其部署模式的简单性，新的日志记录框架的开发人员应该很容易地编写SLF4J绑定。
### Libraries
从SLF4J版本1.6.0开始，如果在类路径上没有找到绑定，则slf4j-api将默认为无操作的实现，丢弃所有日志请求。因此，由于缺少org.slf4j.impl.StaticLoggerBinder类而不是抛出NoClassDefFoundError，因此SLF4J版本1.6.0及更高版本将会发出关于不存在绑定的单个警告消息，并继续丢弃所有日志请求，而无需进一步抗议。
例如，根据SLF4J的记录，让Wombat成为一些与生物相关的框架.为了避免在最终用户上强加一个日志框架，Wombat发行版包括slf4j-api.jar，但没有bind。即使在类路径上没有任何SLF4J绑定，Wombat的发行版仍然可以开箱即用，并且不要求最终用户从SLF4J的网站下载绑定。只有当最终用户决定启用日志记录时，她才需要安装与她选择的日志框架相对应的SLF4J绑定。
不应声明对任何SLF4J绑定的依赖，而仅依赖于slf4j-api。当一个库声明对特定绑定的传递依赖性时，该绑定被强加于最终用户否定SLF4J的目的。请注意，声明对绑定的非传递依赖关系，例如用于测试，不会影响最终用户。


您只需确保您的绑定版本与slf4j-api.jar的版本匹配。您不必担心项目中给定依赖关系使用的slf4j-api.jar的版本。你可以随时使用任何版本的slf4j-api.jar，只要这个版本的slf4j-api.jar和它的绑定相匹配，你应该可以。
在初始化时，如果SLF4J怀疑可能存在slf4j-api与绑定版本不匹配问题，则会发出关于疑似错配的警告。

#### 通过SLF4J整合日志记录

通常情况下，给定的项目将取决于依赖于除SLF4J之外的日志API的各种组件。 通常根据JCL，java.util.logging，log4j和SLF4J的组合来查找项目。 然后，通过单个通道合并日志记录是合乎需要的。 SLF4J通过为JCL，java.util.logging和log4j提供桥接模块来满足此常见用例。 有关详细信息，请参阅桥接旧版API的页面。

#### Mapped Diagnostic Context (MDC) support

“Mapped Diagnostic Context”本质上是由日志记录框架维护的映射，其中应用程序代码提供键值对，
然后可以将日志框架插入到日志消息中。 MDC数据还可以非常有助于过滤消息或触发某些操作。
SLF4J支持MDC或映射诊断上下文。 如果底层日志框架提供MDC功能，则SLF4J将委托到底层框架的MDC。 请注意，此时只有log4j和logback提供MDC功能。 如果底层框架不提供MDC，例如java.util.logging，则SLF4J仍将存储MDC数据，但其中的信息将需要通过自定义用户代码检索。
因此，作为SLF4J用户，您可以在存在log4j或logback的情况下利用MDC信息，但不会强制将这些日志记录框架作为依赖关系。

## Bridging legacy APIs
### Gradual migration to SLF4J from Jakarta Commons Logging (JCL)
#### jcl-over-slf4j.jar
为了简化从JCL迁移到SLF4J，SLF4J发行版包括jar文件jcl-over-slf4j.jar。此jar文件旨在作为JCL版本1.1.1的替代品。它实现了JCL的公共API，但在下面使用了SLF4J，因此命名为“JCL over SLF4J”。
我们的JCL通过SLF4J实现将允许您逐渐迁移到SLF4J，特别是如果您的软件依赖的某些库在可预见的将来继续使用JCL。您可以立即享受SLF4J的可靠性的优点，同时保持向后兼容性。只需用jcl-over-slf4j.jar替换commons-logging.jar。随后，基础日志框架的选择将由SLF4J而不是JCL完成，但不会使类加载程序头痛困扰JCL。底层日志记录框架可以是SLF4J支持的任何框架。通常，用jcl-over-slf4j.jar替换commons-logging.jar将立即永久地解决与commons logging有关的类加载器问题。

#### slf4j-jcl.jar

我们的一些用户在切换到SLF4J API后，意识到在某些情况下，使用JCL是强制性的，它们使用SLF4J可能是一个问题。 对于这个不常见但重要的情况，SLF4J提供了一个JCL绑定，位于文件slf4j-jcl.jar中。 JCL绑定将通过SLF4J API进行的所有日志调用委托给JCL。 因此，如果由于某种原因现有应用程序必须使用JCL，那么该应用程序的部分仍然可以以更大的应用程序环境透明的方式对SLF4J API进行编码。 
您可以继续使用JCL的其他应用程序，您对SLF4J API的选择将不可见。

#### jcl-over-slf4j.jar should not be confused with slf4j-jcl.jar
JCL-over-SLF4J（即jcl-over-slf4j.jar）在需要以向后兼容性原因支持JCL的情况下派上用场。它可以用来解决与JCL相关的问题，而不必采用SLF4J API，这个决定可以推迟到以后的时间。
另一方面，在您为组件采用SLF4J API之后，slf4j-jcl.jar非常有用，该组件需要嵌入到JCL是一个正式要求的更大的应用程序环境中。您的软件组件仍然可以使用SLF4J API，而不会中断较大的应用程序。实际上，slf4j-jcl.jar会将所有的日志记录决定委托给JCL，以便您的组件对SLF4J API的依赖对于较大的整体来说将是透明的。
请注意，jcl-over-slf4j.jar和slf4j-jcl.jar不能同时部署。前一个jar文件将导致JCL将日志系统的选择委托给SLF4J，后一个jar文件将导致SLF4J将日志系统的选择委托给JCL，导致无限循环。