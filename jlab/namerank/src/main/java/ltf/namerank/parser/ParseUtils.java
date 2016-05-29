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
                try {
                    parser.handle(f.getPath(), file2Str(f.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static String file2Str(final String fn) throws IOException {
        File f;

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fn), "utf8"));
        String content = "", line;
        while ((line = reader.readLine()) != null) content += line + "\n";
        return content;
    }
}
