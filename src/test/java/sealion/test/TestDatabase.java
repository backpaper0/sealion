package sealion.test;

import java.sql.Connection;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestDatabase implements TestRule {

    private Flyway flyway;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {

                //インメモリモードの場合、通常はコネクションを閉じるとDB自体も破棄されますが、
                //DB_CLOSE_DELAY=-1オプションを付けるとJVMが終了するまでDBも破棄されません。
                String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

                flyway = new Flyway();
                flyway.setDataSource(url, "sa", "secret");
                flyway.migrate();

                try {
                    base.evaluate();

                } finally {
                    DataSource dataSource = flyway.getDataSource();
                    try (Connection con = dataSource.getConnection();
                            java.sql.Statement st = con.createStatement()) {
                        st.execute("DROP ALL OBJECTS");
                    }
                }
            }
        };
    }

    public DataSource getDataSource() {
        return flyway.getDataSource();
    }
}
