package io.augusto.pdfgenerator.restapi.service;

import io.augusto.pdfgenerator.restapi.model.PdfGeneratorForm;
import io.augusto.pdfgenerator.domain.service.PdfGeneratorService;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by j49u4r on 4/5/17.
 */
public class PdfGeneratorApiServiceTest {

    public final Mockery context = new Mockery();


    /**
     * Prueba el mejor escenario para la generación del PDF.
     * @throws PdfEngineException En caso que suceda un error al generar el PDF.
     */
    @Test
    public void generateTest() throws PdfEngineException {

        PdfGeneratorService domainService = context.mock(PdfGeneratorService.class);
        PdfGeneratorApiService apiService = new PdfGeneratorApiService(domainService);

        PdfGeneratorForm input = new PdfGeneratorForm();
        input.setTemplateName("nomina");
        input.setXmlContent("<xml></xml>");

        context.checking(new Expectations() {{
            oneOf(domainService).generatePdf(input.getTemplateName(), input.getXmlContent());
            will(returnValue(new byte[]{}));
        }});

        assertEquals(Response.Status.OK, apiService.generate(input).getStatusInfo());
    }

    @Test
    public void generateFailedTest() throws PdfEngineException {

        PdfGeneratorService domainService = context.mock(PdfGeneratorService.class);
        PdfGeneratorApiService apiService = new PdfGeneratorApiService(domainService);

        PdfGeneratorForm input = new PdfGeneratorForm();
        input.setTemplateName("nomina");
        input.setXmlContent("<xml></xml>");

        context.checking(new Expectations() {{
            oneOf(domainService).generatePdf(input.getTemplateName(), input.getXmlContent());
            will(throwException(new PdfEngineException(PdfEngineError.PDFGEN_2000)));
        }});

        Response response = apiService.generate(input);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
        //assertEquals("\"PDFGEN_2000\"", ((JsonObject) response.getEntity()).get("Código").toString());

        context.checking(new Expectations() {{
            oneOf(domainService).generatePdf(input.getTemplateName(), input.getXmlContent());
            will(throwException(new PdfEngineException(PdfEngineError.PDFGEN_2004)));
        }});

        response = apiService.generate(input);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
        //assertEquals("\"PDFGEN_2004\"", ((JsonObject) response.getEntity()).get("Código").toString());
    }
}
