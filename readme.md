## 简介
这是一个简单的参数校验组件，主要的设计意图是减少controller层大量的“判断`null`代码”。

源代码可作为一种参考实现，具体实现可以根据业务需求定制。实现细节以及单元测试可直接查看源代码。

## 主要用途
* 检查方法参数是否为`null`
* 检查方法参数是否为empty（`String`/`Collection`的实例）
* 检查方法参数的某个属性是否为`null`
* 检查方法参数的某个属性是否为empty（`String`/`Collection`的实例）

## 环境
* Oracle JDK 1.8 u121

## 依赖（详见pom.xml）
* spring-boot
* spring-boot-starter
* spring-boot-starter-web
* spring-boot-starter-aop
* spring-boot-starter-test
* gson（可用任意json库代替）

## 使用方法

### 1. 开启AOP支持
Spring boot 启动类中添加`@EnableAspectJAutoProxy`注解，开启AOP的支持。

### 2. 配置需要拦截的方法
在`AopConfig`类中进行配置，配置一个`package com.sd.advice.ParameterCheckingAdvice`实例，并创建一个切点，设置切点表达式，并关联`package com.sd.advice.ParameterCheckingAdvice`实例。

切点表达式决定了需要拦截的方法，切点表达式语法可以查阅相关资料，此处不再赘述。

```java
    @Bean
    public Advisor parameterCheckingAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 设置切点表达式
        pointcut.setExpression("execution(public * com.sd.controller.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, parameterCheckingAdvice());
    }
```

### 3. 定制异常处理逻辑
异常逻辑位于`package com.sd.advice.ParameterCheckingAdvice`类的`before`方法中，使用`TODO`注释进行标记，可以对相关逻辑进行改写，比如抛出自定义异常等，目前为抛出`RuntimeException`实例。

可以在controller中添加异常处理方法，对参数校验失败异常进行统一处理：
```java
@RestController
public class HelloController {

    @ExceptionHandler({RuntimeException.class})
    public String handle(RuntimeException x) {
        // ... custom exception handling
    }
}
```

### 4. 向方法参数添加注解
`@CheckParam`注解有4个属性，分别对应4种校验策略：
* `notnull`：是否不允许为null
* `notEmpty`：是否不允许为empty（如String、Collection）
* `notNullFields`：不允许为null的属性（主要用于DTO传递参数，目前仅支持一层属性）
* `notEmptyFields`：不允许为empty的属性（主要用于DTO传递参数，目前仅支持一层属性）

```java
    @GetMapping(value = "/param/not-null")
    public String checkParamNull(@CheckParam(notNull = true) String userName) {
        return successResult;
    }

    @GetMapping(value = "/param/not-empty")
    public String checkParamEmpty(@CheckParam(notEmpty = true) String userName) {
        return successResult;
    }

    @PostMapping(value = "/param/field/not-null")
    public String checkParamFieldNull(@RequestBody @CheckParam(notNullFields = {"userName"}) User user) {
        return successResult;
    }

    @PostMapping(value = "/param/field/not-empty")
    public String checkParamFieldEmpty(@RequestBody @CheckParam(notEmptyFields = {"userName"}) User user) {
        return successResult;
    }
```

## 实现细节
待补充。
