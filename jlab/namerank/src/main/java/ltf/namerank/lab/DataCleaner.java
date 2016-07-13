package ltf.namerank.lab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ltf.namerank.utils.FileUtils.file2Lines;
import static ltf.namerank.utils.FileUtils.lines2File;
import static ltf.namerank.utils.PathUtils.getWordsHome;

/**
 * @author ltf
 * @since 16/7/13, 下午1:58
 */
public class DataCleaner {
    public DataCleaner() {
        try {
            clean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clean() throws IOException {

        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        file2Lines(getWordsHome() + "/positive.txt", list);
        boolean skipNextChar = false;
        for (int i = list.size() - 1; i >= 0; i--) {
            String s = list.get(i);
            System.out.print(s + "    ");
            if (skipNextChar) {
                System.in.read();
                skipNextChar = false;
            }
            char c = (char) System.in.read();
            if (c == '0' || c == ' ') {
                System.out.println("\t\t\t\t\t\tdel");
                list.remove(i);
                skipNextChar = true;
            } else {
                System.out.println("\t\t\t\tkeep");
            }
            lines2File(list, getWordsHome() + "/positive_selected.txt");
        }
    }

}
