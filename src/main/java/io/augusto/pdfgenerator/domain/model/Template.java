package io.augusto.pdfgenerator.domain.model;

import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;

/**
 * Created by j49u4r on 3/28/17.
 */
public class Template {

    private static final Logger logger = LogManager.getLogger(Template.class);

    private String name;
    private Templates compiledTemplate;

    public Template(String name, Templates compiledTemplate) {
        this.name = name;
        this.compiledTemplate = compiledTemplate;
    }

    public String getName() {
        return name;
    }

    public Templates getCompiledTemplate() {
        return compiledTemplate;
    }

    public Transformer newTransformer() throws PdfEngineException {
        try {
            return compiledTemplate.newTransformer();
        } catch (TransformerConfigurationException e) {
            logger.info("Error al intentar devolver un nuevo transformer de la plantilla {}", () -> name);
            logger.info(() -> PdfEngineError.PDFGEN_2006.getDescription());
            throw new PdfEngineException(PdfEngineError.PDFGEN_2000);
        }
    }
}
