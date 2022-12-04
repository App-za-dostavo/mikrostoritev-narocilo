package si.fri.rso.narocilo.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.narocilo.lib.NarociloMetadata;
import si.fri.rso.narocilo.services.beans.NarociloMetadataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;



@ApplicationScoped
@Path("/narocilo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NarociloMetadataResource {

    private Logger log = Logger.getLogger(NarociloMetadataResource.class.getName());

    @Inject
    private NarociloMetadataBean narociloMetadataBean;


    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all narocilo metadata.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of narocilo metadata",
                    content = @Content(schema = @Schema(implementation = NarociloMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getNarociloMetadata() {

        List<NarociloMetadata> narociloMetadata = narociloMetadataBean.getNarociloMetadataFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(narociloMetadata).build();
    }


    @Operation(description = "Get metadata for 1 narocilo.", summary = "Get metadata for 1 narocilo")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Narocilo metadata",
                    content = @Content(
                            schema = @Schema(implementation = NarociloMetadata.class))
            )})
    @GET
    @Path("/{narociloMetadataId}")
    public Response getNarociloMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("narociloMetadataId") Integer narociloMetadataId) {

        NarociloMetadata narociloMetadata = narociloMetadataBean.getNarociloMetadata(narociloMetadataId);

        if (narociloMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(narociloMetadata).build();
    }

    @Operation(description = "Add narocilo metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createNarociloMetadata(@RequestBody(
            description = "DTO object with narocilo metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = NarociloMetadata.class))) NarociloMetadata narociloMetadata) {

        if ((narociloMetadata.getClient() == null || narociloMetadata.getProvider() == null || narociloMetadata.getContents() == null || narociloMetadata.getCost() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            narociloMetadata = narociloMetadataBean.createNarociloMetadata(narociloMetadata);
        }

        return Response.status(Response.Status.CONFLICT).entity(narociloMetadata).build();

    }


    @Operation(description = "Update metadata for 1 narocilo.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{narociloMetadataId}")
    public Response putNarociloMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("narociloMetadataId") Integer narociloMetadataId,
                                     @RequestBody(
                                             description = "DTO object with narocilo metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = NarociloMetadata.class)))
                                             NarociloMetadata narociloMetadata){

        narociloMetadata = narociloMetadataBean.putNarociloMetadata(narociloMetadataId, narociloMetadata);

        if (narociloMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete metadata for 1 narocilo.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{narociloMetadataId}")
    public Response deleteNarociloMetadata(@Parameter(description = "Metadata ID.", required = true)
                                        @PathParam("narociloMetadataId") Integer narociloMetadataId){

        boolean deleted = narociloMetadataBean.deleteNarociloMetadata(narociloMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}