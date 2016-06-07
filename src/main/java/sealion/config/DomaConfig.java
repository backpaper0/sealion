package sealion.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;

@ApplicationScoped
@DataSourceDefinition(name = "java:app/jdbc/sealion", className = "org.h2.jdbcx.JdbcDataSource", url = "${db.url}", user = "sa", password = "secret")
public class DomaConfig implements Config {

    @Resource(name = "java:app/jdbc/sealion")
    private DataSource dataSource;
    private Dialect dialect;

    @PostConstruct
    public void init() {
        dialect = new H2Dialect();
    }

    public void migrate(@Observes @Initialized(ApplicationScoped.class) Object context) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }
}
