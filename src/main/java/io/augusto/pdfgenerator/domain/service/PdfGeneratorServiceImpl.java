package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.domain.model.Xml2PdfFopGenerator;
import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Created by j49u4r on 3/23/17.
 */
@Dependent
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private static Logger logger = LogManager.getLogger();

    private TemplateService templateService;

    @Inject
    public PdfGeneratorServiceImpl(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public byte[] generatePdf(String templateName, String xmlSource) throws PdfEngineException {

        if (StringUtils.isEmpty(templateName)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2001);
        }

        if (StringUtils.isEmpty(xmlSource)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2101);
        }

        Template template = templateService.byName(templateName);
        logger.debug("La plantilla compilada es null ? {}", () -> null == template.getCompiledTemplate());
        return Xml2PdfFopGenerator.generate(xmlSource, template);
    }

    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }
}
