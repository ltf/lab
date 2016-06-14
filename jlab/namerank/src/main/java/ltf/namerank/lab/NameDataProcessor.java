package ltf.namerank.lab;

import ltf.namerank.utils.FileUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author ltf
 * @since 16/6/14, 下午3:13
 */
public class NameDataProcessor {

    public void go() {
        Map<String, Integer> namesCount = new HashMap<>();
        Map<String, Integer> namesLen = new HashMap<>();
        Set<String> familyNames = new TreeSet<>();
        Set<String> givenNames = new TreeSet<>();

        List<String> names = new LinkedList<>();

        StringBuilder sb =new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/f/labfy/test1/data/10w.txt"), "gbk"));

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
                        names.add(line.replaceAll(" ",""));
                    }
                }

            }


            System.out.println(namesCount.size());

            Collections.sort(names);
            System.out.println(names.size());
            for(String name:names){
                sb.append(name).append("\n");
            }

            FileUtils.str2File("/Users/f/labfy/test1/data/10w_cleaned.txt", sb.toString());


            //orderPrint(namesCount);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void orderPrint(Map<String, Integer> map) {
        List<String> numStrList = new LinkedList<>();
        DecimalFormat df = new DecimalFormat("0000");
        map.forEach((s, i) -> {
            if (i > 1) numStrList.add(df.format(i) + " : " + s);
        });
        Collections.sort(numStrList);
        for (String s : numStrList) System.out.println(s);
    }


}
