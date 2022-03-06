package ltf.namerank.dataprepare;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static ltf.namerank.utils.FileUtils.fromLinesData;
import static ltf.namerank.utils.FileUtils.toLinesData;
import static ltf.namerank.utils.PathUtils.getLinesHome;

/**
 * @author ltf
 * @since 16/7/12, 下午1:42
 */
public class WuxingDataCollect {


    private void cleanCharWuxingdata() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        fromLinesData("wuxing_huo", list);
        list.forEach(l -> {
            for (char c : l.toCharArray()) {
                if ("\r".equals(c) || "\t".equals(c) || " ".equals(c)) continue;
                set.add(String.valueOf(c));
            }
        });
        list.clear();
        list.addAll(set);
        toLinesData(list, "wuxing_huo2");
    }

    private void doStep2() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        set.addAll(fromLinesData("givenNames"));

        fromLinesData("ranking2").forEach(l -> {
            if (set.contains(l.substring(0, 2))) {
                list.add(l);
            }
        });
        toLinesData(list, "ranking2_step2");
    }

    private void doStep2_2() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        HashSet<String> girlSet = new HashSet<>();
        set.addAll(fromLinesData("givenNames"));

        fromLinesData("Chinese_Names").forEach(l -> {
            if (l.indexOf(',') == 3) {
                String name = l.substring(1, 3);
                if ("女".equals(l.substring(4, 5))) {
                    girlSet.add(name);
                } else {
                    set.add(name);
                }
            }
        });

        fromLinesData("ranking3").forEach(l -> {
            girlSet.add(l.substring(0, 2));
        });


        fromLinesData("ranking6").forEach(l -> {
            String name = l.substring(0, 2);
            if (set.contains(name) && !girlSet.contains(name)) {
                list.add(l);
            }
        });
        toLinesData(list, "ranking6_step2");
    }

    public static List<String> genCandidates() throws IOException {

        HashSet<String> girlSet = new HashSet<>();
        HashSet<String> set = new HashSet<>(fromLinesData("givenNames"));

        fromLinesData("Chinese_Names").forEach(l -> {
            if (l.indexOf(',') == 3) {
                String name = l.substring(1, 3);
                if ("女".equals(l.substring(4, 5))) {
                    girlSet.add(name);
                } else {
                    set.add(name);
                }
            }
        });

        girlSet.forEach(set::remove);


        fromLinesData("ranking6").forEach(l -> {
            String name = l.substring(0, 2);
            set.remove(name);
        });

        return new ArrayList<>(set);
    }

    private void makeFayin() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        fromLinesData("picked").forEach(l -> {
            list.add("李" + l.substring(0, 2) + ",");
        });

        for (int i = 0; i < list.size() / 2000+1; i++) {
            ArrayList<String> tl = new ArrayList<>();
            for (int x = 2000 * i; x < (Math.min((2000 * i + 2000), list.size())); x++) {
                tl.add(list.get(x));
            }

            toLinesDataAnsi(tl, "fyg" + Integer.toString(i));
        }
    }

    public static void toLinesDataAnsi(Iterable<String> lines, String shortFileName) throws IOException {
        lines2FileAnsi(lines, getLinesHome() + "/" + shortFileName + ".txt");
    }

    public static void str2FileAnsi(String content, File f) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "GBK"));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    public static void lines2FileAnsi(Iterable<String> lines, String fn) throws IOException {
        StringBuilder sb = new StringBuilder();
        lines.forEach(line -> sb.append(line).append("\n"));
        str2FileAnsi(sb.toString(), fn);
    }

    public static void str2FileAnsi(String content, String fn) throws IOException {
        str2FileAnsi(content, new File(fn));
    }

    public static void main(String[] args) throws IOException {
        //new WuxingDataCollect().cleanCharWuxingdata();
        //new WuxingDataCollect().doStep2_2();
        new WuxingDataCollect().makeFayin();
    }


}
