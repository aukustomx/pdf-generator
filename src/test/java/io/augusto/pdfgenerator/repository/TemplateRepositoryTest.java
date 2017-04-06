package io.augusto.pdfgenerator.repository;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.commons.io.IOUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Escenarios de prueba del componente de administración de plantillas.
 * Created by j49u4r on 3/22/17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplateRepositoryTest {

    private static final String PERSISTED_TEMPLATES_DIRECTORY = "/data/templates/";

    /**
     * Prueba el caso de respuesta del método all del repositorio cuando la colección
     * de plantillas está vacío.
     *  El inicio del nombre del método garantiza que se ejecute primero y que el
     *  mapa de plantillas estpe vacío.
     * @throws PdfEngineException En caso de error.
     */
    @Test
    public void a_allEmptyListTest() throws PdfEngineException {

        TemplateRepositoryImpl repository = new TemplateRepositoryImpl();
        assertNotNull(repository.all());
        assertEquals(0, repository.all().size());
    }

    /**
     * Happy path
     *
     * @throws PdfEngineException Se lanza cuando sucede un error al agregar la plantilla
     */
    @Test
    public void addTest() throws PdfEngineException {

        TemplateRepositoryImpl repository = new TemplateRepositoryImpl();
        String templateName = "TEMPLATE_ADD";
        String templateContent = getFile("template1.xsl");
        repository.add(templateName, templateContent);
        assertEquals("TEMPLATE_ADD", repository.byName(templateName).getName());
    }

    /**
     * Si se ejecuta el caso de prueba addTest, este valida que la plantilla recuperada sea TEMPLATE1.
     *
     */
    @Test
    public void byNameTest() throws PdfEngineException {
        TemplateRepositoryImpl repository = new TemplateRepositoryImpl();

        String templateName = "TEMPLATE_BY_NAME";
        String templateContent = getFile("template1.xsl");
        repository.add(templateName, templateContent);

        Template template = repository.byName(templateName);
        assertEquals(templateName, template.getName());
    }

    /**
     * Similar a las pruebas de add, este test valida que el método persist se ejecuten correctamente.
     * @throws PdfEngineException Excepción en caso de error al agregar y persistir.
     */
    @Test
    public void persistTest() throws PdfEngineException {
        TemplateRepositoryImpl repository = new TemplateRepositoryImpl();
        String templateName = "TEMPLATE_PERSIST";
        String templateContent = getFile("template1.xsl");

        repository.persist(templateName, templateContent);

        Path xslFilePath = Paths.get(PERSISTED_TEMPLATES_DIRECTORY + templateName + ".xsl");
        assertTrue(Files.exists(xslFilePath));

        try {
            Files.deleteIfExists(xslFilePath);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void existTemplateTest() throws PdfEngineException {
        TemplateRepositoryImpl repository = new TemplateRepositoryImpl();
        String templateName = "TEMPLATE_EXISTING";
        String templateContent = getFile("template1.xsl");

        repository.add(templateName, templateContent);

        assertTrue(repository.existTemplate(templateName));
        assertFalse(repository.existTemplate("OTHER_TEMPLATE"));
    }

    @Test
    public void allTest() throws PdfEngineException {

        TemplateRepositoryImpl repository = new TemplateRepositoryImpl();
        String templateContent = getFile("template1.xsl");

        String templateName1 = "TEMPLATE_EXISTING_1";
        repository.add(templateName1, templateContent);

        String templateName2 = "TEMPLATE_EXISTING_2";
        repository.add(templateName2, templateContent);

        List<String> expectedTemplatesList = Arrays.asList("TEMPLATE_EXISTING_1", "TEMPLATE_EXISTING_2");
        assertTrue(repository.all().containsAll(expectedTemplatesList));
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
            return "";
        }
    }
}
