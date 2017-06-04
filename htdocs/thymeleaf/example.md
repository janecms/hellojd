# Tutorial: Using Thymeleaf(3-)
## 3 Using Texts
### 3.1 A multi-language welcome
我们的第一个任务是为我们的杂货店创建一个主页。
这个页面的第一个版本会非常简单：只需一个标题和一个欢迎信息。这是我们的/WEB-INF/templates/home.html文件：
```
<!DOCTYPE html>

<html xmlns:th="http://www.thymeleaf.org">

  <head>
    <title>Good Thymes Virtual Grocery</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" media="all" 
          href="../../css/gtvg.css" th:href="@{/css/gtvg.css}" />
  </head>

  <body>
  
    <p th:text="#{home.welcome}">Welcome to our grocery store!</p>
  
  </body>

</html>
```
您会注意到的第一件事是，该文件是HTML5，可以由任何浏览器正确显示，因为它不包括任何非HTML标签（浏览器忽略他们不明白的所有属性，如：文本）。

但是您也可能会注意到，这个模板并不是一个真正有效的HTML5文档，因为HTML5规范不允许在th：*形式中使用这些非标准属性。事实上，我们甚至在我们的<html>标签中添加了一个xmlns：th属性，这绝对是非HTML5-ish：
```
<html xmlns:th="http://www.thymeleaf.org">
```
.在模板处理中根本没有任何影响，但作为咒语，阻止我们的IDE抱怨所有这些：*属性缺少命名空间定义。
那么如果我们想让这个模板HTML5有效呢？ Easy：切换到Thymeleaf的数据属性语法，使用属性名称和连字符（ - ）分隔符的数据前缀而不是分号（:)：
```
<!DOCTYPE html>

<html>

  <head>
    <title>Good Thymes Virtual Grocery</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" media="all" 
          href="../../css/gtvg.css" data-th-href="@{/css/gtvg.css}" />
  </head>

  <body>
  
    <p data-th-text="#{home.welcome}">Welcome to our grocery store!</p>
  
  </body>

</html>
```
HTML5规范允许自定义数据前缀属性，因此，使用上面的代码，我们的模板将是一个有效的HTML5文档。
** 两个符号是完全等同的和可互换的，但为了简单和紧凑的代码示例，本教程将使用命名空间符号（th：*）。
此外，th：*符号在每个Thymeleaf模板模式（XML，TEXT ...）中更为通用且允许，而数据符号仅允许在HTML模式下使用。**

** Using th:text and externalizing text
-----------------------------
外部化文本是从模板文件中提取模板代码的片段，以便它们可以保存在单独的文件（通常为.properties文件）中，并且可以轻松地替换为用其他语言编写的等效文本（称为国际化或简单的i18n） 。
文本的外部化片段通常称为“messages”。
消息总是有一个标识它们的键，而Thymeleaf允许您指定一个文本应该对应于具有＃{...}语法的特定消息：
```
<p th:text="#{home.welcome}">Welcome to our grocery store!</p>
```
我们在这里可以看到的是实际上Thymeleaf标准方言的两个不同特征：
 - th：text属性，它评估其值表达式并将结果设置为主机标签的主体，有效地替换了代码中我们看到的“欢迎使用我们的杂货店！”文本。
 - ＃标准表达式语法中指定的＃{home.welcome}表达式指示由th：text属性使用的文本应该是home.welcome KEY对应于我们正在处理模板的区域设置的消息。
现在，这个外部化文本在哪里？
Thymeleaf中外部化文本的位置是完全可配置的，它将取决于正在使用的具体的org.thymeleaf.messageresolver.IMessageResolver实现。通常，将使用基于.properties文件的实现，但是如果我们想要（例如）从数据库获取消息，我们可以创建自己的实现。
但是，我们在初始化期间尚未为模板引擎指定消息解析器，这意味着我们的应用程序正在使用由org.thymeleaf.messageresolver.StandardMessageResolver实现的标准消息解析器。
标准消息解析器期望在/WEB-INF/templates/home.html中找到与文件夹相同的文件夹中的属性文件的消息，例如：
/WEB-INF/templates/home_en.properties为英文文本。
西班牙语文本的/WEB-INF/templates/home_es.properties。
葡萄牙语（巴西）语言文本的/WEB-INF/templates/home_pt_BR.properties。
/WEB-INF/templates/home.properties为默认文本（如果语言环境不匹配）。
我们来看看我们的home_es.properties文件：
```
home.welcome =¡Bienvenido a nuestra tienda de comestibles！
```
这就是我们所需要的，让我们制作我们的模板。然后让我们创建我们的家庭控制器。

** Contexts
---------------------
为了处理我们的模板，我们将创建一个实现我们以前看到的IGTVGController接口的HomeController类：
```
public class HomeController implements IGTVGController {

    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {
        
        WebContext ctx = 
                new WebContext(request, response, servletContext, request.getLocale());
        
        templateEngine.process("home", ctx, response.getWriter());
        
    }

}
```

我们首先看到的是创建一个上下文。 Thymeleaf上下文是实现org.thymeleaf.context.IContext接口的对象。上下文应包含在变量映射中执行模板引擎所需的所有数据，并且还引用必须用于外部化消息的区域设置。

```
public interface IContext {

    public Locale getLocale();
    public boolean containsVariable(final String name);
    public Set<String> getVariableNames();
    public Object getVariable(final String name);
    
}
```
这个接口有一个专门的扩展，org.thymeleaf.context.IWebContext，用于基于ServletAPI的Web应用程序（如SpringMVC）中。

```
public interface IWebContext extends IContext {
    
    public HttpServletRequest getRequest();
    public HttpServletResponse getResponse();
    public HttpSession getSession();
    public ServletContext getServletContext();
    
}
```
Thymeleaf核心库提供了以下每个接口的实现：
   - org.thymeleaf.context.Context implements IContext
   - org.thymeleaf.context.WebContext implements IWebContext
而在控制器代码中可以看到，WebContext是我们使用的。实际上我们必须，因为使用一个ServletContextTemplateResolver要求我们使用实现IWebContext的上下文。

```
WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
```
只需要这四个构造函数参数中的三个，因为如果没有指定，那么将使用系统的默认语言环境（尽管不应该在实际应用程序中发生）。
有一些专门的表达式，我们将能够用来从我们的模板中的WebContext获取请求参数和请求，会话和应用程序属性。例如：

  -  ${x} will return a variable x stored into the Thymeleaf context or as a request attribute.
  -  ${param.x} will return a request parameter called x (which might be multivalued).
  -  ${session.x} will return a session attribute called x.
  -  ${application.x} will return a servlet context attribute called x.
** Executing the template engine
--------------
随着我们的上下文对象准备就绪，现在我们可以告诉模板引擎使用上下文来处理模板（通过它的名字），并传递一个响应写入器，以便可以将响应写入它：
```
templateEngine.process（“home”，ctx，response.getWriter（））;
```
让我们看看使用西班牙语区域的结果：
```
<!DOCTYPE html>

<html>

  <head>
    <title>Good Thymes Virtual Grocery</title>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type"/>
    <link rel="stylesheet" type="text/css" media="all" href="/gtvg/css/gtvg.css" />
  </head>

  <body>
  
    <p>¡Bienvenido a nuestra tienda de comestibles!</p>

  </body>

</html>
```  
### 3.2 More on texts and variables
  ** Unescaped Text
  我们的主页的最简单的版本似乎已经准备好了，但是我们还没有想到什么呢？如果我们有这样的消息呢？
  ```
  home.welcome=Welcome to our <b>fantastic</b> grocery store!
  ```
  如果我们像以前一样执行此模板，我们将获得：
  ```
  <p>Welcome to our &lt;b&gt;fantastic&lt;/b&gt; grocery store!</p>
  ```
  这不是我们预期的，因为我们的<b>标签已被转义，因此它将显示在浏览器中。
  这是th：text属性的默认行为。如果我们希望Thymeleaf尊重我们的HTML标签，而不是逃避他们，我们将不得不使用一个不同的属性：th：utext（对于“unes​​caped text”）：
```
<p th:utext="#{home.welcome}">Welcome to our grocery store!</p>
```
<p th：utext =“＃{home.welcome}”>欢迎来到我们的杂货店！</ p>

这将输出我们的消息就像我们想要的：
```
<p>Welcome to our <b>fantastic</b> grocery store!</p>
```
** Using and displaying variables
现在我们再添加一些更多的内容到我们的主页。例如，我们可能希望在我们的欢迎信息下方显示日期，如下所示：
```
Welcome to our fantastic grocery store!

Today is: 12 july 2010
```
今天是：2010年7月12日

首先，我们将必须修改我们的控制器，以便将该日期添加为上下文变量：
public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {
        
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    Calendar cal = Calendar.getInstance();
        
    WebContext ctx = 
            new WebContext(request, response, servletContext, request.getLocale());
    ctx.setVariable("today", dateFormat.format(cal.getTime()));
        
    templateEngine.process("home", ctx, response.getWriter());
        
}

我们在我们的上下文中添加了一个今天调用的String变量，现在我们可以在我们的模板中显示它：
```
<body>

  <p th:utext="#{home.welcome}">Welcome to our grocery store!</p>

  <p>Today is: <span th:text="${today}">13 February 2011</span></p>
  
</body>
```
正如你所看到的，我们仍然使用th：text属性作为工作（这是正确的，因为我们要替换标签的正文），但是这个时候语法有点不同，而不是＃{.. 。}表达式值，
我们使用$ {...}一个。这是一个变量表达式，它包含一个名为OGNL（Object-Graph Navigation Language）的表达式，它将在之前讨论的上下文变量映射上执行。

$ {today}表达式只是意味着“获取当前调用的变量”，但是这些表达式可能会更复杂.