package sealion.entity;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.seasar.doma.Entity;
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

        Path classpathRoot = Paths
                .get(Task.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        //テーブルに1対1の関係でマッピングされているエンティティクラスが
        //格納されているディレクトリを検出しています。
        Path entityDir = classpathRoot.resolve(Task.class.getPackage().getName().replace('.', '/'));

        Function<Path, String> toFQCN = file -> {
            String s = file.toString();
            s = s.substring(0, s.length() - ".class".length());
            s = s.replace('/', '.');
            return s;
        };

        Function<String, Optional<Class<?>>> tryToClass = className -> {
            try {
                return Optional.of(Class.forName(className));
            } catch (ClassNotFoundException e) {
                return Optional.empty();
            }
        };

        Predicate<Class<?>> isEntity = clazz -> clazz.isAnnotationPresent(Entity.class);

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(entityDir)) {

            //IteratorをもとにしてStreamを構築
            Stream<Path> stream = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(ds.iterator(), 0), false);

            List<Class<?>> entityClasses = stream.map(classpathRoot::relativize).map(toFQCN)
                    .map(tryToClass).flatMap(a -> a.map(Stream::of).orElseGet(Stream::empty))
                    .filter(isEntity).collect(Collectors.toList());

            return entityClasses;
        }
    }
}
