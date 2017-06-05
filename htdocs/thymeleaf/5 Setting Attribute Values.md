# 5 Setting Attribute Values

## 5.1 Setting the value of any attribute

```
<form action="subscribe.html">
  <fieldset>
    <input type="text" name="email" />
    <input type="submit" value="Subscribe!" />
  </fieldset>
</form>
```
与Thymeleaf一样，这个模板比一个Web应用程序的模板更像一个静态原型。首先，我们表单中的action属性静态地链接到模板文件本身，这样就没有可用的URL重写的地方。
其次，提交按钮中的值属性使其以英文显示文本，但我们希望将其国际化。

```
<form action="subscribe.html" th:attr="action=@{/subscribe}">
  <fieldset>
    <input type="text" name="email" />
    <input type="submit" value="Subscribe!" th:attr="value=#{subscribe.submit}"/>
  </fieldset>
</form>
```

这个概念很简单：th：attr只需要一个赋值给一个属性的表达式。创建相应的控制器和消息文件后，处理此文件的结果将是：
```
<form action="/gtvg/subscribe">
  <fieldset>
    <input type="text" name="email" />
    <input type="submit" value="¡Suscríbe!"/>
  </fieldset>
</form>
```
但是如果我们想要一次设置多个属性呢？ XML规则不允许您在标签中设置两次属性，因此：attr将以逗号分隔的分配列表，如：

```
<img src="../../images/gtvglogo.png" 
     th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
```
## 5.2 Setting value to specific attributes
```
<form action="subscribe.html" th:action="@{/subscribe}">

<li><a href="product/list.html" th:href="@{/product/list}">Product List</a></li>
```

## 5.3 Setting more than one value at a time

```
<img src="../../images/gtvglogo.png" 
     th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
	 相当于
<img src="../../images/gtvglogo.png" 
     th:src="@{/images/gtvglogo.png}" th:title="#{logo}" th:alt="#{logo}" />	 
```

## 5.4 Appending and prepending
Thymeleaf还提供th：attrappend和th：attrprepend属性，它们将评估结果附加（后缀）或前缀（前缀）到现有属性值。
```
<input type="button" value="Do it!" class="btn" th:attrappend="class=${' ' + cssStyle}" />
渲染后
<input type="button" value="Do it!" class="btn warning" />
```

## 5.5 Fixed-value boolean attributes

HTML具有布尔属性的概念，没有值的属性，并且存在一个意味着值为“true”。 在XHTML中，这些属性只取一个值，它本身就是。

```
<input type="checkbox" name="option2" checked /> <!-- HTML -->
<input type="checkbox" name="option1" checked="checked" /> <!-- XHTML -->
```
标准方言包括允许您通过评估条件设置这些属性的属性，以便如果评估为true，则该属性将被设置为其固定值，如果评估为false，则不会设置该属性：
```
<input type="checkbox" name="active" th:checked="${user.active}" />
```
## 5.6 Setting the value of any attribute (default attribute processor)
Thymeleaf提供了一个默认属性处理器，允许我们设置任何属性的值，即使在标准方言中没有为其定义特定的th *处理器。

```
<span th:whatever="${user.name}">...</span>
```

## 5.7 Support for HTML5-friendly attribute and element names
```
<table>
    <tr data-th-each="user : ${users}">
        <td data-th-text="${user.login}">...</td>
        <td data-th-text="${user.name}">...</td>
    </tr>
</table>
```





