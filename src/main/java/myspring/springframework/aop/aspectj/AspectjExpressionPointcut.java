package myspring.springframework.aop.aspectj;

import myspring.springframework.aop.ClassFilter;
import myspring.springframework.aop.MethodMatcher;
import myspring.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 切点表达式
 * 使用 AspectJ 编织器来评估切入点表达式。
 * @author Ryan
 */
public class AspectjExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    private final PointcutExpression pointcutExpression;

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    public AspectjExpressionPointcut(String expression){
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 匹配方法
     *
     * @param clazz 类
     * @return 布尔值
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * 匹配方法
     *
     * @param method      方法
     * @param targetClass 目标class
     * @return 布尔值
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    /**
     * class过滤器
     *
     * @return filter
     */
    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    /**
     * 方法匹配
     *
     * @return matcher
     */
    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
