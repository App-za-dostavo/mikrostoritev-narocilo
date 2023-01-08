package si.fri.rso.narocilo.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Narocilo API", version = "v1",
        license = @License(name = "dev")),
        servers = @Server(url = "http://localhost:8080/"))
@ApplicationPath("/v1")
@CrossOrigin
public class OrderApplication extends Application {
}
