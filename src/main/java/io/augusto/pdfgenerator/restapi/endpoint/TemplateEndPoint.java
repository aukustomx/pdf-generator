package io.augusto.pdfgenerator.restapi.endpoint;

import io.augusto.pdfgenerator.restapi.model.TemplateForm;
import io.augusto.pdfgenerator.restapi.service.TemplateApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Expone las capacidades de la aplicación a través de una capa de RESTful Web Services
 * específicamente, capacidades de administración de Plantillas xsl-fo
 * Created by j49u4r on 3/31/17.
 */
@Path("/template")
@Api(value = "template")
public class TemplateEndPoint {

    private static Logger logger = LogManager.getLogger();

    private static final String TEMPLATE_NAME_PARAM = "templateName";

    @Inject
    private TemplateApiService apiService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() throws PdfEngineException {
        logger.debug("Inicia ejecución operación all");
        return apiService.all();
    }

    @POST
    @Path("/")
    @ApiOperation(value = "Agregar plantilla al microservicio de generación de PDFs")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response add(@MultipartForm TemplateForm input) throws IOException, PdfEngineException {
        logger.debug("Inicia ejecución operación add");
        return apiService.add(input);

    }

    @DELETE
    @Path("/{templateName}")
    public Response delete(@PathParam(TEMPLATE_NAME_PARAM) String templateName) throws PdfEngineException {
        logger.debug("Inicia ejecución operación delete");
        return apiService.delete(templateName);
    }
}