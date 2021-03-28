package myspring.springframework.reader;

/**
 * Bean定义读取
 */
public interface BeanDefinitionReader {

    /**
     * 从某个位置读取Bean的配置
     * @param location 配置文件路径
     * @throws Exception 可能出现的异常
     */
    void loadBeanDefinitions(String location) throws Exception;

}
