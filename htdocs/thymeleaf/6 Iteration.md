# 6 Iteration
## 6.1 Iteration basics

### Using th:each
```
<!DOCTYPE html>

<html xmlns:th="http://www.thymeleaf.org">

  <head>
    <title>Good Thymes Virtual Grocery</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" media="all" 
          href="../../../css/gtvg.css" th:href="@{/css/gtvg.css}" />
  </head>

  <body>

    <h1>Product list</h1>
  
    <table>
      <tr>
        <th>NAME</th>
        <th>PRICE</th>
        <th>IN STOCK</th>
      </tr>
      <tr th:each="prod : ${prods}">
        <td th:text="${prod.name}">Onions</td>
        <td th:text="${prod.price}">2.41</td>
        <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
      </tr>
    </table>
  
    <p>
      <a href="../home.html" th:href="@{/}">Return to home</a>
    </p>

  </body>

</html>
```
你看到的prod:${prods}属性值意味着“对于${prods}的结果中的每个元素，重复这个模板片段，使用名为prod”的变量中的当前元素。 让我们给每个我们看到的东西一个名字：

     我们将调用$ {prods}迭代表达式或迭代变量。
     我们将调用prod的迭代变量或简单的iter变量。

请注意，prod iter变量的作用域为<tr>元素，这意味着它可用于内部标记，如<td>。

## 6.2 Keeping iteration status
```
<table>
  <tr>
    <th>NAME</th>
    <th>PRICE</th>
    <th>IN STOCK</th>
  </tr>
  <tr th:each="prod,iterStat : ${prods}" th:class="${iterStat.odd}? 'odd'">
    <td th:text="${prod.name}">Onions</td>
    <td th:text="${prod.price}">2.41</td>
    <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
  </tr>
</table>
```
状态变量在每个属性中定义，并包含以下数据：
     当前迭代索引，从0开始。这是index属性。
     当前的迭代索引，从1开始。这是count属性。
     迭代变量中元素的总量。 这是size属性。
     每次迭代的iter变量。 这是现在的财产。
     当前的迭代是偶数还是奇数。 这些是偶数/奇数布尔属性。
     当前的迭代是否是第一个迭代。 这是第一个布尔属性。
     当前的迭代是否是最后一个迭代。 这是最后一个布尔属性。

## 6.3 Optimizing through lazy retrieval of data
为了支持这一点，Thymeleaf提供了一种延迟加载上下文变量的机制。 实现ILazyContextVariable接口的上下文变量 - 很可能通过扩展其LazyContextVariable默认实现 - 将在执行时解决。 例如：
```
context.setVariable(
     "users",
     new LazyContextVariable<List<User>>() {
         @Override
         protected List<User> loadValue() {
             return databaseRepository.findAllUsers();
         }
     });
```










