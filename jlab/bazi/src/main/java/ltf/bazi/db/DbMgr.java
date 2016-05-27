package ltf.bazi.db;

import java.sql.*;

/**
 * @author ltf
 * @since 16/5/26, 上午10:03
 */
public class DbMgr {

    Connection conn;
    Connection connHsql;

    private DbMgr() {
        try {
            conn = DriverManager.getConnection("jdbc:h2:/Users/f/plab/jlab/bazi/db/h2/h2db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void init() throws SQLException {
        Statement stHsql = connHsql.createStatement();
        Statement stH2 = conn.createStatement();
        String sql = "create table dict_bm8 (" +
                "kword nvarchar(12) primary key," +
                "htmid nvarchar(12)," +
                "spell nvarchar(36)," +
                "traditional nvarchar(12)," +
                "strokes nvarchar(12)," +
                "wuxing nvarchar(12)," +
                "gorb nvarchar(12)," +
                "info nvarchar(65535)" +
                ");";
        sql = "CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));";
        //System.out.println(stHsql.execute(sql));
        //System.out.println(stH2.execute(sql));
        long hq_start = System.currentTimeMillis();
        for (int i = 1000; i < 1000000; i++) {
            sql = "INSERT INTO TEST(ID,NAME) VALUES(" + i + ",'" + i + "');";
        }
        long hq_end = System.currentTimeMillis();

        long h2_start = System.currentTimeMillis();
        for (int i = 1000; i < 1000000; i++) {
            sql = "INSERT INTO TEST(ID,NAME) VALUES(" + i + ",'" + i + "');";
        }
        long h2_end = System.currentTimeMillis();

        System.out.println(String.format("insert  hq: %d; h2: %d", hq_end - h2_start, h2_end - h2_start));

        stHsql.close();
        stH2.close();
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
