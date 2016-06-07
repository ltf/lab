package ltf.namerank.db;

import java.sql.*;

import static ltf.namerank.utils.PathUtils.getDbHome;

/**
 * @author ltf
 * @since 16/5/26, 上午10:03
 */
public class DbMgr {

    public Connection getConn() {
        return conn;
    }

    Connection conn;

    private DbMgr() {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + getDbHome() + "/h2/h2db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() throws SQLException {
        Statement st = conn.createStatement();
        String sql = "create table if not exists dict_bm8 (" +
                "kword nvarchar(12)," +
                "htmid nvarchar(12)," +
                "spell nvarchar(36)," +
                "traditional nvarchar(12)," +
                "strokes nvarchar(12)," +
                "wuxing nvarchar(12)," +
                "luckyornot nvarchar(12)," +
                "comment nvarchar(12)," +
                "info nvarchar(65535)" +
                ");";

        st.execute(sql);

        sql = "create index if not exists dict_bm8_kword on dict_bm8 (kword);";

        st.execute(sql);

        st.close();
    }

    public static DbMgr instance() {
        return InstanceWrapper.getInstance();
    }

    private static class InstanceWrapper {
        private static final DbMgr instance = new DbMgr();

        static DbMgr getInstance() {
            return instance;
        }
    }
}
