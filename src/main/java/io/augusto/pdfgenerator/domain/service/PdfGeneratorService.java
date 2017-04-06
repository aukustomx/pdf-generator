package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.infra.exception.PdfEngineException;

/**
 * Created by j49u4r on 3/23/17.
 */
public interface PdfGeneratorService {

    byte[] generatePdf(String templateName, String xmlInput) throws PdfEngineException;
}
