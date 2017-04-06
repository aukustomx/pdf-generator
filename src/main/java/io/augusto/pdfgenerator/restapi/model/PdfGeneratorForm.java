package io.augusto.pdfgenerator.restapi.model;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

/**
 * Sirve de mapping entre los parámetros del formulario de entrada con los
 * esperados por el apiService.
 * Created by j49u4r on 4/5/17.
 */
public class PdfGeneratorForm {

    @FormParam("templateName")
    @PartType(MediaType.TEXT_PLAIN)
    private String templateName;

    @FormParam("xmlContent")
    @PartType(MediaType.TEXT_PLAIN)
    private String xmlContent;

    public PdfGeneratorForm() {
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }
}
