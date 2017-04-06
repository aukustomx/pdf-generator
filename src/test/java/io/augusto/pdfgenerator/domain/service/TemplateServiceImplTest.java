package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import io.augusto.pdfgenerator.repository.TemplateRepository;
import org.apache.commons.io.IOUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by j49u4r on 3/30/17.
 */
public class TemplateServiceImplTest {

    public final Mockery context = new Mockery();

    @Test
    public void addTest() throws PdfEngineException {

        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        final String templateName = "nomina_add";
        final String templateContent = getFile("template1.xsl");

        context.checking(new Expectations(){{
            oneOf(repository).existTemplate(templateName);
            will(returnValue(false));
        }});

        context.checking(new Expectations() {{
            oneOf(repository).add(templateName, templateContent);
        }});

        service.add(templateName, templateContent);
    }

    @Test
    public void addNullOrEmptyTemplateNameContentTest() {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        try {
            service.add(null, "");
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            Assert.assertEquals(PdfEngineError.PDFGEN_2001.getCode(), ex.getErrorCode());
        }

        try {
            service.add("nomina_addnull", null);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2002.getCode(), ex.getErrorCode());
        }

        String templateName = "nomina_addnull";
        String templateContent = getFile("template1.xsl");

        context.checking(new Expectations() {{
            oneOf(repository).existTemplate(templateName);
            will(returnValue(true));
        }});

        try {
            service.add(templateName, templateContent);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2003.getCode(), ex.getErrorCode());
        }
    }

    @Test
    public void addAndPersistTest() throws PdfEngineException {

        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        final String templateName = "nomina_add";
        final String templateContent = getFile("template1.xsl");

        context.checking(new Expectations() {{
            oneOf(repository).existTemplate(templateName);
            will(returnValue(false));
            oneOf(repository).add(templateName, templateContent);
            oneOf(repository).persist(templateName, templateContent);
        }});

        service.addAndPersist(templateName, templateContent);
    }

    @Test
    public void addAndPersistNullOrEmptyTest() {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        try {
            service.addAndPersist(null, "");
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2001.getCode(), ex.getErrorCode());
        }

        try {
            service.addAndPersist("nomina_addnull", null);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2002.getCode(), ex.getErrorCode());
        }

        String templateName = "nomina_addnull";
        String templateContent = getFile("template1.xsl");

        context.checking(new Expectations() {{
            oneOf(repository).existTemplate(templateName);
            will(returnValue(true));
        }});

        try {
            service.addAndPersist(templateName, templateContent);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2003.getCode(), ex.getErrorCode());
        }
    }

    @Test
    public void deleteTest() throws PdfEngineException {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        final String templateName = "nomina_delete";

        context.checking(new Expectations() {{
            oneOf(repository).existTemplate(templateName);
            will(returnValue(true));
            oneOf(repository).delete(templateName);
        }});

        service.delete(templateName);
    }

    @Test
    public void deleteNullOrEmptyTemplateName() {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        try {
            service.delete(null);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2001.getCode(), ex.getErrorCode());
        }
    }

    @Test
    public void deleteNonExistingTemplate() {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        final String templateName = "nomina_non_existing";

        context.checking(new Expectations() {{
            oneOf(repository).existTemplate(templateName);
            will(returnValue(false));
        }});

        try {
            service.delete(templateName);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2004.getCode(), ex.getErrorCode());
        }
    }

    @Test
    public void allTest() throws PdfEngineException {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        List<String> templatesName = Arrays.asList("nomina", "cheque", "inversion");

        context.checking(new Expectations() {{
            oneOf(repository).all();
            will(returnValue(templatesName));
        }});

        assertEquals(templatesName, service.all());
    }

    @Test
    public void byNameTest() throws PdfEngineException {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        final String templateName = "nomina_by_name";
        final String templateContent = getFile("template1.xsl");

        context.checking(new Expectations() {{

            oneOf(repository).existTemplate(with(any(String.class)));
            will(returnValue(true));
            oneOf(repository).byName(with(any(String.class)));
            will(returnValue(new Template(templateName, null)));
        }});

        Assert.assertEquals(templateName, service.byName(templateName).getName());
    }

    @Test
    public void byNameFailedTest() {
        TemplateRepository repository = context.mock(TemplateRepository.class);
        TemplateServiceImpl service = new TemplateServiceImpl(repository);

        try {
            service.byName("");
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2001.getCode(), ex.getErrorCode());
        }

        context.checking(new Expectations() {{
            oneOf(repository).existTemplate(with(any(String.class)));
            will(returnValue(false));
        }});

        try {
            service.byName("nomina");
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2004.getCode(), ex.getErrorCode());
        }
    }

    /**
     * Utilidad para recuperar el contenido de un archivo como un tring.
     * @param fileName Nombre del archivo dentro de resources.
     * @return String con el contenido del archivo.
     */
    private String getFile(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return "";
        }
    }
}
