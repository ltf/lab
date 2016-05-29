package ltf.namerank.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static ltf.namerank.PathUtils.getDbPath;

/**
 * @author ltf
 * @since 16/5/27, 上午11:13
 */
public class DbBenchmark {

    public void benchmarkTest() throws SQLException {
        String dir = getDbPath() + "benchmark/";
        new File(dir).delete();

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
}
