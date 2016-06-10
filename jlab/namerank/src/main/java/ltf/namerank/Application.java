package ltf.namerank;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ltf
 * @since 5/25/16, 10:39 PM
 */
public class Application {
    public static void main(String[] args) {

        new ClassPathXmlApplicationContext("/spring-config.xml");

//        new HanziWuxing().run();
//        try {
//            DbMgr.instance().init();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
