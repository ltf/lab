package ltf.namerank.dataprepare;

import com.hankcs.hanlp.HanLP;
import ltf.namerank.utils.LinesInFile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import static ltf.namerank.utils.FileUtils.file2Lines;
import static ltf.namerank.utils.FileUtils.lines2File;
import static ltf.namerank.utils.PathUtils.getNamesHome;
import static ltf.namerank.utils.PathUtils.getRawHome;


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
            //processDoubleFamilyNames();

            //cleanAllNames();
            //joinAllNames();

            cleanGivenNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeGivenNames() throws IOException {
        Set<String> ex = new HashSet<>();
        List<String> out = new ArrayList<>();
        List<String> in = new ArrayList<>();


        file2Lines(getNamesHome() + "/givenNames.txt", out);
        file2Lines(getRawHome() + "/givenNames.txt", in);
        ex.addAll(out);

        for (String name : in) {
            name = HanLP.convertToSimplifiedChinese(name);
            if (!ex.contains(name))
                out.add(name);
        }


        lines2File(out, getNamesHome() + "/givenNames.txt");
    }

    private void cleanGivenNames() throws IOException {
        Set<String> ex = new HashSet<>();
        List<String> out = new ArrayList<>();
        List<String> in = new ArrayList<>();


        file2Lines(getNamesHome() + "/givenNames.txt", out);
        file2Lines(getRawHome() + "/givenNames.txt", in);
        ex.addAll(out);

        for (String name : in) {
            name = HanLP.convertToSimplifiedChinese(name);
            if (!ex.contains(name))
                out.add(name);
        }


        lines2File(out, getNamesHome() + "/givenNames.txt");
    }

    private void joinAllNames() throws IOException {
        Set<String> names = new HashSet<>();
        Map<String, Integer> familyNames = new HashMap<>();
        Map<String, Integer> givenNames = new HashMap<>();
//        new LinesInFile(dfn("10w.txt.cleaned")).each(names::add);
//        new LinesInFile(dfn("260w.txt.cleaned")).each(names::add);
//        new LinesInFile(dfn("3k_female.txt.cleaned")).each(names::add);
//        new LinesInFile(dfn("3k_male.txt.cleaned")).each(names::add);
//        new LinesInFile(dfn("50w.txt.cleaned")).each(names::add);
        new LinesInFile(dfn("newNames.txt.cleaned")).each(names::add);

        System.out.println(String.format("names count: %d", names.size()));

        names.forEach(name -> {
            String familyName, givenName;
            if (isDoubleFamilyName(name)) {
                familyName = name.substring(0, 2);
                givenName = name.substring(2);
            } else {
                familyName = name.substring(0, 1);
                givenName = name.substring(1);
            }

            putOrInc(familyNames, familyName);
            putOrInc(givenNames, givenName);
        });
        System.out.println(String.format("familyNames count: %d", familyNames.size()));
        System.out.println(String.format("givenNames count: %d", givenNames.size()));

        List<String> list = new LinkedList<>();
        orderPrint(familyNames, list);
        Collections.reverse(list);
        lines2File(list, dfn("familyNames.txt"));

        orderPrint(givenNames, list);
        Collections.reverse(list);
        lines2File(list, dfn("givenNames.txt"));
    }

    private void putOrInc(Map<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }

    private void cleanAllNames() throws IOException {
//        cleanNames("gbk", dfn("10w.txt"));
//        cleanNames("gbk", dfn("260w.txt"));
//        cleanNames("gbk", dfn("3k_female.txt"));
//        cleanNames("gbk", dfn("3k_male.txt"));
//        cleanNames("utf8", dfn("50w.txt"));
        cleanNames("utf8", dfn("newNames.txt"));
    }

    private void cleanNames(String encoding, String fn) throws IOException {
        Map<String, Integer> namesCount = new HashMap<>();

        List<String> names = new LinkedList<>();

        new LinesInFile(fn).each(line -> {
            String name = line.trim().replaceAll("[^\\u3400-\\u9FFF]", "");

            if (namesCount.containsKey(name)) {
                namesCount.put(name, namesCount.get(name) + 1);
            } else {
                namesCount.put(name, 1);
                int len = name.length();
                if (len <= 3 && len >= 2) {
                    names.add(name.replaceAll(" ", ""));
                } else if (len == 4 && isDoubleFamilyName(name)) {
                    names.add(name);
                }
//                else {
//                    //System.out.println(line);
//                }
            }
        }, encoding);
        System.out.println(String.format("orig distinct count: %d", namesCount.size()));
        System.out.println(String.format("count after cleaned: %d", names.size()));
        lines2File(names, fn + ".cleaned");
    }

    private Set<String> doubleFamilyNames = null;

    private boolean isDoubleFamilyName(String name) {
        if (doubleFamilyNames == null) {
            try {
                initDoubleFamilyNames();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return doubleFamilyNames.contains(name.substring(0, name.length() < 2 ? 2 : name.length()));
    }

    private void initDoubleFamilyNames() throws IOException {
        doubleFamilyNames = new HashSet<>();
        new LinesInFile(dfn("doubleFamilyName.txt")).each(s -> {
            if (s != null && s.length() == 2) doubleFamilyNames.add(s);

        });
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

    private void orderPrint(Map<String, Integer> map) {
        orderPrint(map, null);
    }

    private void orderPrint(Map<String, Integer> map, Collection<String> collection) {
        List<String> numStrList = new LinkedList<>();
        DecimalFormat df = new DecimalFormat("000000000");
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
        return getRawHome() + "/" + filename;
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
//                    else {
//                        System.out.println(line);
//                    }
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


}
