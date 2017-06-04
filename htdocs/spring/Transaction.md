## 事务管理 
 ###  beanFactory lifecycle
 1. BeanNameAware's setBeanName
 2. BeanClassLoaderAware's setBeanClassLoader
 3. BeanFactoryAware's setBeanFactory
 4. ResourceLoaderAware's setResourceLoader (only applicable when running in an application context)
 5. ApplicationEventPublisherAware's setApplicationEventPublisher (only applicable when running in an application context)
 6. MessageSourceAware's setMessageSource (only applicable when running in an application context)
 7. ApplicationContextAware's setApplicationContext (only applicable when running in an application context)
 8. ServletContextAware's setServletContext (only applicable when running in a web application context)
 9. postProcessBeforeInitialization methods of BeanPostProcessors
 10. InitializingBean's afterPropertiesSet
 11. a custom init-method definition
 12. postProcessAfterInitialization methods of BeanPostProcessors
beanfactory shutdown 	
 1. DisposableBean's destroy
 2. a custom destroy-method definition


![classdigram](images/ProxyFactoryBean.png)

###  1. ProxyConfig

方便的用于创建代理的超类配置，以确保所有代理创建者具有一致的属性。
```
	/**
	*   设置是否直接代理目标类，而不是仅代理特定的接口。 默认值为“false”。
	*	将其设置为“true”以强制对TargetSource的暴露目标类进行代理。 
	*	如果该目标类是接口，将为给定的接口创建一个JDK代理。 
	*   如果该目标类是任何其他类，将为给定的类创建一个CGLIB代理。
	*/
	private boolean proxyTargetClass = false;
	/**
	*设置代理是否应该执行积极的优化。 “aggressive optimizations”的确切含义在代理之间有所不同，但通常有一些权衡。 默认值为“false”。
	*例如，优化通常意味着建议更改在代理创建后不会生效。 
	*因此，默认情况下禁用优化。 如果其他设置排除优化，则可以忽略“true”的优化值：例如，如果“publicProxy”设置为“true”，并且与优化不兼容。
	*/
	private boolean optimize = false;
	/**
	*设置是否应该阻止通过此配置创建的代理被转换为Advised,以查询代理状态。
	*默认为“false”，这意味着任何AOP代理可以转换为Advised。
	*/
	boolean opaque = false;
	/**
	是否可以被AOP框架作为ThreadLocal暴露,通过AopContext。默认false,以避免不必要的额外拦截
	*/
	boolean exposeProxy = false;
	/**
	* 设置此配置是否应该被冻结。当配置被冻结时，不会改变任何advice。 这对于优化是有用的，
	*/
	private boolean frozen = false;
```
### 2.AbstractSingletonProxyFactoryBean
	方便的FactoryBean类型的超类,产生单例范围的代理对象。
	```
	//目标对象
	private Object target;
	//被代理的一组接口
	private Class<?>[] proxyInterfaces;
	//在隐式”事务“拦截器之前设置要应用的interceptors (or advisors) 
	private Object[] preInterceptors;
	//在隐式”事务“拦截器后设置要应用的interceptors (or advisors) 
	private Object[] postInterceptors;
	

	//Specify the AdvisorAdapterRegistry to use. Default is the global AdvisorAdapterRegistry.
	private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
	//生成代理的ClassLoader
	private transient ClassLoader proxyClassLoader;

	private Object proxy;
	```
	
### 3.TransactionProxyFactoryBean
	代理工厂bean，用于简化的声明性事务处理。
```
	//设置事务管理器。 这将执行实际的事务管理：这个类只是一种调用它的方式。
	private final TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
	////设置一个pointcut，即根据传递的方法和属性可以导致TransactionInterceptor的条件调用的bean。 注意：总是调用其他拦截器。
	private Pointcut pointcut;

	//将方法名称的属性设置为键和事务属性描述符（通过TransactionAttributeEditor解析）
	public void setTransactionAttributes(Properties transactionAttributes) {
		this.transactionInterceptor.setTransactionAttributes(transactionAttributes);
	}	
	//设置用于查找事务属性的事务属性源。 如果指定一个String属性值，PropertyEditor将从该值创建一个MethodMapTransactionAttributeSource。
	public void setTransactionAttributeSource(TransactionAttributeSource transactionAttributeSource) {
		this.transactionInterceptor.setTransactionAttributeSource(transactionAttributeSource);
	}
```	


##  代理相关

	###  TargetClassAware
	用于将目标类暴露在代理后面的最小界面。
	### Advised
	AOP代理工厂配置接口
	```
	/**是否冻结了“Advised”配置，在这种情况下，无法进行任何建议更改。*/
	boolean isFrozen();
	/** 代理完整的目标类而不是指定的接口？*/
	boolean isProxyTargetClass();
	/**返回由AOP代理代理的接口  */
	Class<?>[] getProxiedInterfaces();
	/**确定给定的接口是否被代理。 */
	boolean isInterfaceProxied(Class<?> intf);

	/** 更改此Advised对象使用的TargetSource。 */
	void setTargetSource(TargetSource targetSource);
	TargetSource getTargetSource();

	/**是否可以被AOP框架作为ThreadLocal暴露,通过AopContext。 */
	void setExposeProxy(boolean exposeProxy);
	boolean isExposeProxy();

	/**设置此代理配置是否经过预筛选，以便它仅包含适用的advisors （与此代理的目标类匹配）。 */
	void setPreFiltered(boolean preFiltered);
	boolean isPreFiltered();

	/**返回适用于此代理的Advisor。*/
	Advisor[] getAdvisors();
	void addAdvisor(Advisor advisor) throws AopConfigException;
	void addAdvisor(int pos, Advisor advisor) throws AopConfigException;
	boolean removeAdvisor(Advisor advisor);
	void removeAdvisor(int index) throws AopConfigException;
	int indexOf(Advisor advisor);
	boolean replaceAdvisor(Advisor a, Advisor b) throws AopConfigException;

	/**将给定的AOP advice 添加到advice（interceptor）链的尾部。这将包含在一个DefaultPointcutAdvisor中，该切点总是适用，*/
	void addAdvice(Advice advice) throws AopConfigException;
	void addAdvice(int pos, Advice advice) throws AopConfigException;
	boolean removeAdvice(Advice advice);
	int indexOf(Advice advice);
	String toProxyConfigString();
	```
	 ### AdvisedSuppor
	AOP代理配置管理器的基类
```
public class AdvisedSupport extends ProxyConfig implements Advised {
	/** Package-protected to allow direct access for efficiency */
	TargetSource targetSource = EMPTY_TARGET_SOURCE;

	/** Advisors是否已针对特定目标类过滤 */
	private boolean preFiltered = false;

	/** The AdvisorChainFactory to use */
	AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

	/** Cache with Method as key and advisor chain List as value */
	private transient Map<MethodCacheKey, List<Object>> methodCache;

	/**
	 * 接口由代理实现。 在List中保存注册顺序，创建具有指定顺序接口的JDK代理。
	 */
	private List<Class> interfaces = new ArrayList<Class>();

	/**Advisor名单 如果添加了一个Advise，它将被包装在一个Advisor中，然后被添加到此列表中。
	 */
	private List<Advisor> advisors = new LinkedList<Advisor>();

	/**
	 * Array updated on changes to the advisors list, which is easier
	 * to manipulate internally.
	 */
	private Advisor[] advisorArray = new Advisor[0];
	}
```	
### ProxyCreatorSupport
···
public class ProxyCreatorSupport extends AdvisedSupport {
	//允许在不改变核心框架的情况下选择不同的策略。
	private AopProxyFactory aopProxyFactory;

	private List<AdvisedSupportListener> listeners = new LinkedList<AdvisedSupportListener>();

	/** Set to true when the first AOP proxy has been created */
	private boolean active = false;

	}
···