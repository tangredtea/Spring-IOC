package myspring.springframework.aop;

/**
 * @author Ryan
 */
public interface PointcutAdvisor extends Advisor{

    /**
     * 得到连接点
     * @return 连接点
     */
    Pointcut getPointcut();
}
