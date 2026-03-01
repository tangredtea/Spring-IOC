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
 * Pointcut Expression
 * Uses the AspectJ weaver to evaluate pointcut expressions.
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
     * Match class
     *
     * @param clazz the class to match
     * @return boolean
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * Match method
     *
     * @param method      the method to match
     * @param targetClass the target class
     * @return boolean
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    /**
     * Get class filter
     *
     * @return filter
     */
    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    /**
     * Get method matcher
     *
     * @return matcher
     */
    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
