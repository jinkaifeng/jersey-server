package io.neocdtv.jetty.base.boundary;

import io.neocdtv.jetty.base.Constants;
import io.neocdtv.jetty.base.control.ExampleControl;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * ExampleResource
 *
 * @author xix
 * @since 22.12.2017.
 */
@Path(Constants.PATH_RESOURCE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExampleResource {

  @Inject
  private ExampleControl exampleControl;

  @GET
  public Response exampleRestMethod() {
    return Response.ok(exampleControl.businessMethod()).build();
  }
}
