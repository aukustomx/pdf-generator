package io.augusto.pdfgenerator.restapi.service;

import io.augusto.pdfgenerator.restapi.model.PdfGeneratorForm;
import io.augusto.pdfgenerator.domain.service.PdfGeneratorService;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

/**
 * Funciona como adapter entre el API rest y los servicio de dominio.
 * Transforma entradas del api a parámetros esperados por los servicios de dominio
 * y transforma salidas de los servicios de dominio a respuestas del API rest.
 * Created by j49u4r on 4/4/17.
 */
@Dependent
public class PdfGeneratorApiService {

    private static Logger logger = LogManager.getLogger();

    private PdfGeneratorService domainService;

    /**
     * Se construye el bean con el servicio de dominio como parámetro obligatorio.
     *
     * @param domainService Servicio de aplicación que atiende las solicitudes del API
     */
    @Inject
    public PdfGeneratorApiService(PdfGeneratorService domainService) {
        this.domainService = domainService;
    }

    /**
     * Funciona como adapter que extrae/transforma parámetros de entrada y construye la respuesta del API.
     *
     * @param input Parámetros de entrada al servicio.
     * @return Objeto PDF.
     */
    public Response generate(PdfGeneratorForm input) {

        logger.debug("TemplateName: {}  XmlContent: {}", () -> input.getTemplateName(), () -> input.getXmlContent());

        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(new ByteArrayInputStream(domainService.generatePdf(input.getTemplateName(),
                            input.getXmlContent())))
                    .type("application/pdf")
                    .header("Content-Disposition", "attachment; filename=\"generated.pdf\"")
                    .build();
        } catch (PdfEngineException e) {
            logger.debug("Error al generar el pdf", () -> e.getErrorCode(), () -> e.getMessage());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.toJsonString())
                    .build();
        }
    }
}
