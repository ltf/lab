package ltf.bazi.db;

import java.io.File;
import java.sql.*;
import java.util.Observable;
import java.util.StringTokenizer;

/**
 * @author ltf
 * @since 16/5/26, 上午10:03
 */
public class DbMgr {

    Connection connH2;
    Connection connHsql;

    private DbMgr() {
        try {
            connHsql = DriverManager.getConnection("jdbc:hsqldb:file:/Users/f/plab/jlab/bazi/db/hsql/hdb");
            connH2 = DriverManager.getConnection("jdbc:h2:/Users/f/plab/jlab/bazi/db/h2/h2db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void init() throws SQLException {
        Statement stHsql = connHsql.createStatement();
        Statement stH2 = connH2.createStatement();
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

    public void benchmarkTest() throws SQLException {
        String dir = "/Users/f/plab/jlab/bazi/db/benchmark/";
        //new File(dir).delete();

        benchmark("h2", "jdbc:h2:" + dir + "h2/h2db");
//        benchmark("hs", "jdbc:hsqldb:file:" + dir + "hsql/hdb");
//        benchmark("db", "jdbc:derby:" + dir + "db/derby;create=true");
    }

    private interface BenchRunnable {
        void run() throws SQLException;
    }

    private void benchmark(final String dbType, final String connStr) throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection(connStr);
        final Statement st = conn.createStatement();

        step(dbType, "createTable", new BenchRunnable() {
            @Override
            public void run() throws SQLException {
                st.execute("CREATE TABLE TEST(" +
                        "ID VARCHAR(255) PRIMARY KEY, " +
                        "NAME VARCHAR(255))");
            }
        });

        step(dbType, "insertItems", new BenchRunnable() {
            @Override
            public void run() throws SQLException {
                for (int i = 0; i < 500000; i++) {
                    st.execute("INSERT INTO TEST(ID,NAME) VALUES('" + i + "','" + i + "')");
                }
            }
        });

        step(dbType, "selectKey", new BenchRunnable() {
            @Override
            public void run() throws SQLException {
                for (int i = 0; i < 50; i++) {
                    st.execute("select ID,NAME from TEST where ID like '%" + i + "%'");
                }
            }
        });

        step(dbType, "selectNotKey", new BenchRunnable() {
            @Override
            public void run() throws SQLException {
                for (int i = 0; i < 50; i++) {
                    st.execute("select ID,NAME from TEST where NAME like '%" + i + "%';");
                }
            }
        });

        st.close();
        conn.close();
    }

    private void step(final String dbType, final String stepName, BenchRunnable step) {
        try {
            long start, end;
            start = System.currentTimeMillis();
            step.run();
            end = System.currentTimeMillis();
            System.out.println(String.format("%s - %s: %d", dbType, stepName, end - start));
        } catch (SQLException e) {
            System.out.println(String.format("%s - %s: failed, %s", dbType, stepName, e.getMessage()));
        }
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
