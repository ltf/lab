package ltf.namerank.service;

import ltf.namerank.HanziWuxing;
import ltf.namerank.entity.Hanzi;
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

    @PostConstruct
    public void autoRun() {
        Map<String, List<Hanzi>> data = HanziWuxing.testLoadDict();

        data.forEach((k, l) -> {
                    if (l.size() > 1) {

                        System.out.println(k);
                        System.out.println(l.size());
                        l.forEach(z -> System.out.println(z.getHtmid()));
                        for (byte b : k.getBytes()) {
                            System.out.print("=" +b +"=");
                        }
                    }
                }
        );
    }
}
