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
超出字符数上限
5000/5000
比 5000 个字符的上限多出 16622 个字符：
epeated (for example, as a result of an iteration). You can check what functions are offered by each of these utility objects in the Appendix B. Reformatting dates in our home page Now we know about these utility objects, we could use them to change the way in which we show the date in our home page. Instead of doing this in our HomeController: SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy"); Calendar cal = Calendar.getInstance(); WebContext ctx = new WebContext(request, servletContext, request.getLocale()); ctx.setVariable("today", dateFormat.format(cal.getTime())); templateEngine.process("home", ctx, response.getWriter()); …we can do just this: WebContext ctx = new WebContext(request, response, servletContext, request.getLocale()); ctx.setVariable("today", Calendar.getInstance()); templateEngine.process("home", ctx, response.getWriter()); …and then perform date formatting in the view layer itself: <p> Today is: <span th:text="${#calendars.format(today,'dd MMMM yyyy')}">13 May 2011</span> </p> 4.3 Expressions on selections (asterisk syntax) Not only can variable expressions be written as ${...}, but also as *{...}. There is an important difference though: the asterisk syntax evaluates expressions on selected objects rather than on the whole context. That is, as long as there is no selected object, the dollar and the asterisk syntaxes do exactly the same. And what is a selected object? The result of an expression using the th:object attribute. Let’s use one in our user profile (userprofile.html) page: <div th:object="${session.user}"> <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p> <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p> <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p> </div> Which is exactly equivalent to: <div> <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p> <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p> <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p> </div> Of course, dollar and asterisk syntax can be mixed: <div th:object="${session.user}"> <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p> <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p> <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p> </div> When an object selection is in place, the selected object will also be available to dollar expressions as the #object expression variable: <div th:object="${session.user}"> <p>Name: <span th:text="${#object.firstName}">Sebastian</span>.</p> <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p> <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p> </div> As said, if no object selection has been performed, dollar and asterisk syntaxes are equivalent. <div> <p>Name: <span th:text="*{session.user.name}">Sebastian</span>.</p> <p>Surname: <span th:text="*{session.user.surname}">Pepper</span>.</p> <p>Nationality: <span th:text="*{session.user.nationality}">Saturn</span>.</p> </div> 4.4 Link URLs Because of their importance, URLs are first-class citizens in web application templates, and the Thymeleaf Standard Dialect has a special syntax for them, the @ syntax: @{...} There are different types of URLs: Absolute URLs: http://www.thymeleaf.org Relative URLs, which can be: Page-relative: user/login.html Context-relative: /itemdetails?id=3 (context name in server will be added automatically) Server-relative: ~/billing/processInvoice (allows calling URLs in another context (= application) in the same server. Protocol-relative URLs: //code.jquery.com/jquery-2.0.3.min.js The real processing of these expressions and their conversion to the URLs that will be output is done by implementations of the org.thymeleaf.linkbuilder.ILinkBuilder interface that are registered into the ITemplateEngine object being used. By default, a single implementation of this interface is registered of the class org.thymeleaf.linkbuilder.StandardLinkBuilder, which is enough for both offline (non-web) and also web scenarios based on the Servlet API. Other scenarios (like integration with non-ServletAPI web frameworks) might need specific implementations of the link builder interface. Let’s use this new syntax. Meet the th:href attribute: <!-- Will produce 'http://localhost:8080/gtvg/order/details?orderId=3' (plus rewriting) --> <a href="details.html" th:href="@{http://localhost:8080/gtvg/order/details(orderId=${o.id})}">view</a> <!-- Will produce '/gtvg/order/details?orderId=3' (plus rewriting) --> <a href="details.html" th:href="@{/order/details(orderId=${o.id})}">view</a> <!-- Will produce '/gtvg/order/3/details' (plus rewriting) --> <a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a> Some things to note here: th:href is a modifier attribute: once processed, it will compute the link URL to be used and set that value to the href attribute of the <a> tag. We are allowed to use expressions for URL parameters (as you can see in orderId=${o.id}). The required URL-parameter-encoding operations will also be automatically performed. If several parameters are needed, these will be separated by commas: @{/order/process(execId=${execId},execType='FAST')} Variable templates are also allowed in URL paths: @{/order/{orderId}/details(orderId=${orderId})} Relative URLs starting with / (eg: /order/details) will be automatically prefixed by the application context name. If cookies are not enabled or this is not yet known, a ";jsessionid=..." suffix might be added to relative URLs so that the session is preserved. This is called URL Rewriting and Thymeleaf allows you to plug in your own rewriting filters by using the response.encodeURL(...) mechanism from the Servlet API for every URL. The th:href attribute allows us to (optionally) have a working static href attribute in our template, so that our template links remained navigable by a browser when opened directly for prototyping purposes. As was the case with the message syntax (#{...}), URL bases can also be the result of evaluating another expression: <a th:href="@{${url}(orderId=${o.id})}">view</a> <a th:href="@{'/details/'+${user.login}(orderId=${o.id})}">view</a> A menu for our home page Now that we know how to create link URLs, what about adding a small menu in our home page for some of the other pages in the site? <p>Please select an option</p> <ol> <li><a href="product/list.html" th:href="@{/product/list}">Product List</a></li> <li><a href="order/list.html" th:href="@{/order/list}">Order List</a></li> <li><a href="subscribe.html" th:href="@{/subscribe}">Subscribe to our Newsletter</a></li> <li><a href="userprofile.html" th:href="@{/userprofile}">See User Profile</a></li> </ol> Server root relative URLs An additional syntax can be used to create server-root-relative (instead of context-root-relative) URLs in order to link to different contexts in the same server. These URLs will be specified like @{~/path/to/something} 4.5 Fragments Fragment expressions are an easy way to represent fragments of markup and move them around templates. This allows us to replicate them, pass them to other templates as arguments, and so on. The most common use is for fragment insertion using th:insert or th:replace (more on these in a later section): <div th:insert="~{commons :: main}">...</div> But they can be used anywhere, just as any other variable: <div th:with="frag=~{footer :: #main/text()}"> <p th:insert="${frag}"> </div> Later in this tutorial there is an entire section devoted to Template Layout, including deeper explanation of fragment expressions. 4.6 Literals Text literals Text literals are just character strings specified between single quotes. They can include any character, but you should escape any single quotes inside them using \'. <p> Now you are looking at a <span th:text="'working web application'">template file</span>. </p> Number literals Numeric literals are just that: numbers. <p>The year is <span th:text="2013">1492</span>.</p> <p>In two years, it will be <span th:text="2013 + 2">1494</span>.</p> Boolean literals The boolean literals are true and false. For example: <div th:if="${user.isAdmin()} == false"> ... In this example, the == false is written outside the braces, and so it is Thymeleaf that takes care of it. If it were written inside the braces, it would be the responsibility of the OGNL/SpringEL engines: <div th:if="${user.isAdmin() == false}"> ... The null literal The null literal can be also used: <div th:if="${variable.something} == null"> ... Literal tokens Numeric, boolean and null literals are in fact a particular case of literal tokens. These tokens allow a little bit of simplification in Standard Expressions. They work exactly the same as text literals ('...'), but they only allow letters (A-Z and a-z), numbers (0-9), brackets ([ and ]), dots (.), hyphens (-) and underscores (_). So no whitespaces, no commas, etc. The nice part? Tokens don’t need any quotes surrounding them. So we can do this: <div th:class="content">...</div> instead of: <div th:class="'content'">...</div> 4.7 Appending texts Texts, no matter whether they are literals or the result of evaluating variable or message expressions, can be easily appended using the + operator: <span th:text="'The name of the user is ' + ${user.name}"> 4.8 Literal substitutions Literal substitutions allow for an easy formatting of strings containing values from variables without the need to append literals with '...' + '...'. These substitutions must be surrounded by vertical bars (|), like: <span th:text="|Welcome to our application, ${user.name}!|"> Which is equivalent to: <span th:text="'Welcome to our application, ' + ${user.name} + '!'"> Literal substitutions can be combined with other types of expressions: <span th:text="${onevar} + ' ' + |${twovar}, ${threevar}|"> Only variable/message expressions (${...}, *{...}, #{...}) are allowed inside |...| literal substitutions. No other literals ('...'), boolean/numeric tokens, conditional expressions etc. are. 4.9 Arithmetic operations Some arithmetic operations are also available: +, -, *, / and %. <div th:with="isEven=(${prodStat.count} % 2 == 0)"> Note that these operators can also be applied inside OGNL variable expressions themselves (and in that case will be executed by OGNL instead of the Thymeleaf Standard Expression engine): <div th:with="isEven=${prodStat.count % 2 == 0}"> Note that textual aliases exist for some of these operators: div (/), mod (%). 4.10 Comparators and Equality Values in expressions can be compared with the >, <, >= and <= symbols, and the == and != operators can be used to check for equality (or the lack of it). Note that XML establishes that the < and > symbols should not be used in attribute values, and so they should be substituted by &lt; and &gt;. <div th:if="${prodStat.count} &gt; 1"> <span th:text="'Execution mode is ' + ( (${execMode} == 'dev')? 'Development' : 'Production')"> A simpler alternative may be using textual aliases that exist for some of these operators: gt (>), lt (<), ge (>=), le (<=), not (!). Also eq (==), neq/ne (!=). 4.11 Conditional expressions Conditional expressions are meant to evaluate only one of two expressions depending on the result of evaluating a condition (which is itself another expression). Let’s have a look at an example fragment (introducing another attribute modifier, th:class): <tr th:class="${row.even}? 'even' : 'odd'"> ... </tr> All three parts of a conditional expression (condition, then and else) are themselves expressions, which means that they can be variables (${...}, *{...}), messages (#{...}), URLs (@{...}) or literals ('...'). Conditional expressions can also be nested using parentheses: <tr th:class="${row.even}? (${row.first}? 'first' : 'even') : 'odd'"> ... </tr> Else expressions can also be omitted, in which case a null value is returned if the condition is false: <tr th:class="${row.even}? 'alt'"> ... </tr> 4.12 Default expressions (Elvis operator) A default expression is a special kind of conditional value without a then part. It is equivalent to the Elvis operator present in some languages like Groovy, lets you specify two expressions: the first one is used if it doesn’t evaluate to null, but if it does then the second one is used. Let’s see it in action in our user profile page: <div th:object="${session.user}"> ... <p>Age: <span th:text="*{age}?: '(no age specified)'">27</span>.</p> </div> As you can see, the operator is ?:, and we use it here to specify a default value for a name (a literal value, in this case) only if the result of evaluating *{age} is null. This is therefore equivalent to: <p>Age: <span th:text="*{age != null}? *{age} : '(no age specified)'">27</span>.</p> As with conditional values, they can contain nested expressions between parentheses: <p> Name: <span th:text="*{firstName}?: (*{admin}? 'Admin' : #{default.username})">Sebastian</span> </p> 4.13 The No-Operation token The No-Operation token is represented by an underscore symbol (_). The idea behind this token is to specify that the desired result for an expression is to do nothing, i.e. do exactly as if the processable attribute (e.g. th:text) was not there at all. Among other possibilities, this allows developers to use prototyping text as default values. For example, instead of: <span th:text="${user.name} ?: 'no user authenticated'">...</span> …we can directly use ‘no user authenticated’ as a prototyping text, which results in code that is both more concise and versatile from a design standpoint: <span th:text="${user.name} ?: _">no user authenticated</span> 4.15 Data Conversion / Formatting Thymeleaf defines a double-brace syntax for variable (${...}) and selection (*{...}) expressions that allows us to apply data conversion by means of a configured conversion service. It basically goes like this: <td th:text="${{user.lastAccessDate}}">...</td> Noticed the double brace there?: ${{...}}. That instructs Thymeleaf to pass the result of the user.lastAccessDate expression to the conversion service and asks it to perform a formatting operation (a conversion to String) before writing the result. Assuming that user.lastAccessDate is of type java.util.Calendar, if a conversion service (implementation of IStandardConversionService) has been registered and contains a valid conversion for Calendar -> String, it will be applied. The default implementation of IStandardConversionService (the StandardConversionService class) simply executes .toString() on any object converted to String. For more information on how to register a custom conversion service implementation, have a look at the More on Configuration section. The official thymeleaf-spring3 and thymeleaf-spring4 integration packages transparently integrate Thymeleaf’s conversion service mechanism with Spring’s own Conversion Service infrastructure, so that conversion services and formatters declared in the Spring configuration will be made automatically available to ${{...}} and *{{...}} expressions. 4.14 Preprocessing In addition to all these features for expression processing, Thymeleaf has the feature of preprocessing expressions. Preprocessing is an execution of the expressions done before the normal one that allows for modification of the expression that will eventually be executed. Preprocessed expressions are exactly like normal ones, but appear surrounded by a double underscore symbol (like __${expression}__). Let’s imagine we have an i18n Messages_fr.properties entry containing an OGNL expression calling a language-specific static method, like: article.text=@myapp.translator.Translator@translateToFrench({0}) …and a Messages_es.properties equivalent: article.text=@myapp.translator.Translator@translateToSpanish({0}) We can create a fragment of markup that evaluates one expression or the other depending on the locale. For this, we will first select the expression (by preprocessing) and then let Thymeleaf execute it: <p th:text="${__#{article.text('textVar')}__}">Some text here...</p> Note that the preprocessing step for a French locale will be creating the following equivalent: <p th:text="${@myapp.translator.Translator@translateToFrench(textVar)}">Some text here...</p> The preprocessing String __ can be escaped in attributes using \_\_.
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


