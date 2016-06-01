package ltf.namerank;

import ltf.namerank.db.DbMgr;

import java.sql.SQLException;

/**
 * @author ltf
 * @since 5/25/16, 10:39 PM
 */
public class Runner {
    public static void main(String[] args) {
        //new HanziWuxing().run();
        try {
            DbMgr.instance().init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
