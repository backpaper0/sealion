package sealion.ui;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityResponseHeaders implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        //生成元が異なる場合frame要素やiframe要素の内部に表示しない
        headers.add("X-Frame-Options", "SAMEORIGIN");

        //Content-Typeの自動判別防止
        headers.add("X-Content-Type-Options", "nosniff");

        //XSSフィルターを有効化する
        headers.add("X-XSS-Protection", "1; mode=block");

        //リソースの取得元を生成元のみに制限する
        //画像は生成元に加えてgravatarも許可する
        headers.add("Content-Security-Policy",
                "default-src 'self'; img-src 'self' www.gravatar.com");
    }
}
