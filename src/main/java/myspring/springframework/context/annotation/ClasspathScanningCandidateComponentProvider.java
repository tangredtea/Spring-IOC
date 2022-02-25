package myspring.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Ryan
 */
public class ClasspathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackages){
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackages, Component.class);
        for (Class<?> aClass : classes) {
            candidates.add(new BeanDefinition<>(aClass));
        }
        return candidates;
    }

}
