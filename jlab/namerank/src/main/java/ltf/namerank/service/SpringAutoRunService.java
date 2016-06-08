package ltf.namerank.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author ltf
 * @since 16/6/8, 上午11:03
 */
@Service
public class SpringAutoRunService {
    @PostConstruct
    public void autoRun() {
        System.out.println("dddd");

    }
}
