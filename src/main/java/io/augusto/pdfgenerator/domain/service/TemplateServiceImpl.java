package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.repository.TemplateRepository;
import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by j49u4r on 3/22/17.
 */
@Dependent
public class TemplateServiceImpl implements TemplateService {

    TemplateRepository templateRepository;

    @Inject
    public TemplateServiceImpl(TemplateRepository repository) {
        this.templateRepository = repository;
    }

    @Override
    public void add(String templateName, String templateContent) throws PdfEngineException {

        if (StringUtils.isEmpty(templateName)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2001);
        }

        if (StringUtils.isEmpty(templateContent)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2002);
        }

        if (templateRepository.existTemplate(templateName)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2003);
        }

        templateRepository.add(templateName, templateContent);
    }

    @Override
    public void addAndPersist(String templateName, String templateContent) throws PdfEngineException {
        add(templateName, templateContent);
        templateRepository.persist(templateName, templateContent);
    }

    @Override
    public void delete(String templateName) throws PdfEngineException {
        if (StringUtils.isEmpty(templateName)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2001);
        }

        if (!templateRepository.existTemplate(templateName)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2004);
        }

        templateRepository.delete(templateName);
    }

    @Override
    public List<String> all() {
        return templateRepository.all();
    }

    @Override
    public Template byName(String templateName) throws PdfEngineException {
        if (StringUtils.isEmpty(templateName)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2001);
        }

        if (!templateRepository.existTemplate(templateName)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2004);
        }

        return templateRepository.byName(templateName);
    }
}
