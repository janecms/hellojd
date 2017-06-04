# Tutorial: Using Thymeleaf(1-2)
##  1 Introducing Thymeleaf
### 1.1 What is Thymeleaf?
Thymeleaf是面向Web和独立环境的现代服务器端Java模板引擎，能够处理HTML，XML，JavaScript，CSS甚至纯本。
Thymeleaf的主要目标是提供一个优雅和高度可维护的创建模板的方式。 为了实现这一点，它建立在自然模板的概念上，将其逻辑注入到模板文件中，不会影响模板被用作设计原型。 这改善了设计的沟通，弥合了设计和开发团队之间的差距。
Thymeleaf也从一开始就设计了Web标准 - 特别是HTML5 - 允许您创建完全验证的模板，如果这是您需要的。
### 1.2什么样的模板可以让Thymeleaf进行？
开箱即用的Thymeleaf可让您处理六种模板，每种模板称为模板模式：
    HTML
    XML
    TEXT
    JAVASCRIPT
    CSS
    RAW
有两种标记模板模式（HTML和XML），三种文本模板模式（TEXT，JAVASCRIPT和CSS）和一个无操作模板模式（RAW）。
HTML模板模式将允许任何类型的HTML输入，包括HTML5，HTML 4和XHTML。将不执行验证或形式良好检查，并且模板代码/结构将在产出中尽可能的最大程度地得到尊重。
XML模板模式将允许XML输入。在这种情况下，代码预期形式良好 - 没有未关闭的标签，不引用任何属性等，如果找到良好的违规行为，解析器将抛出异常。请注意，不会执行验证（针对DTD或XML架构）。
TEXT模板模式将允许对非标记性质的模板使用特殊语法。此类模板的示例可能是文本电子邮件或模板文档。请注意，HTML或XML模板也可以作为TEXT处理，在这种情况下，它们将不会被解析为标记，并且每个标签DOCTYPE，注释等将被视为纯文本。
JAVASCRIPT模板模式将允许在Thymeleaf应用程序中处理JavaScript文件。这意味着能够使用JavaScript文件中的模型数据与HTML文件中可以完成的方式相同，但可以使用特定于JavaScript的集成，例如专门的转义或自然脚本。 JAVASCRIPT模板模式被认为是文本模式，因此使用与TEXT模板模式相同的特殊语法。
CSS模板模式将允许处理涉及Thymeleaf应用程序的CSS文件。与JAVASCRIPT模式类似，CSS模板模式也是文本模式，并使用TEXT模板模式下的特殊处理语法。
RAW模板模式根本不会处理模板。它用于将未经修改的资源（文件，URL响应等）插入正在处理的模板中。例如，HTML格式的外部不受控制的资源可以包含在应用程序模板中，安全地知道这些资源可能包含的任何Thymeleaf代码将不会被执行。
### 1.3方言：标准方言
Thymeleaf是一个非常可扩展的模板引擎（实际上它可以称为模板引擎框架），允许您定义和自定义您的模板将被处理到一个很好的细节。
将一些逻辑应用于标记工件（标签，某些文本，注释或只有占位符，如果模板不是标记）的一个对象被称为处理器，并且这些处理器的集合以及一些额外的工件是什么 一个方言通常包括。 开箱即用，Thymeleaf的核心库提供了一种称为标准方言的方言，这对大多数用户来说应该是足够的。
**请注意，方言实际上可能没有处理器，并且完全由其他类型的工件组成，但处理器绝对是最常见的用例。**
本教程涵盖标准方言。 您将在以下页面中了解的每个属性和语法功能都由此方言定义，即使没有明确提及。
当然，如果用户希望在利用库的高级功能的同时定义自己的处理逻辑，用户可以创建自己的方言（甚至扩展标准的方言）。 也可以将Thymeleaf配置为一次使用几种方言。
** 官方的 thymeleaf-spring3 和 thymeleaf-spring4的整合包都定义了一种称为“SpringStandard Dialect”的方言，与“标准方言”大致相同，但是对于Spring框架中的某些功能（例如，通过使用Spring Expression Language或SpringEL而不是OGNL）。 所以如果你是一个Spring MVC用户，你不会浪费你的时间，因为你在这里学到的所有东西都将在你的Spring应用程序中使用。**
标准方言的大多数处理器是属性处理器。 这样，即使在处理之前，浏览器也可以正确地显示HTML模板文件，因为它们将简单地忽略其他属性。 例如，虽然使用标记库的JSP可能包含不能像浏览器那样直接显示的代码片断：
```<form:inputText name="userName" value="${user.name}" />```
Thymeleaf标准方言将允许我们实现与以下功能相同的功能：
```
<input type="text" name="userName" value="James Carrot" th:value="${user.name}" />`
```
浏览器不仅可以正确显示这些信息，而且还可以（可选地）在浏览器中静态打开原型时显示的值（可选地）指定一个值属性（在这种情况下为“James Carrot”）， 将在模板处理期间由$ {user.name}的评估得到的值代替。
这有助于您的设计师和开发人员处理相同的模板文件，并减少将静态原型转换为工作模板文件所需的工作量。 这样做的功能是称为自然模板的功能。
## 2 The Good Thymes Virtual Grocery
本指南和本指南的未来章节中显示的示例的源代码可以在[Good Thymes Virtual Grocery GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)存储库中找到。
### 2.1 A website for a grocery
为了更好地解释使用Thymeleaf处理模板所涉及的概念，本教程将使用您可以从项目网站下载的演示应用程序。
这个应用程序是一个想象的虚拟杂货店的网站，并将为我们提供许多场景来展示Thymeleaf的许多功能。
为了开始，我们需要一套简单的模型实体，用于我们的应用：通过创建订单销售给客户的产品。 我们还将管理关于这些产品的意见：
![](http://www.thymeleaf.org/doc/tutorials/3.0/images/usingthymeleaf/gtvg-model.png)
我们的应用程序也将有一个非常简单的服务层，由Service对象组成，包含以下方法：
````
public class ProductService {
    ...

    public List<Product> findAll() {
        return ProductRepository.getInstance().findAll();
    }

    public Product findById(Integer id) {
        return ProductRepository.getInstance().findById(id);
    }
}`
```

在Web层，我们的应用程序将有一个过滤器，将根据请求URL将执行委托给启用Thymeleaf的命令：
```
private boolean process(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
    
    try {

        // This prevents triggering engine executions for resource URLs
        if (request.getRequestURI().startsWith("/css") ||
                request.getRequestURI().startsWith("/images") ||
                request.getRequestURI().startsWith("/favicon")) {
            return false;
        }

        
        /*
         * Query controller/URL mapping and obtain the controller
         * that will process the request. If no controller is available,
         * return false and let other filters/servlets process the request.
         */
        IGTVGController controller = this.application.resolveControllerForRequest(request);
        if (controller == null) {
            return false;
        }

        /*
         * Obtain the TemplateEngine instance.
         */
        ITemplateEngine templateEngine = this.application.getTemplateEngine();

        /*
         * Write the response headers
         */
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        /*
         * Execute the controller and process view template,
         * writing the results to the response writer. 
         */
        controller.process(
                request, response, this.servletContext, templateEngine);
        
        return true;
        
    } catch (Exception e) {
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (final IOException ignored) {
            // Just ignore this
        }
        throw new ServletException(e);
    }
    
}
```
这是我们的IGTVGController接口：
```
public interface IGTVGController {

    public void process(
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext, ITemplateEngine templateEngine);    
    
}
```
我们现在要做的就是创建IGTVGController接口的实现，使用ITemplateEngine对象从服务中检索数据并处理模板。

最后，它将如下所示：

![](http://www.thymeleaf.org/doc/tutorials/3.0/images/usingthymeleaf/gtvg-view.png)

### 2.2 Creating and configuring the Template Engine

```
ITemplateEngine templateEngine = this.application.getTemplateEngine();
```

这意味着GTVGApplication类负责创建和配置Thymeleaf应用程序中最重要的对象之一：TemplateEngine实例（ITemplateEngine接口的实现）。
对象初始化如下
```
public class GTVGApplication {
  
    
    ...
    private static TemplateEngine templateEngine;
    ...
    
    
    public GTVGApplication(final ServletContext servletContext) {

        super();

        ServletContextTemplateResolver templateResolver = 
                new ServletContextTemplateResolver(servletContext);
        
        // HTML is the default mode, but we set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // Template cache TTL=1h. If not set, entries would be cached until expelled by LRU
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        
        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
        templateResolver.setCacheable(true);
        
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        
        ...

    }

}
```
有很多方法来配置TemplateEngine对象，但现在这几行代码将足够教给我们所需的步骤。
- The Template Resolver
```
ServletContextTemplateResolver templateResolver = 
        new ServletContextTemplateResolver(servletContext);
```
模板解析器是实现名为org.thymeleaf.templateresolver.ITemplateResolver的Thymeleaf API的接口：

```
public interface ITemplateResolver {
    ...
  
    /*
     * Templates are resolved by their name (or content) and also (optionally) their 
     * owner template in case we are trying to resolve a fragment for another template.
     * Will return null if template cannot be handled by this template resolver.
     */
    public TemplateResolution resolveTemplate(
            final IEngineConfiguration configuration,
            final String ownerTemplate, final String template,
            final Map<String, Object> templateResolutionAttributes);
}
```

这些对象负责确定如何访问我们的模板，在这个GTVG应用程序中，org.thymeleaf.templateresolver.ServletContextTemplateResolver意味着我们将从Servlet上下文中获取我们的模板文件作为资源：应用程序范围的javax 每个Java Web应用程序中存在的.servlet.ServletContext对象，并从Web应用程序根目录中解析资源。
但是，这并不是我们可以对模板解析器所说的，因为我们可以在其上设置一些配置参数。 
一，模板模式( template mode)：
```
templateResolver.setTemplateMode(TemplateMode.HTML);
```

HTML是ServletContextTemplateResolver的默认模板模式，但无论如何，建立它是很好的做法，以便我们的代码清楚地说明了发生了什么。
```
templateResolver.setPrefix("/WEB-INF/templates/");
templateResolver.setSuffix(".html");
```
前缀和后缀修改我们将传递给引擎的模板名称，以获取要使用的真实资源名称。

使用此配置，模板名称“product / list”将对应于：
```
servletContext.getResourceAsStream("/WEB-INF/templates/product/list.html")
```
可选地，解析的模板可以在缓存中生存的时间量通过cacheTTLMs属性在Template Resolver中进行配置：
```
templateResolver.setCacheTTLMs(3600000L);
```
如果达到最大高速缓存大小，并且它是当前缓存的最早的条目，模板仍然可以在达到TTL之前被从缓存中排出。
** 缓存行为和大小可由用户通过实现ICacheManager接口或修改StandardCacheManager对象来管理默认缓存来定义。**

有更多的了解模板解析器，但现在我们来看看我们的模板引擎对象的创建。

- The Template Engine

模板引擎对象是org.thymeleaf.ITemplateEngine接口的实现。 这些实现之一由Thymeleaf核心提供：
org.thymeleaf.TemplateEngine，我们在此创建一个实例：
```
templateEngine = new TemplateEngine();
templateEngine.setTemplateResolver(templateResolver);
```
模板解析器是TemplateEngine需要的唯一必需参数，尽管还有很多其他参数将被覆盖（消息解析器，缓存大小等）。 现在，这就是我们所需要的。
我们的模板引擎现在已经准备就绪，我们可以开始使用Thymeleaf创建我们的页面。

