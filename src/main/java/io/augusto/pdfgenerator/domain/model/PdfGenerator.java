package io.augusto.pdfgenerator.domain.model;

import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.fop.apps.FOPException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.FileNotFoundException;

/**
 * Created by j49u4r on 3/22/17.
 */
public interface PdfGenerator {

    void generate(String inputXml) throws PdfEngineException;

    void generate(String inputXml, String templateName) throws FileNotFoundException, FOPException,
            TransformerConfigurationException, PdfEngineException;
}
