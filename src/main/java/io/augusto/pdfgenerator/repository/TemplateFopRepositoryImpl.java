package io.augusto.pdfgenerator.repository;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by j49u4r on 3/22/17.
 */
@Repo("fop")
@ApplicationScoped
public class TemplateFopRepositoryImpl implements TemplateRepository {

    private static Logger logger = LogManager.getLogger();
    private static ConcurrentHashMap<String, Template> templates = new ConcurrentHashMap<String, Template>();
    private static final String PERSISTED_TEMPLATES_DIRECTORY = "/data/templates/fop/";

    /**
     * {@inheritDoc}
     */
    public TemplateFopRepositoryImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existTemplate(String templateName) {
        return templates.containsKey(templateName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(String templateName, String templateContent) throws PdfEngineException {

        logger.debug("TemplateName: {}  TemplateContent: {}", () -> templateName, () -> templateContent);

        ByteArrayInputStream input = new ByteArrayInputStream(templateContent.getBytes(UTF_8));
        Source xslSrc = new StreamSource(input);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        try {
            Template template = new Template(templateName, transformerFactory.newTemplates(xslSrc));
            templates.put(templateName, template);
            logger.debug("Plantilla {} agregada correctamente ", () -> templateName);
        } catch (Exception e) {
            logger.info("Ocurrió un error al intentar agregar la plantilla {} ", () -> templateName);
            throw new PdfEngineException(PdfEngineError.PDFGEN_2007, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(String templateName, String templateContent) throws PdfEngineException {

        String xslFileName = PERSISTED_TEMPLATES_DIRECTORY + templateName + ".xsl";
        writeFile(Paths.get(xslFileName), templateContent);
        logger.debug("Archivo {} de plantilla {} agregado correctamente ", () -> templateName, () -> xslFileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String templateName) {

        templates.remove(templateName);
        logger.debug("Plantilla {} eliminada exitosamente", () -> templateName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> all() {
        return templates.keySet().stream()
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Template byName(String templateName) {
        return templates.get(templateName);
    }
}
