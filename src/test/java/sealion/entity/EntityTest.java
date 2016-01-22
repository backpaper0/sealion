package sealion.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.builder.SelectBuilder;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;

import sealion.test.TestDatabase;

@RunWith(Parameterized.class)
public class EntityTest {

    @Parameter(0)
    public Class<?> entityClass;

    private static Config config;

    @ClassRule
    public static TestDatabase database = new TestDatabase();

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
        config = new Config() {

            final Dialect dialect = new H2Dialect();

            @Override
            public Dialect getDialect() {
                return dialect;
            }

            @Override
            public DataSource getDataSource() {
                return database.getDataSource();
            }
        };
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
