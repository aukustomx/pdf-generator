package io.augusto.pdfgenerator.restapi.endpoint;

import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import io.augusto.pdfgenerator.restapi.model.PdfGeneratorForm;
import io.augusto.pdfgenerator.restapi.service.PdfGeneratorApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Expone las capacidades de la aplicación para la generación de PDFs.
 * Created by j49u4r on 4/4/17.
 */
@Path("/pdfgenerator")
public class PdfGeneratorEndPoint {

    private static Logger logger = LogManager.getLogger();

    @Inject
    private PdfGeneratorApiService apiService;

    @POST
    @Path("/generate")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/pdf")
    public Response generate(@MultipartForm PdfGeneratorForm input) throws IOException, PdfEngineException {
        logger.debug("Inicia ejecución operación generate");
        return apiService.generate(input);
    }
}
