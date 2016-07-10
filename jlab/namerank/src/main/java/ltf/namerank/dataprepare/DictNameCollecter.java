package ltf.namerank.dataprepare;

import com.parser.Baidu.BaiduBdcitReader;
import com.parser.QQ.QQqpydReader;
import com.parser.Sogou.SougouScelReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ltf.namerank.utils.FileUtils.lines2File;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 7/11/16, 12:59 AM
 */
public class DictNameCollecter {


    public DictNameCollecter() {
        collect();
    }


    private void collect() {
        List<String> list = new ArrayList<>();
        for (File f : new File(getRawHome() + "/dicts/names").listFiles()) {
            if (!f.isFile()) continue;

            file2Strs(f, list);
        }

        try {
            lines2File(list, getRawHome()+"/newNames.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void file2Strs(File f, Collection<String> strs) {
        try {
            if (f.getName().endsWith(".bdict")) {
                strs.addAll(BaiduBdcitReader.readBdictFile(f.getAbsolutePath()));
            } else if (f.getName().endsWith(".qpyd")) {
                strs.addAll(QQqpydReader.readQpydFile(f.getAbsolutePath()));
            } else if (f.getName().endsWith(".scel")) {
                strs.addAll(SougouScelReader.readDict(f.getAbsolutePath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
