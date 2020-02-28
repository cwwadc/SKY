package com.sky.core.service.api.utils;

import java.io.File;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.EndpointImplFactory;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.jaxws.support.JaxWsEndpointImplFactory;
import org.apache.cxf.common.util.Compiler;

/**
 * This class reads a WSDL and creates a dynamic client from it with JAX-WS
 * functionality.
 * This provides support for attachments and other useful things that
 * come with JAX-WS.
 *
 * Use {@link #newInstance} to obtain an instance, and then
 * {@link #createClient(String)} (or other overloads) to create a client.
 *
 * This factory uses the JAXB data binding.
 **/
public class JaxWsDynamicClientFactory extends DynamicClientFactory {
	protected JaxWsDynamicClientFactory(Bus bus) {
        super(bus);
    }

    @Override
    protected EndpointImplFactory getEndpointImplFactory() {
        return JaxWsEndpointImplFactory.getSingleton();
    }

    protected boolean allowWrapperOps() {
        return true;
    }

    /**
     * Create a new instance using a specific <tt>Bus</tt>.
     *
     * @param b the <tt>Bus</tt> to use in subsequent operations with the
     *            instance
     * @return the new instance
     */
    public static JaxWsDynamicClientFactory newInstance(Bus b) {
        return new JaxWsDynamicClientFactory(b);
    }

    /**
     * Create a new instance using a default <tt>Bus</tt>.
     *
     * @return the new instance
     * @see CXFBusFactory#getDefaultBus()
     */
    public static JaxWsDynamicClientFactory newInstance() {
        Bus bus = CXFBusFactory.getThreadDefaultBus();
        return new JaxWsDynamicClientFactory(bus);
    }

    /**
     * 覆写父类的该方法<br/>
     * 注：解决此（错误：编码GBK的不可映射字符）问题
     *
     * @return
     */
    @Override
    protected boolean compileJavaSrc(String classPath, List<File> srcList, String dest) {
        Compiler javaCompiler = new Compiler();

        // 设置编译编码格式（此处为新增代码）
        javaCompiler.setEncoding("UTF-8");
        javaCompiler.setClassPath(classPath);
        javaCompiler.setOutputDir(dest);
        javaCompiler.setTarget("1.8");

        return javaCompiler.compileFiles(srcList);
    }

}
