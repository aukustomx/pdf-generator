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
    private Object compiledTemplate;
    private TemplateType type;

    public Template(String name, Object compiledTemplate, TemplateType type) {
        this.name = name;
        this.compiledTemplate = compiledTemplate;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Object getCompiledTemplate() {
        return compiledTemplate;
    }

    public TemplateType getType() {
        return type;
    }
}
