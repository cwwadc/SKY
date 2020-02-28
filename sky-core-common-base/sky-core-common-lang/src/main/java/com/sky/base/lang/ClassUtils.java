package com.sky.base.lang;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.sky.base.lang.string.StringUtils;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-26
 */
public class ClassUtils {

	/**
	 * 是否为基本类型
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isPrimitiveClass(Object object) {
		return object == null ? false : isPrimitiveClass(object.getClass());
	}

	/**
	 * 是否为基本类型
	 * 
	 * @param clz
	 * @return
	 */
	public static boolean isPrimitiveClass(Class<?> clz) {
		try {
			return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 是否为基本类型或String类型
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isPrimitiveClassOrString(Object object) {
		return object == null ? false : isPrimitiveClassOrString(object.getClass());
	}

	/**
	 * 是否为基本类型或String类型
	 * 
	 * @param clz
	 * @return
	 */
	public static boolean isPrimitiveClassOrString(Class<?> clz) {
		if (String.class.isAssignableFrom(clz) || isPrimitiveClass(clz)) {
			return true;
		}
		return false;
	}
	
	/**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }
    
    /**获取指定包下的类（结果集为未链接的类）
     * @param packageName 包名
     * @return
     */
    public static List<Class<?>> getPackageClasses(String packageName){
    	try {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
					Thread.currentThread().getContextClassLoader());
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ packageName.replace(".", "/") + "/**/*.class";
			Resource[] resources = resolver.getResources(packageSearchPath);
			if(resources.length < 1) {
				return Collections.emptyList();
			}
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for (int i = 0; i < resources.length; i++) {
				String classFilePath = resources[i].getURI().toString().replace("/", ".").replace("\\", ".");
				String fullClassName = classFilePath.substring(classFilePath.indexOf(packageName)).replace(".class", "");
				classes.add(Thread.currentThread().getContextClassLoader().loadClass(fullClassName));
			}
			return classes;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return Collections.emptyList();
    }
}


