package myspring.springframework.web;

import myspring.springframework.annotation.Controller;
import myspring.springframework.annotation.RequestMapping;
import myspring.springframework.context.ClassPathXmlApplicationContext;
import myspring.springframework.entity.BeanDefinition;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class DispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String, Method> handlerMapping = new HashMap<>();

    private HashSet<Class> classes = new HashSet<>();

    private Map<String, Object> controllerMap = new HashMap<>();

    private ClassPathXmlApplicationContext xmlApplicationContext;

    @Override
    public void init(ServletConfig config) {
        try {
            xmlApplicationContext = new ClassPathXmlApplicationContext("application-annotation.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        doScanner(properties.getProperty("scanPackage"));
        doInstance();
        initHandlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            //处理请求
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500!! Server Exception");
        }
    }

    public void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (handlerMapping.isEmpty()) return;
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if (!handlerMapping.containsKey(url)) {
            response.getWriter().write("404 NOT FOUND!");
            return;
        }
        Method method = handlerMapping.get(url);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Object[] paramValues = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            String requestParam = parameterTypes[i].getSimpleName();
            if (requestParam.equals("HttpServletRequest")) {
                paramValues[i] = request;
                continue;
            }
            if (requestParam.equals("HttpServletResponse")) {
                paramValues[i] = response;
                continue;
            }
            if (requestParam.equals("String")) {
                for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                    paramValues[i] = value;
                }
            }
        }
        try {
            method.invoke(controllerMap.get(url), paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doLoadConfig(String location) {
        //把web.xml中的contextConfigLocation对应value值的文件加载到流里面
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);

        try {
            //用Properties文件加载文件里的内容
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doScanner(String packageName) {
        //把所有的.替换成/
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                //递归读取包
                doScanner(packageName + "." + file.getName());
            } else {
                String className = packageName + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            try {
                //把类搞出来,反射来实例化(只有加@Controller需要实例化)
                Class clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    classes.add(clazz);
                    BeanDefinition definition = new BeanDefinition();
                    definition.setSingleton(true);
                    definition.setBeanClassName(clazz.getName());
                    xmlApplicationContext.addNewBeanDefinition(clazz.getName(), definition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            xmlApplicationContext.refreshBeanFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHandlerMapping() {
        if (classes.isEmpty()) return;
        try {
            for (Class<?> clazz : classes) {
                String baseUrl = "";
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    baseUrl = clazz.getAnnotation(RequestMapping.class).value();
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(RequestMapping.class)) continue;
                    String url = method.getAnnotation(RequestMapping.class).value();
                    url = (baseUrl + "/" + url).replaceAll("/+", "/");
                    handlerMapping.put(url, method);
                    controllerMap.put(url, xmlApplicationContext.getBean(clazz));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
