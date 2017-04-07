package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;

import java.util.List;
import java.util.Map;

/**
 * Created by j49u4r on 3/22/17.
 */
public interface TemplateService {

    void add(String name, String content, String type) throws PdfEngineException;
    void addAndPersist(String name, String content, String type) throws PdfEngineException;
    void delete(String name, String type) throws PdfEngineException;
    Map<String, String> all();
    Map<String, String> allOfType(String type);
    Template byName(String name) throws PdfEngineException;
}
