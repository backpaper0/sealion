package sealion.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;

@ApplicationScoped
public class DomaConfig implements Config {

    @Resource(name = "java:app/jdbc/sealion")
    private DataSource dataSource;
    private Dialect dialect;

    @PostConstruct
    public void init() {
        dialect = new H2Dialect();
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
