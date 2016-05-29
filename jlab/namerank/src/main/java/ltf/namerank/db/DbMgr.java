package ltf.namerank.db;

import ltf.namerank.PathUtils;

import java.sql.*;

/**
 * @author ltf
 * @since 16/5/26, 上午10:03
 */
public class DbMgr {

    Connection conn;

    public static String getDbPath() {
        return PathUtils.getProjectPath() + "db/";
    }

    private DbMgr() {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + getDbPath() + "h2/h2db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() throws SQLException {
        Statement st = conn.createStatement();
        String sql = "create table if not exists dict_bm8 (" +
                "kword nvarchar(12) primary key," +
                "htmid nvarchar(12)," +
                "spell nvarchar(36)," +
                "traditional nvarchar(12)," +
                "strokes nvarchar(12)," +
                "wuxing nvarchar(12)," +
                "gorb nvarchar(12)," +
                "info nvarchar(65535)" +
                ");";

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
