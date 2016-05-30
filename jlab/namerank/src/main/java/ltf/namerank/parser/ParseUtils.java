package ltf.namerank.parser;

import ltf.namerank.PathUtils;
import ltf.namerank.db.Hanzi;

import java.io.*;

/**
 * @author ltf
 * @since 5/29/16, 2:35 PM
 */
public class ParseUtils {

    public static void processFilesInDir(final String dir, IParser parser) {
        File directory = new File(dir);
        if (directory.isDirectory() && directory.exists()) {
            for (File f : directory.listFiles()) {
                if (f.isDirectory()) continue;
                try {
                    parser.handle(f.getName(), file2Str(f));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String file2Str(final String fn) throws IOException {
        return file2Str(new File(fn));
    }

    public static String file2Str(final File f) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf8"));
        String content = "", line;
        while ((line = reader.readLine()) != null) content += line + "\n";
        return content;
    }
}
