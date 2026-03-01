package myspring.springframework.aop;

/**
 * Superinterface for all Advisors that are driven by a pointcut.
 *
 * @author Ryan
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * Get the Pointcut that drives this advisor.
     *
     * @return the pointcut
     */
    Pointcut getPointcut();
}
