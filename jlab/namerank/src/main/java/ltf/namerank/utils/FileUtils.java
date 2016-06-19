package ltf.namerank.utils;

import java.io.*;
import java.util.Collection;

/**
 * @author ltf
 * @since 5/31/16, 10:24 PM
 */
public class FileUtils {

    /**
     * save string to file, use utf8 encoding
     */
    public static void str2File(String content, String fn) throws IOException {
        str2File(content, new File(fn));
    }

    public static void str2File(String content, File f) throws IOException {
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

    /**
     * save lines to file
     */
    public static void lines2File(Iterable<String> lines, String fn) throws IOException {
        StringBuilder sb = new StringBuilder();
        lines.forEach(line -> sb.append(line).append("\n"));
        FileUtils.str2File(sb.toString(), fn);
    }

    /**
     * save lines to file
     */
    public static void file2Lines(Collection<String> lines, String fn) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(file2Str(fn)));
        String line;
        while ((line = br.readLine()) != null) lines.add(line);
    }
}
