package io.augusto.pdfgenerator.domain.model;

import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.fop.apps.FOPException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.FileNotFoundException;

/**
 * Created by j49u4r on 3/31/17.
 */
public class Xml2PdfJasperGenerator implements PdfGenerator {
    @Override
    public void generate(String inputXml) throws PdfEngineException {
        throw new PdfEngineException(PdfEngineError.PDFGEN_2000,
                new UnsupportedOperationException("Operation not implemented yet"));
    }

    @Override
    public void generate(String inputXml, String templateName) throws FileNotFoundException, FOPException,
            TransformerConfigurationException {

        throw new UnsupportedOperationException("Unsupported operation.");
    }
}
