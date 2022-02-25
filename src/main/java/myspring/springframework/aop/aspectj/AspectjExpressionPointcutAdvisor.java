package myspring.springframework.aop.aspectj;

import myspring.springframework.aop.Pointcut;
import myspring.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @author Ryan
 */
public class AspectjExpressionPointcutAdvisor implements PointcutAdvisor {

    /**
     * 切面
     */
    private AspectjExpressionPointcut pointcut;

    /**
     * 具体拦截方法
     */
    private Advice advice;

    /**
     * 表达式
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
     * 得到连接点
     *
     * @return 连接点
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
