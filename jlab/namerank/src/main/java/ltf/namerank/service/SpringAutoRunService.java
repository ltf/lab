package ltf.namerank.service;

import ltf.namerank.HanziWuxing;
import ltf.namerank.dao.HanziDao;
import ltf.namerank.entity.Hanzi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author ltf
 * @since 16/6/8, 上午11:03
 */
@Service
public class SpringAutoRunService {

    @Autowired
    private HanziDao hanziDao;

    boolean brk = false;

    @PostConstruct
    public void autoRun() {
        Map<String, List<Hanzi>> data = HanziWuxing.testLoadDict();

        data.forEach((k, l) -> {
                    if (brk) return;

                    hanziDao.saveHanzi(l.get(0));

//                    if (l.size() > 1) {
//
//                        System.out.println(l.size());
//                        l.forEach(z -> System.out.println(z.getHtmid()));
//                        System.out.println(k);
//                    }
                    //hanziDao

                    brk = true;
                }
        );
    }
}
