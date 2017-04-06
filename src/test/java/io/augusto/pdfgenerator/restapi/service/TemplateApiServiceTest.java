package io.augusto.pdfgenerator.restapi.service;

import io.augusto.pdfgenerator.restapi.model.TemplateForm;
import io.augusto.pdfgenerator.domain.service.TemplateService;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Prueba los escenarios del adapter del servicio de plantilla.
 * Created by j49u4r on 4/5/17.
 */
public class TemplateApiServiceTest {

    public final Mockery context = new Mockery();

    /**
     * Prueba el escenario en el que se solicita la lista de nombres
     * de plantillas registradas en la aplicación.
     */
    @Test
    public void allTest() {

        TemplateService domainService = context.mock(TemplateService.class);
        TemplateApiService apiService = new TemplateApiService(domainService);

        context.checking(new Expectations() {{
            atLeast(1).of(domainService).all();
            will(returnValue(new ArrayList<String>()));
        }});

        assertEquals(Response.Status.OK.getStatusCode(), apiService.all().getStatus());
        assertEquals(0, ((List<String>) apiService.all().getEntity()).size());
    }

    /**
     * Prueba el escenario en el que se agrega correctamente una
     * plantilla a la aplicación.
     *
     * @throws PdfEngineException Se lanza en caso de que suceda un error
     *                            al agregar una plantilla a la aplicación.
     */
    @Test
    public void addTest() throws PdfEngineException {

        TemplateService domainService = context.mock(TemplateService.class);
        TemplateApiService apiService = new TemplateApiService(domainService);

        TemplateForm input = new TemplateForm();
        input.setTemplateName("nomina");
        input.setTemplateContent("<xsl></xsl>");
        input.setPersistent(true);

        context.checking(new Expectations() {{
            oneOf(domainService).addAndPersist(input.getTemplateName(), input.getTemplateContent());
        }});


        assertEquals(Response.Status.OK.getStatusCode(), apiService.add(input).getStatus());
    }

    /**
     * Prueba el escenario en el que se agrega incorrectamente una
     * plantilla a la aplicación.
     *
     * @throws PdfEngineException Se lanza en caso de que suceda un error
     *                            al agregar una plantilla a la aplicación.
     */
    @Test
    public void addFailedTest() throws PdfEngineException {

        TemplateService domainService = context.mock(TemplateService.class);
        TemplateApiService apiService = new TemplateApiService(domainService);

        TemplateForm inputPersist = new TemplateForm();
        inputPersist.setTemplateName("nomina");
        inputPersist.setTemplateContent("<xsl></xsl>");
        inputPersist.setPersistent(true);

        context.checking(new Expectations() {{
            oneOf(domainService).addAndPersist(inputPersist.getTemplateName(), inputPersist.getTemplateContent());
            will(throwException(new PdfEngineException(PdfEngineError.PDFGEN_2007)));
        }});

        Response responsePersist = apiService.add(inputPersist);
        assertEquals("\"PDFGEN_2007\"", ((JsonObject) responsePersist.getEntity()).get("Código").toString());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, responsePersist.getStatusInfo());

        TemplateForm inputAdd = new TemplateForm();
        inputAdd.setTemplateName("cheques");
        inputAdd.setTemplateContent("<xsl></xsl>");
        inputAdd.setPersistent(false);

        context.checking(new Expectations() {{
            oneOf(domainService).add(inputAdd.getTemplateName(), inputAdd.getTemplateContent());
            will(throwException(new PdfEngineException(PdfEngineError.PDFGEN_2004)));
        }});

        Response responseAdd = apiService.add(inputAdd);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, responseAdd.getStatusInfo());
        assertEquals("\"PDFGEN_2004\"", ((JsonObject) responseAdd.getEntity()).get("Código").toString());
    }

    @Test
    public void deleteTest() throws PdfEngineException {

        TemplateService domainService = context.mock(TemplateService.class);
        TemplateApiService apiService = new TemplateApiService(domainService);

        final String templateName = "nomina";

        context.checking(new Expectations() {{
            oneOf(domainService).delete(templateName);
        }});

        Response response = apiService.delete(templateName);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    /**
     * Prueba el caso en el que el servicio de dominio regresa una exception de tipo
     * PdfEngineException.
     * @throws PdfEngineException Cuando falla algo al agregar una plantilla.
     */
    @Test
    public void deleteFailedTest() throws PdfEngineException {

        TemplateService domainService = context.mock(TemplateService.class);
        TemplateApiService apiService = new TemplateApiService(domainService);

        final String templateName = "nomina";

        context.checking(new Expectations() {{
            oneOf(domainService).delete(templateName);
            will(throwException(new PdfEngineException(PdfEngineError.PDFGEN_2004)));
        }});

        Response response = apiService.delete(templateName);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
        assertEquals("\"PDFGEN_2004\"", ((JsonObject) response.getEntity()).get("Código").toString());

        context.checking(new Expectations(){{
            oneOf(domainService).delete(templateName);
            will(throwException(new PdfEngineException(PdfEngineError.PDFGEN_2007)));
        }});

        response = apiService.delete(templateName);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
        assertEquals("\"PDFGEN_2007\"", ((JsonObject) response.getEntity()).get("Código").toString());
    }
}
