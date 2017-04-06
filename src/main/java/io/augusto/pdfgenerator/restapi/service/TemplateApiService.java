package io.augusto.pdfgenerator.restapi.service;

import io.augusto.pdfgenerator.restapi.model.TemplateForm;
import io.augusto.pdfgenerator.domain.service.TemplateService;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 * Funciona como el adapter entre el API rest y el servicio de dominio.
 * Se encarga de extraer del request los parámetros que el servicio de dominio
 * necesita y de convertir la respuesta de este último a una que se especifica
 * en el API.
 * Created by j49u4r on 4/4/17.
 */
@Dependent
public class TemplateApiService {

    private static Logger logger = LogManager.getLogger();

    private TemplateService domainService;

    @Inject
    public TemplateApiService(TemplateService domainService) {
        this.domainService = domainService;
    }

    /**
     * Transforma la respuesta del servicio de dominio a una
     * de la establecida por el API rest.
     *
     * @return Respuesta con todos los nombres de plantillas registradas
     * en la aplicación.
     */
    public Response all() {
        return Response
                .status(Response.Status.OK)
                .entity(domainService.all())
                .build();
    }

    /**
     * Transforma los parámetros de entrada en el multiformdata a los esperados
     * por el servicio de dominio.
     * Además, transforma la respuesta del servicio de dominio a una
     * de la establecida por el API rest.
     *
     * @param input Parámetros de entrada.
     * @return Respuesta de exito o error.
     */
    public Response add(@MultipartForm TemplateForm input) {

        try {
            if (input.isPersistent()) {
                domainService.addAndPersist(input.getTemplateName(), input.getTemplateContent());
            } else {
                domainService.add(input.getTemplateName(), input.getTemplateContent());
            }
        } catch (PdfEngineException e) {
            logger.debug("Error al agregar la plantilla " + input.getTemplateName());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.toJson())
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity("Plantilla agregada correctamente")
                .build();
    }

    /**
     * Transforma el parámetro de entrada a uno esperado por el servicio
     * y construye una respuesta como la especificada en el API rest.
     *
     * @param templateName Nombre de la plantilla a eliminar.
     * @return Respuesta de exito o error.
     */
    public Response delete(String templateName) {
        try {
            domainService.delete(templateName);
        } catch (PdfEngineException e) {
            logger.debug("Error al eliminar la plantilla " + templateName);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.toJson())
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity("Plantilla eliminada correctamente")
                .build();
    }
}
