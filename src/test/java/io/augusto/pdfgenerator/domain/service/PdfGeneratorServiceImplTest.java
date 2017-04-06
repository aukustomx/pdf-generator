package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.commons.io.IOUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by j49u4r on 3/23/17.
 */
public class PdfGeneratorServiceImplTest {

    public final Mockery context = new Mockery();

    @Test
    public void generatePdfTest() throws PdfEngineException {

        TemplateService templateService = context.mock(TemplateService.class);
        PdfGeneratorServiceImpl service = new PdfGeneratorServiceImpl(templateService);

        final String templateName = "nomina_generate_pdf";
        final String xmlSource = getFile("source.xml");

        Template template = new Template(templateName, getCompiledTemplate("template1.xsl"));

        context.checking(new Expectations() {{
            atLeast(1).of(templateService).byName(templateName);
            will(returnValue(template));
        }});

        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    try {
                        long init = System.currentTimeMillis();
                        assertNotNull(service.generatePdf(templateName, xmlSource));
                    } catch (PdfEngineException e) {
                        System.out.println(e.getStackTrace());
                    }
                });

        deleteFiles(".pdf", "/tmp/");
    }

    @Test
    public void generatePdfFailedTest() {

        TemplateService templateService = context.mock(TemplateService.class);
        PdfGeneratorServiceImpl service = new PdfGeneratorServiceImpl(templateService);

        try {
            service.generatePdf(null, null);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            Assert.assertEquals(PdfEngineError.PDFGEN_2001.getCode(), ex.getErrorCode());
        }

        try {
            service.generatePdf("not empty string", null);
        } catch (Exception e) {
            assertTrue(e instanceof PdfEngineException);
            PdfEngineException ex = (PdfEngineException) e;
            assertEquals(PdfEngineError.PDFGEN_2101.getCode(), ex.getErrorCode());
        }

    }

    /**
     * Genera un archivo de plantilla para simular la respuesta del servicio
     * de administración de plantillas.
     *
     * @param fileName Nombre del archivo que contiene la plantilla.
     * @return Plantilla compilada.
     */
    private Templates getCompiledTemplate(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        String template;
        try {
            template = getFile(fileName);
            ByteArrayInputStream input = new ByteArrayInputStream(template.getBytes(UTF_8));
            Source xslSrc = new StreamSource(input);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            return transformerFactory.newTemplates(xslSrc);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return null;
    }

    /**
     * Obtiene el contenido de un archivo como un String.
     *
     * @param fileName Nombre del archivo(recurso) a obtener.
     * @return String con el contenido del archivo.
     */
    private String getFile(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return "";
        }
    }

    /**
     * Utilidad que elimina todos los archivos con determinada extensión
     * en determinado directorio.
     *
     * @param extension    Extensión de los archivos a eliminar.
     * @param pathAsString Directorio de donde se eliminan los archivos.
     */
    private void deleteFiles(String extension, String pathAsString) {
        try {
            Files.list(Paths.get(pathAsString))
                    .map(Path::toString)
                    .filter(s -> s.endsWith(extension))
                    .forEach(s -> this.deleteFile(Paths.get(s)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un archivo, si existe, usando la ruta del mismo.
     *
     * @param path Ruta del archivo a eliminar.
     */
    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
