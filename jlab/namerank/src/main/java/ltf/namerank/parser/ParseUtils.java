package ltf.namerank.parser;

import java.io.*;

import static ltf.namerank.FileUtils.file2Str;

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

}
