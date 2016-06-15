package ltf.namerank.lab;

import ltf.namerank.utils.FileUtils;
import ltf.namerank.utils.LinesInFile;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * @author ltf
 * @since 16/6/14, 下午3:13
 */
public class NameDataProcessor {

    public void go() {
//        try {
//            new LinesInFile("/Users/f/labfy/test1/data/260w.txt").each(line->{
//                System.out.println(line);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //process260w();

        try {
            processDoubleFamilyNames();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processDoubleFamilyNames() throws IOException {
        List<String> fnames = new ArrayList<>();
        Map<String, Integer> fnamesCount = new HashMap<>();
        new LinesInFile(dfn("doubleFamilyName.txt")).each(fnames::add);
        fnames.forEach(l -> fnamesCount.put(l, 0));
        countFamilyName(fnamesCount, "gbk", dfn("10w.txt"));
        countFamilyName(fnamesCount, "gbk", dfn("260w.txt"));
        countFamilyName(fnamesCount, "gbk", dfn("3k_female.txt"));
        countFamilyName(fnamesCount, "gbk", dfn("3k_male.txt"));
        countFamilyName(fnamesCount, "utf8", dfn("50w.txt"));
        List<String> orderedNames = new LinkedList<>();
        orderPrint(fnamesCount, orderedNames);
        Collections.reverse(orderedNames);
        lines2File(orderedNames, dfn("orderdDoubleFamilyNames.txt"));
    }

    private void countFamilyName(Map<String, Integer> countMap, String fileEncoding, String fn) throws IOException {
        new LinesInFile(fn).each(line -> {
            String n = line.trim();
            if (n.length() == 4) {
                String familyName = n.substring(0, 2);
                if (countMap.containsKey(familyName)) {
                    countMap.put(familyName, countMap.get(familyName) + 1);
                }
            }
        }, fileEncoding);
    }

    private void process260w() {
        Map<String, Integer> namesCount = new HashMap<>();
        Map<String, Integer> namesLen = new HashMap<>();

        List<String> names = new LinkedList<>();
        final String fn = dfn("260w.txt");
        try {
            new LinesInFile(fn).each(line -> {
                if (namesCount.containsKey(line)) {
                    namesCount.put(line, namesCount.get(line) + 1);
                } else {
                    namesCount.put(line, 1);
                    namesLen.put(line, line.length());
                    if (line.length() > 3 || line.length() < 2) {
                        System.out.println(line);
                    } else {
                        names.add(line.replaceAll(" ", ""));
                    }
                }
            }, "gbk");
            System.out.println(namesCount.size());
            System.out.println(namesLen.size());
            System.out.println(names.size());
            lines2File(names, fn + "_processed");
            //orderPrint(namesLen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process10w() {
        Map<String, Integer> namesCount = new HashMap<>();
        Map<String, Integer> namesLen = new HashMap<>();
        Set<String> familyNames = new TreeSet<>();
        Set<String> givenNames = new TreeSet<>();

        List<String> names = new LinkedList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dfn("10w.txt")), "gbk"));

            String line;
            while ((line = reader.readLine()) != null) {

                if (namesCount.containsKey(line)) {
                    namesCount.put(line, namesCount.get(line) + 1);
                } else {
                    namesCount.put(line, 1);
                    namesLen.put(line, line.length());
                    if (line.length() > 3 || line.length() < 2) {
                        //System.out.println(line);
                    } else {
                        names.add(line.replaceAll(" ", ""));
                    }
                }

            }


            System.out.println(namesCount.size());

            Collections.sort(names);
            System.out.println(names.size());

            lines2File(names, dfn("10w_cleaned.txt"));


            //orderPrint(namesCount);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void lines2File(Iterable<String> lines, String fn) throws IOException {
        StringBuilder sb = new StringBuilder();
        lines.forEach(line -> sb.append(line).append("\n"));
        FileUtils.str2File(fn, sb.toString());
    }

    private void orderPrint(Map<String, Integer> map) {
        orderPrint(map, null);
    }

    private void orderPrint(Map<String, Integer> map, Collection<String> collection) {
        List<String> numStrList = new LinkedList<>();
        DecimalFormat df = new DecimalFormat("0000");
        map.forEach((s, i) -> {
            //if (i > 1)
            numStrList.add(df.format(i) + " : " + s);
        });
        Collections.sort(numStrList);
        if (collection != null) for (String s : numStrList) collection.add(s);
        else for (String s : numStrList) System.out.println(s);
    }

    /**
     * return $data_path/filename
     */
    private String dfn(final String filename) {
        return "/Users/f/labfy/test1/data/" + filename;
    }


}
