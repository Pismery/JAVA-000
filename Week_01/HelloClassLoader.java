

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws FileNotFoundException {
        try {
            Object hello = new HelloClassLoader().findClass("Hello").newInstance();
            hello.getClass().getMethod("hello").invoke(hello);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] encodeBytes = readToByteArray("Hello.xlass");
        byte[] bytes = decode(encodeBytes);
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] readToByteArray(String name) {
        byte[] result = new byte[1];

        URL resource = HelloClassLoader.class.getClassLoader().getResource(name);
        String path = resource.getPath();
        try (FileInputStream fis = new FileInputStream(path);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()
        ) {
            byte[] content = new byte[1024];
            int len = 0;
            while ((len = fis.read(content)) != -1) {
                bos.write(content, 0, len);
            }

            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private byte[] decode(byte[] bytes) {
        byte[] decodeBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            int i1 = 255 - new Byte(bytes[i]).intValue();
            decodeBytes[i] = (byte) i1;
        }
        return decodeBytes;
    }
}
