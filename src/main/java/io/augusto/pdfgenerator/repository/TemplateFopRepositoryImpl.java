package io.augusto.pdfgenerator.repository;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.domain.model.TemplateType;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toMap;

/**
 * Responsable de todas las operaciones de administración de plantillas
 * fop.
 * Created by j49u4r on 3/22/17.
 */
@Repo("fop")
@ApplicationScoped
public class TemplateFopRepositoryImpl implements TemplateRepository {

    private static Logger logger = LogManager.getLogger();
    private static ConcurrentHashMap<String, Template> templates = new ConcurrentHashMap<>();
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
    public boolean existTemplate(String name) {
        return templates.containsKey(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(String name, String content) throws PdfEngineException {

        logger.debug("TemplateName: {}  TemplateContent: {}", () -> name, () -> content);

        ByteArrayInputStream input = new ByteArrayInputStream(content.getBytes(UTF_8));
        Source xslSrc = new StreamSource(input);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        try {
            Template template = new Template(name, transformerFactory.newTemplates(xslSrc), TemplateType.FOP);
            templates.put(name, template);
            logger.debug("Plantilla {} agregada correctamente ", () -> name);
        } catch (Exception e) {
            logger.info("Ocurrió un error al intentar agregar la plantilla {} ", () -> name);
            throw new PdfEngineException(PdfEngineError.PDFGEN_2007, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(String name, String content) throws PdfEngineException {

        String xslFileName = PERSISTED_TEMPLATES_DIRECTORY + name + ".xsl";
        writeFile(Paths.get(xslFileName), content);
        logger.debug("Archivo {} de plantilla {} agregado correctamente ", () -> name, () -> xslFileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String name) {

        templates.remove(name);
        logger.debug("Plantilla {} eliminada exitosamente", () -> name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> all() {
        return  templates.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(toMap(Template::getName, t -> t.getType().toString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Template byName(String name) {
        return templates.get(name);
    }
}
