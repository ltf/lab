package ltf.namerank.utils;

import java.io.*;
import java.util.function.Consumer;

/**
 * @author ltf
 * @since 5/31/16, 10:24 PM
 */
public class FileUtils {

    /**
     * save string to file, use utf8 encoding
     */
    public static void str2File(String fn, String content) throws IOException {
        str2File(new File(fn), content);
    }

    public static void str2File(File f, String content) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf8"));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    /**
     * file content to string, use utf8 encoding
     */
    public static String file2Str(final String fn) throws IOException {
        return file2Str(new File(fn));
    }

    /**
     * file content to string, use utf8 encoding
     */
    public static String file2Str(final File f) throws IOException {
        char[] buf = new char[1024 * 8];
        FileInputStream fis = new FileInputStream(f);
        InputStreamReader fr = new InputStreamReader(fis, "utf8");
        StringBuilder sb = new StringBuilder();
        int len = 0;
        while ((len = fr.read(buf, 0, buf.length)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
