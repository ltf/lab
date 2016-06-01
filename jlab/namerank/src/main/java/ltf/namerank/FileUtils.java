package ltf.namerank;

import java.io.*;

/**
 * @author ltf
 * @since 5/31/16, 10:24 PM
 */
public class FileUtils {


    /**
     * save string to file
     */
    public static void str2File(String fn, String content) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fn)), "utf8"));
        writer.write(content);
        writer.flush();
        writer.close();
    }


    /**
     * file content to string
     */
    public static String file2Str(final String fn) throws IOException {
        return file2Str(new File(fn));
    }

    /**
     * file content to string
     */
    public static String file2Str(final File f) throws IOException {
        char[] buf = new char[1024 * 8];
        FileReader fr = new FileReader(f);
        StringBuilder sb = new StringBuilder();
        int len = 0;
        while ((len =fr.read(buf, 0, buf.length))>0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
