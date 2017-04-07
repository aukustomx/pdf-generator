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
import java.util.Map;
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
    public boolean existTemplate(String name) {
        return templates.containsKey(name);
    }

    @Override
    public void add(String name, String content) throws PdfEngineException {

        logger.debug("TemplateName: {}  TemplateContent: {}", () -> name, () -> content);
        try {
            templates.put(name, content);
            logger.debug("Plantilla {} agregada correctamente ", () -> name);
        } catch (Exception e) {
            logger.info("Ocurrió un error al intentar agregar la plantilla {} ", () -> name);
            throw new PdfEngineException(PdfEngineError.PDFGEN_2007, e);
        }
    }

    @Override
    public void persist(String name, String content) throws PdfEngineException {

        String xslFileName = PERSISTED_TEMPLATES_DIRECTORY + name + ".jrxml";
        writeFile(Paths.get(xslFileName), content);
        logger.debug("Archivo {} de plantilla {} agregado correctamente ", () -> name, () -> xslFileName);
    }

    @Override
    public void delete(String name) {

    }

    @Override
    public Map<String, String> all() {
        return null;
    }

    @Override
    public Template byName(String name) {
        return null;
    }
}
