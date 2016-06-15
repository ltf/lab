package ltf.namerank.utils;

import java.io.*;
import java.util.function.Consumer;

/**
 * Helper for process each line in a file
 *
 * @author ltf
 * @since 16/6/14, 下午4:57
 */
public class LinesInFile {
    private File file;

    public LinesInFile(String file) {
        this.file = new File(file);
    }

    public LinesInFile(File file) {
        this.file = file;
    }

    /**
     * process each line with open file in special encoding
     */
    public void each(Consumer<String> consumer, String encoding) throws IOException {
        process(consumer, new InputStreamReader(new FileInputStream(file), encoding));
    }

    /**
     * process each line with open file in utf8 encoding
     */
    public void each(Consumer<String> consumer) throws IOException {
        process(consumer, new InputStreamReader(new FileInputStream(file), "utf8"));
    }

    private void process(Consumer<String> consumer, InputStreamReader isr) throws IOException {
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            consumer.accept(line);
        }
    }
}
