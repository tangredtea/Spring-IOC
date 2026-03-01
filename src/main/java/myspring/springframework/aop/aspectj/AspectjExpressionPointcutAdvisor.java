package myspring.springframework.aop.aspectj;

import myspring.springframework.aop.Pointcut;
import myspring.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * Spring AOP Advisor that can be used with any AspectJ pointcut expression.
 * @author Ryan
 */
public class AspectjExpressionPointcutAdvisor implements PointcutAdvisor {

    /**
     * Pointcut
     */
    private AspectjExpressionPointcut pointcut;

    /**
     * Interceptor advice
     */
    private Advice advice;

    /**
     * Expression
     */
    private String expression;

    public void setExpression(String expression){
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    /**
     * Get the pointcut
     *
     * @return the pointcut
     */
    @Override
    public Pointcut getPointcut() {
        if (null == pointcut){
            pointcut = new AspectjExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }
}
