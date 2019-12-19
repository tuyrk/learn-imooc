package com.tuyrk.analog_jdkproxy;

import org.apache.commons.io.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;


/**
 * 模拟JDK动态代理
 * 代理类
 *
 * @author tuyrk
 */
public class Proxy {
    public static Object newProxyInstance(Class infce, InvocationHandler h)
            throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String rt = "\r\n";
        StringBuilder methodStr = new StringBuilder();
        for (Method m : infce.getMethods()) {
            Class<?>[] parameterTypes = m.getParameterTypes();
            methodStr.
                    append("@Override").append(rt).
                    append("public void ").append(m.getName()).append("(");
            // 参数
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i != 0) {
                    methodStr.append(" ,");
                }
                methodStr.append(parameterTypes[i].getName()).append(" p").append(i);
            }
            methodStr.append(") {").append(rt)
                    .append("    try {").append(rt)
                    .append("        Method md = ").append(infce.getName()).append(".class.getMethod(\"").append(m.getName()).append("\"");
            for (Class<?> parameterType : parameterTypes) {
                methodStr.append(" ,").append(parameterType.getName()).append(".class");
            }
            methodStr.append(");").append(rt)
                    .append("        h.invoke(this, md, new String[]{");
            // 参数
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i != 0) {
                    methodStr.append(" ,");
                }
                methodStr.append("p").append(i);
            }
            methodStr.append("});").append(rt)
                    .append("    } catch (Exception e) {").append(rt)
                    .append("        e.printStackTrace();").append(rt)
                    .append("    }").append(rt)
                    .append("}").append(rt);
        }
        String str = "package com.tuyrk.analog_jdkproxy;" + rt +
                "import com.tuyrk.analog_jdkproxy.InvocationHandler;" + rt +
                "import java.lang.reflect.Method;" + rt +
                "public class $Proxy0 implements " + infce.getName() + " {" + rt +
                "    private InvocationHandler h;" + rt +
                "    public $Proxy0(InvocationHandler h) {" + rt +
                "        this.h = h;" + rt +
                "    }" + rt +
                methodStr + rt +
                "}" + rt;

        // 产生代理类的Java文件
        String filename = System.getProperty("user.dir") + "/214-proxy-mode/target/classes/com/tuyrk/analog_jdkproxy/$Proxy0.java";
        File file = new File(filename);
        FileUtils.writeStringToFile(file, str, StandardCharsets.UTF_8);

        // 编译-拿到编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 文件管理者
        StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
        // 获取文件
        Iterable<? extends JavaFileObject> units = fileMgr.getJavaFileObjects(filename);
        // 编译任务
        CompilationTask task = compiler.getTask(null, fileMgr, null, null, null, units);
        // 进行编译
        task.call();
        fileMgr.close();

        // 获取类加载器
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        // 加载到内存
        Class<?> c = cl.loadClass("com.tuyrk.analog_jdkproxy.$Proxy0");

        // 获取构造器
        Constructor<?> ctr = c.getConstructor(InvocationHandler.class);
        return ctr.newInstance(h);
    }
}
