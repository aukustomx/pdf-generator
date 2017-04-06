package io.augusto.pdfgenerator.domain.model;

import org.apache.fop.apps.FOPException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.FileNotFoundException;

/**
 * Created by j49u4r on 3/31/17.
 */
public class Xml2PdfJasperGenerator implements PdfGenerator {
    @Override
    public void generate(String inputXml) throws FileNotFoundException, FOPException,
            TransformerConfigurationException {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    @Override
    public void generate(String inputXml, String templateName) throws FileNotFoundException, FOPException,
            TransformerConfigurationException {

        throw new UnsupportedOperationException("Unsupported operation.");
    }
}
