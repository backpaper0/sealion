package sealion;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import sealion.config.DomaConfig;
import sealion.test.TestDatabase;

@RunWith(Parameterized.class)
public class SqlTest {

    @Parameter(0)
    public Path sqlFile;

    private static Path root;

    @ClassRule
    public static TestDatabase database = new TestDatabase();

    @Test
    public void executeSqlFile() throws Exception {
        String sql = new String(Files.readAllBytes(root.resolve(sqlFile)), StandardCharsets.UTF_8);
        DataSource dataSource = database.getDataSource();
        try (Connection con = dataSource.getConnection(); Statement st = con.createStatement()) {
            st.execute(sql);
        }
    }

    @Parameters(name = "{0}")
    public static List<Path> parameters() throws Exception {

        //DomaConfigクラスを元にしてクラスパスを取得します。
        //ここで取得したクラスパスをトラバースしてSQLファイルを集めます。
        ProtectionDomain protectionDomain = DomaConfig.class.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URL location = codeSource.getLocation();
        root = Paths.get(location.toURI());

        return Files.walk(root)
                //rootからの相対パスに変換する
                .map(a -> root.relativize(a))
                //DomaのSQLファイルはMETA-INFディレクトリ以下に置く
                .filter(a -> a.startsWith("META-INF"))
                .filter(a -> a.getFileName().toString().endsWith(".sql"))
                .collect(Collectors.toList());
    }
}
