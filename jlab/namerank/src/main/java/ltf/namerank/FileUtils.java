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
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf8"));
        String content = "", line;
        while ((line = reader.readLine()) != null) content += line + "\n";
        return content;
    }
}
