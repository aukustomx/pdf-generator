package io.augusto.pdfgenerator.repository;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by j49u4r on 4/6/17.
 */
@Repo("jasper")
@ApplicationScoped
public class TemplateJasperRepositoryImpl implements TemplateRepository {

    private static Logger logger = LogManager.getLogger();
    private static ConcurrentHashMap<String, String> templates = new ConcurrentHashMap<String, String>();
    private static final String PERSISTED_TEMPLATES_DIRECTORY = "/data/templates/jasper/";

    @Override
    public boolean existTemplate(String templateName) {
        return templates.containsKey(templateName);
    }

    @Override
    public void add(String templateName, String templateContent) throws PdfEngineException {

        logger.debug("TemplateName: {}  TemplateContent: {}", () -> templateName, () -> templateContent);
        try {
            templates.put(templateName, templateContent);
            logger.debug("Plantilla {} agregada correctamente ", () -> templateName);
        } catch (Exception e) {
            logger.info("Ocurrió un error al intentar agregar la plantilla {} ", () -> templateName);
            throw new PdfEngineException(PdfEngineError.PDFGEN_2007, e);
        }
    }

    @Override
    public void persist(String templateName, String templateContent) throws PdfEngineException {

        String xslFileName = PERSISTED_TEMPLATES_DIRECTORY + templateName + ".jrxml";
        writeFile(Paths.get(xslFileName), templateContent);
        logger.debug("Archivo {} de plantilla {} agregado correctamente ", () -> templateName, () -> xslFileName);
    }

    @Override
    public void delete(String templateName) {

    }

    @Override
    public List<String> all() {
        return null;
    }

    @Override
    public Template byName(String templateName) {
        return null;
    }
}
