package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;

import java.util.List;

/**
 * Created by j49u4r on 3/22/17.
 */
public interface TemplateService {

    void add(String templateName, String templateContent, String templateType) throws PdfEngineException;
    void addAndPersist(String templateName, String templateContent, String templateType) throws PdfEngineException;
    void delete(String templateName) throws PdfEngineException;
    List<String> all();
    Template byName(String templateName) throws PdfEngineException;
}
