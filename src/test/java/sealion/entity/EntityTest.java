package sealion.entity;

import static org.assertj.core.api.Assertions.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.builder.SelectBuilder;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;

@RunWith(Parameterized.class)
public class EntityTest {

    @Parameter(0)
    public Class<?> entityClass;

    private static Config config;
    private static Flyway flyway;

    @Test
    public void tableMappingToEntity() throws Exception {
        //SELECTした結果をエンティティにマッピングできるかテストします。
        //テーブル定義の変更にエンティティを追随するために使用します。
        List<?> entities = SelectBuilder.newInstance(config)
                .sql("SELECT /*%expand*/* FROM " + entityClass.getSimpleName())
                .getEntityResultList(entityClass);
        assertThat(entities.size()).isNotEqualTo(0);
    }

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        //インメモリモードの場合、通常はコネクションを閉じるとDB自体も破棄されますが、
        //DB_CLOSE_DELAY=-1オプションを付けるとJVMが終了するまでDBも破棄されません。
        String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

        flyway = new Flyway();
        flyway.setDataSource(url, "sa", "secret");
        flyway.migrate();

        Dialect dialect = new H2Dialect();
        config = new Config() {

            @Override
            public Dialect getDialect() {
                return dialect;
            }

            @Override
            public DataSource getDataSource() {
                return flyway.getDataSource();
            }
        };
    }

    @AfterClass
    public static void dropDatabase() throws Exception {
        DataSource dataSource = flyway.getDataSource();
        try (Connection con = dataSource.getConnection(); Statement st = con.createStatement()) {
            st.execute("DROP ALL OBJECTS");
        }
    }

    @Parameters(name = "{0}")
    public static List<Class<?>> parameters() throws Exception {
        List<Class<?>> entityClasses = new ArrayList<>();
        entityClasses.add(Account.class);
        entityClasses.add(Assignment.class);
        entityClasses.add(Comment.class);
        entityClasses.add(Grant.class);
        entityClasses.add(Milestone.class);
        entityClasses.add(Password.class);
        entityClasses.add(Project.class);
        entityClasses.add(Task.class);
        return entityClasses;
    }
}
