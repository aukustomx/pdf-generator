package io.augusto.pdfgenerator.restapi.model;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

/**
 * Utilizado para hacer el mapping entre los parámetros
 * de entrada del multiformdata de una plantilla a
 * objetos java.
 * Created by j49u4r on 4/5/17.
 */
public class TemplateForm {

    @FormParam("templateName")
    @PartType(MediaType.TEXT_PLAIN)
    private String templateName;

    @FormParam("templateContent")
    @PartType(MediaType.TEXT_PLAIN)
    private String templateContent;

    @FormParam("isPersistent")
    @PartType(MediaType.TEXT_PLAIN)
    private boolean isPersistent;

    /**
     * Necesario para el mecanismo de un/marshalling de restEasy
     */
    public TemplateForm() {
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }
}
