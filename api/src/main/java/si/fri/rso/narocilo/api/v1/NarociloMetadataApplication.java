package si.fri.rso.narocilo.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Narocilo API", version = "v1",
        license = @License(name = "dev"), description = "API for managing narocilo metadata."),
        servers = @Server(url = "http://localhost:8080/"))
@ApplicationPath("/v1")
public class NarociloMetadataApplication extends Application {

}
