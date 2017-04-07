package io.augusto.pdfgenerator.domain.model;

import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by j49u4r on 3/22/17.
 */
public final class Xml2PdfFopGenerator implements PdfGenerator {

    private static Logger logger = LogManager.getLogger(Xml2PdfFopGenerator.class);
    private static final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

    private Xml2PdfFopGenerator() {
    }

    public void generate(String inputXml2) throws PdfEngineException {
        throw new PdfEngineException(PdfEngineError.PDFGEN_2000,
                new UnsupportedOperationException("Operation not implemented yet"));
    }

    public void generate(String inputXml, String templateName) throws PdfEngineException {
        throw new PdfEngineException(PdfEngineError.PDFGEN_2000,
                new UnsupportedOperationException("Operación no implementada"));
    }

    /**
     * Genera el PDF a partir de un xmlSource (xml origen) y el objeto plantilla (que debe existir).
     *
     * @param xmlSource Xml origen del que se extraen los campos del PDF.
     * @param template  Plantilla.
     * @throws PdfEngineException Cualquier error de envuelve en este tipo.
     */
    public static byte[] generate(String xmlSource, Template template) throws PdfEngineException {

        logger.debug("XmlSource: {}", () -> xmlSource);

        String uuid = UUID.randomUUID().toString();
        String pdfName = "/tmp/" + uuid + ".pdf";

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(pdfName)))) {

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            Source src = new StreamSource(new StringReader(xmlSource));
            Result res = new SAXResult(fop.getDefaultHandler());

            Transformer transformer = ((Templates)(template.getCompiledTemplate())).newTransformer();
            transformer.transform(src, res);
            out.flush();

            return Files.readAllBytes(Paths.get(pdfName));
        } catch (Exception e) {
            logger.info("Error al generar el PDF", e);
            throw new PdfEngineException(PdfEngineError.PDFGEN_2000, e);
        }
    }
}
