package com.xjl.microservice.application;

/**
 * @Classname package_info
 * @Description TODO
 * @Date 2021/9/14 10:01
 * @Created by xjl
 */
public class package_info {
    /**
     * @description
     *
     *Application层 :  相对于领域层,应用层是很薄的一层,应用层定义了软件要完成的任务,要尽量简单。
     * 它不包含任务业务规则或知识,为下一层的领域对象协助任务、委托工作。它没有反映业务情况的状态,但它可以具有反映用户或程序的某个任务的进展状态。对外为展现层提供各种应用功能(service）。
     * 对内 调用领域层（领域对象或领域服务）完成各种业务逻辑任务(task)。这一层也很适合写一些任务处理,日志监控。
     *
     * 注 : 这里图里面所说的对内对外，对程序而言，事实上是从展现层调用应用层，应用层调用领域层，领域层或调用基础设施层。
     *
     * @param: null
     * @date: 2021/9/14 10:01
     * @return:
     * @author: xjl
    */
}
