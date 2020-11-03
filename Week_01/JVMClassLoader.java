
import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class JVMClassLoader {

    public static void main(String[] args) {
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL urL : urLs) {
            System.out.println(" ==> " + urL.toExternalForm());
        }

        printClassLoader("扩展类加载器", JVMClassLoader.class.getClassLoader().getParent());

        printClassLoader("应用加载器", JVMClassLoader.class.getClassLoader());
    }

    private static void printClassLoader(String name, ClassLoader classLoader) {
        if (null != classLoader) {
            System.out.println(name + " -> " + classLoader.toString());
            printURLForClassLoader(classLoader);
        } else {
            System.out.println(name + " -> " + "null");
        }
    }

    private static void printURLForClassLoader(ClassLoader classLoader) {
        Object ucp = insightField(classLoader, "ucp");
        ArrayList path = (ArrayList) insightField(ucp, "path");

        assert path != null;
        for (Object pa : path) {
            System.out.println(" ==> " + pa);
        }
    }

    private static Object insightField(Object obj, String fieldName) {
        Field f = null;
        try {
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fieldName);
            } else {
                f = obj.getClass().getDeclaredField(fieldName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

}
