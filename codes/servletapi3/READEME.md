## Servlet API 3

- 1.声明Listener,Servlet,Filter

    ```
   @WebListener

   @WebServlet(name = "servlet1", urlPatterns = {"/s1", "/s1/*"}, loadOnStartup = 1,
           initParams = {@WebInitParam(name = "msg", value="hello world")
           })

    @WebFilter(urlPatterns = {"/*"},filterName ="filter1",dispatcherTypes ={DispatcherType.REQUEST,DispatcherType.FORWARD} )
    ```
- 2.ServletContainerInitializer

    \resources\META-INF\javax.servlet.ServletContainerInitializer

-3.ServletContainerInitializer 动态注册
```
public class MyServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext sc) throws ServletException {
        System.out.println("MyServletContainerInitializer init");
        ServletRegistration.Dynamic dynamic = sc.addServlet("dynamicServlet4", DynamicServlet.class);
        dynamic.addMapping("/dynamic4");

        sc.getServletRegistrations().get("dynamicServlet4").addMapping("/dynamic41");
    }
}
```
其中Set<Class<?>> c,是由HandlesTypes注解声明。
org.springframework.web.WebApplicationInitializer是一个HandlesTypes。具体参见SpringServletContainerInitializer。

- 4. 配置Spring MVC环境

controller
```
@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.hellojd.servletapi.springmvc.**.web"},
                includeFilters = {@ComponentScan.Filter(value = Controller.class,type= FilterType.ANNOTATION)})
```
定义service
```
@Configuration
@ComponentScan(value = {"com.hellojd.servletapi.**.service"},
               excludeFilters={@ComponentScan.Filter(type =FilterType.ANNOTATION,value = Controller.class)})
```