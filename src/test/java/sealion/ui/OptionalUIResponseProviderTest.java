package sealion.ui;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class OptionalUIResponseProviderTest extends JerseyTest {

    @Test
    public void optional() throws Exception {
        Response response = target("test/1").request().get();
        assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo("x");
    }

    @Test
    public void empty() throws Exception {
        Response response = target("test/2").request().get();
        assertThat(response.getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(TestResource.class, UIResponseProvider.class,
                TestProvider.class);
    }

    @Path("test")
    public static class TestResource {

        @Path("1")
        @GET
        public Optional<UIResponse> get1() {
            return Optional.of(UIResponse.render("x"));
        }

        @Path("2")
        @GET
        public Optional<UIResponse> get2() {
            return Optional.empty();
        }
    }

    public static class TestProvider implements MessageBodyWriter<UIResponse> {

        @Override
        public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
                MediaType mediaType) {
            return true;
        }

        @Override
        public long getSize(UIResponse t, Class<?> type, Type genericType, Annotation[] annotations,
                MediaType mediaType) {
            return -1;
        }

        @Override
        public void writeTo(UIResponse t, Class<?> type, Type genericType, Annotation[] annotations,
                MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                OutputStream entityStream) throws IOException, WebApplicationException {
            entityStream.write(t.template.getBytes());
        }
    }
}
