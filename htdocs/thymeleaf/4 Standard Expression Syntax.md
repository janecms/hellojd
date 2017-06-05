# Tutorial: Using Thymeleaf(3-)
## 4 Standard Expression Syntax
我们将在我们的杂货虚拟商店的开发中休息一下，了解“百里香标准”方言中最重要的部分之一：“Thymeleaf 标准表达”语法。
我们已经看到了两种类型的有效的属性值，以这种语法表示：消息和变量表达式：
```
<p th:utext="#{home.welcome}">Welcome to our grocery store!</p>

<p>Today is: <span th:text="${today}">13 february 2011</span></p>
```
但是，有更多类型的表达，还有更多有趣的细节来了解我们已经知道的内容。首先，我们来看看标准表达式功能的快速总结：
```
    Simple expressions:
        Variable Expressions: ${...}
        Selection Variable Expressions: *{...}
        Message Expressions: #{...}
        Link URL Expressions: @{...}
        Fragment Expressions: ~{...}
    Literals
        Text literals: 'one text', 'Another one!',…
        Number literals: 0, 34, 3.0, 12.3,…
        Boolean literals: true, false
        Null literal: null
        Literal tokens: one, sometext, main,…
    Text operations:
        String concatenation: +
        Literal substitutions: |The name is ${name}|
    Arithmetic operations:
        Binary operators: +, -, *, /, %
        Minus sign (unary operator): -
    Boolean operations:
        Binary operators: and, or
        Boolean negation (unary operator): !, not
    Comparisons and equality:
        Comparators: >, <, >=, <= (gt, lt, ge, le)
        Equality operators: ==, != (eq, ne)
    Conditional operators:
        If-then: (if) ? (then)
        If-then-else: (if) ? (then) : (else)
        Default: (value) ?: (defaultvalue)
    Special tokens:
        No-Operation: _

```
无操作：_

所有这些功能可以组合和嵌套：
```
'User is of type ' + (${user.isAdmin()} ? 'Administrator' : (${user.type} ?: 'Unknown'))
```
### 4.1 Messages
我们已经知道，＃{...}消息表达式允许我们
```
<p th:utext="#{home.welcome}">Welcome to our grocery store!</p>
```
…to this:
```
home.welcome=¡Bienvenido a nuestra tienda de comestibles!
```
但是还有一个方面我们还没有想到：如果消息文本不是完全静态的，会发生什么？例如，如果我们的应用程序知道谁是用户在任何时候访问该网站，我们是否想通过名字问候？
```
<p>¡Bienvenido a nuestra tienda de comestibles, John Apricot!</p>
```
<p>¡Bienvenido a nuestra tienda de comestibles，John Apricot！</ p>

这意味着我们需要在我们的消息中添加一个参数。像这样：
```
home.welcome=¡Bienvenido a nuestra tienda de comestibles, {0}!
```
home.welcome =¡Bienvenido a nuestra tienda de comestibles {0}！

根据java.text.MessageFormat标准语法指定参数，这意味着您可以格式化为这些类的API文档中指定的数字和日期。
为了指定我们参数的值，并给出一个名为user的HTTP会话属性，我们将具有：
```
<p th:utext="#{home.welcome(${session.user.name})}">
  Welcome to our grocery store, Sebastian Pepper!
</p>
```
可以指定几个参数，用逗号分隔。实际上，消息密钥本身可以来自一个变量：
```
<p th:utext="#{${welcomeMsgKey}(${session.user.name})}">
  Welcome to our grocery store, Sebastian Pepper!
</p>
```
### 4.2 Variables
我们已经提到$ {...}表达式实际上是在上下文中包含的变量map上执行的OGNL（Object-Graph Navigation Language）对象。
**
有关OGNL语法和功能的详细信息，请阅读OGNL语言指南
在Spring MVC启用的应用程序中，OGNL将被替换为SpringEL，但其语法与OGNL非常相似（实际上，在大多数常见情况下完全相同）。
**
从OGNL的语法，我们知道：
```
<p>Today is: <span th:text="${today}">13 february 2011</span>.</p>
```
..实际上相当于：
```
ctx.getVariable("today");
```
但是，OGNL允许我们创建更强大的表达式，这就是：
```
<p th:utext="#{home.welcome(${session.user.name})}">
  Welcome to our grocery store, Sebastian Pepper!
</p>
```

...通过执行以下命令获取用户名：
```
((User) ctx.getVariable("session").get("user")).getName();
```
我们已经知道，＃{...}消息表达式允许我们链接：

<p th：utext =“＃{home.welcome}”>欢迎来到我们的杂货店！</ p>

...到这个：

home.welcome =¡Bienvenido a nuestra tienda de comestibles！

但是还有一个方面我们还没有想到：如果消息文本不是完全静态的，会发生什么？例如，如果我们的应用程序知道谁是用户在任何时候访问该网站，我们是否想通过名字问候？

<p>¡Bienvenido a nuestra tienda de comestibles，John Apricot！</ p>

这意味着我们需要在我们的消息中添加一个参数。像这样：

home.welcome =¡Bienvenido a nuestra tienda de comestibles {0}！

根据java.text.MessageFormat标准语法指定参数，这意味着您可以格式化为这些类的API文档中指定的数字和日期。

为了指定我们参数的值，并给出一个名为user的HTTP会话属性，我们将具有：

<p th：utext =“＃{home.welcome（$ {session.user.name}）}”>
  欢迎来到我们的杂货店，塞巴斯蒂安胡椒！
</ p>

可以指定几个参数，用逗号分隔。实际上，消息密钥本身可以来自一个变量：

<p th：utext =“＃{$ {welcomeMsgKey}（$ {session.user.name}）}”>
  欢迎来到我们的杂货店，塞巴斯蒂安胡椒！
</ p>

4.2变量

我们已经提到$ {...}表达式实际上是在上下文中包含的变量的地图上执行的OGNL（Object-Graph Navigation Language）对象。

    有关OGNL语法和功能的详细信息，请阅读OGNL语言指南

    在Spring MVC启用的应用程序中，OGNL将被替换为SpringEL，但其语法与OGNL非常相似（实际上，在大多数常见情况下完全相同）。

从OGNL的语法，我们知道：

<p>今天是：<span th：text =“$ {today}”> 2011年2月13日</ p>

...实际上相当于：

ctx.getVariable（ “今天”）;

但是，OGNL允许我们创建更强大的表达式，这就是：

<p th：utext =“＃{home.welcome（$ {session.user.name}）}”>
  欢迎来到我们的杂货店，塞巴斯蒂安胡椒！
</ p>

...通过执行以下命令获取用户名：

（（User）ctx.getVariable（“session”）。get（“user”））。getName（）;

但是getter方法导航只是OGNL的一个功能。我们再来看一下：
```
/*
 * Access to properties using the point (.). Equivalent to calling property getters.
 */
${person.father.name}

/*
 * Access to properties can also be made by using brackets ([]) and writing 
 * the name of the property as a variable or between single quotes.
 */
${person['father']['name']}

/*
 * If the object is a map, both dot and bracket syntax will be equivalent to 
 * executing a call on its get(...) method.
 */
${countriesByCode.ES}
${personsByName['Stephen Zucchini'].age}

/*
 * Indexed access to arrays or collections is also performed with brackets, 
 * writing the index without quotes.
 */
${personsArray[0].name}

/*
 * Methods can be called, even with arguments.
 */
${person.createCompleteName()}
${person.createCompleteNameWithSeparator('-')}
```
** Expression Basic Objects
---------------------------
当对上下文变量评估OGNL表达式时，某些对象可用于表达式以获得更高的灵活性。这些对象将被引用（按照OGNL标准），从＃符号开始：

 -   #ctx: the context object.
 -   #vars: the context variables.
 -   #locale: the context locale.
 -   #request: (only in Web Contexts) the HttpServletRequest object.
 -   #response: (only in Web Contexts) the HttpServletResponse object.
 -   #session: (only in Web Contexts) the HttpSession object.
 -   #servletContext: (only in Web Contexts) the ServletContext object.

 所以我们可以这样做：
```
Established locale country: <span th:text="${#locale.country}">US</span>.
```
** Expression Utility Objects
------------------------------
除了这些基本的对象之外，Thymeleaf将为我们提供一组实用对象，这些对象将帮助我们在表达式中执行常见任务。

-    #execInfo: information about the template being processed.
-    #messages: methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
-    #uris: methods for escaping parts of URLs/URIs
-    #conversions: methods for executing the configured conversion service (if any).
-    #dates: methods for java.util.Date objects: formatting, component extraction, etc.
-    #calendars: analogous to #dates, but for java.util.Calendar objects.
-    #numbers: methods for formatting numeric objects.
-    #strings: methods for String objects: contains, startsWith, prepending/appending, etc.
-    #objects: methods for objects in general.
-    #bools: methods for boolean evaluation.
-    #arrays: methods for arrays.
-    #lists: methods for lists.
-    #sets: methods for sets.
-    #maps: methods for maps.
-    #aggregates: methods for creating aggregates on arrays or collections.
-    #ids: methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).
![](http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#appendix-b-expression-utility-objects)
** Reformatting dates in our home page
----------------------------------------
在我们的主页重新格式化日期
现在我们知道这些实用程序对象，我们可以使用它们来改变我们在主页中显示日期的方式。 而不是在我们的HomeController中这样做：
```
SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
Calendar cal = Calendar.getInstance();

WebContext ctx = new WebContext(request, servletContext, request.getLocale());
ctx.setVariable("today", dateFormat.format(cal.getTime()));

templateEngine.process("home", ctx, response.getWriter());
```
我们可以这样
```
WebContext ctx = 
    new WebContext(request, response, servletContext, request.getLocale());
ctx.setVariable("today", Calendar.getInstance());

templateEngine.process("home", ctx, response.getWriter());
```
然后在视图层本身执行日期格式化：
···
<p>
  Today is: <span th:text="${#calendars.format(today,'dd MMMM yyyy')}">13 May 2011</span>
</p>
···
### 4.3 Expressions on selections (asterisk syntax)

不仅可以将变量表达式写为$ {...}，还可以作为* {...}。
有一个重要的区别：星号语法评估所选对象而不是整个上下文的表达式。也就是说，只要没有选定的对象，美元和星号语法就会完全相同。
什么是选定对象？使用th：object属性的表达式的结果。我们在用户个人资料（userprofile.html）页面中使用一个：
```
  <div th:object="${session.user}">
    <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
    <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
    <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
  </div>
```
这完全相当于：
```
<div>
  <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p>
  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
  <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p>
</div>
```
当然，美元和星号的语法可以混合使用：
```
<div th:object="${session.user}">
  <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
  <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
</div>
```

当对象选择到位时，所选对象也将作为#object表达式变量可用于美元表达式：
```
<div th:object="${session.user}">
  <p>Name: <span th:text="${#object.firstName}">Sebastian</span>.</p>
  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
  <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
</div>
```
如上所述，如果没有执行对象选择，则美元和星号语法是等效的。
```
<div>
  <p>Name: <span th:text="*{session.user.name}">Sebastian</span>.</p>
  <p>Surname: <span th:text="*{session.user.surname}">Pepper</span>.</p>
  <p>Nationality: <span th:text="*{session.user.nationality}">Saturn</span>.</p>
</div>
```
### 4.4 Link URLs
由于它们的重要性，URL是Web应用程序模板中的一流公民，而Thymeleaf Standard Dialect具有特殊的语法，@语法：@ {...}
有不同类型的网址：

   - Absolute URLs: http://www.thymeleaf.org
   - Relative URLs, which can be:
      --  Page-relative: user/login.html
      --  Context-relative: /itemdetails?id=3 (context name in server will be added automatically)
      --  Server-relative: ~/billing/processInvoice (allows calling URLs in another context (= application) in the same server.
      --  Protocol-relative URLs: //code.jquery.com/jquery-2.0.3.min.js
这些表达式的真实处理及其转换为将被输出的URL将通过注册到正在使用的ITemplateEngine对象中的org.thymeleaf.linkbuilder.ILinkBuilder接口的实现完成。
默认情况下，该接口的单个​​实现已注册到类org.thymeleaf.linkbuilder.StandardLinkBuilder，这对于脱机（非Web）以及基于Servlet API的Web场景都是足够的。其他情况（如与非ServletAPI Web框架的集成）可能需要链接构建器接口的特定实现。
我们来使用这个新的语法。认识th：href属性：
```
<!-- Will produce 'http://localhost:8080/gtvg/order/details?orderId=3' (plus rewriting) -->
<a href="details.html" 
   th:href="@{http://localhost:8080/gtvg/order/details(orderId=${o.id})}">view</a>

<!-- Will produce '/gtvg/order/details?orderId=3' (plus rewriting) -->
<a href="details.html" th:href="@{/order/details(orderId=${o.id})}">view</a>

<!-- Will produce '/gtvg/order/3/details' (plus rewriting) -->
<a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>
```
要注意
 -   th：href是一个修饰符属性：一旦处理，它将计算要使用的链接URL，并将该值设置为<a>标签的href属性。
 -   我们被允许使用表达式的URL参数（可以在orderId = $ {o.id}中看到）。所需的URL参数编码操作也将自动执行。
 -   如果需要几个参数，这些参数将以逗号分隔：@ {/ order / process（execId = $ {execId}，execType ='FAST'）}
 -   URL路径中也允许使用变量模板：@ {/ order / {orderId} / details（orderId = $ {orderId}）}
 -   以/开头的相对URL（例如：/ order / details）将自动以应用程序上下文名称为前缀。
 -   如果cookie未启用或尚未知道，则可能会在相对URL中添加“; jsessionid = ...”后缀，以便会话被保留。这被称为URL重写，Thymeleaf允许您使用Servlet API中的每个URL的response.encodeURL（...）机制来插入自己的重写过滤器。
 -   th：href属性允许我们（可选地）在我们的模板中有一个工作的静态href属性，这样当我们直接打开原型设计时，我们的模板链接才能被浏览器导航。

与消息语法（＃{...}）的情况一样，URL基数也可以是评估另一个表达式的结果：
```
<a th:href="@{${url}(orderId=${o.id})}">view</a>
<a th:href="@{'/details/'+${user.login}(orderId=${o.id})}">view</a>
```
我们的主页菜单
现在我们知道如何创建链接网址，如何在网站的其他一些页面的主页中添加一个小菜单呢？
```
<p>Please select an option</p>
<ol>
  <li><a href="product/list.html" th:href="@{/product/list}">Product List</a></li>
  <li><a href="order/list.html" th:href="@{/order/list}">Order List</a></li>
  <li><a href="subscribe.html" th:href="@{/subscribe}">Subscribe to our Newsletter</a></li>
  <li><a href="userprofile.html" th:href="@{/userprofile}">See User Profile</a></li>
</ol>
```
服务器根相对URL

可以使用附加语法来创建服务器根目录（而不是上下文相对）URL，以链接到同一服务器中的不同上下文。这些URL将被指定为@{~/path/to/something}

### 4.5 Fragments
片段表达式是表示标记片段的简单方法，并将其移动到模板周围。 这允许我们复制它们，将它们传递给其他模板作为参数，等等。
最常见的用途是使用th：insert或th：replace进行片段插入（稍后部分更详细的介绍）：
```
<div th:insert="~{commons :: main}">...</div>
```
但它们可以在任何地方使用，就像任何其他变量一样：
```
<div th:with="frag=~{footer :: #main/text()}">
  <p th:insert="${frag}">
</div>
```
在本教程的后面，有一个专门用于模板布局的部分，包括对片段表达式的更深入的解释。


