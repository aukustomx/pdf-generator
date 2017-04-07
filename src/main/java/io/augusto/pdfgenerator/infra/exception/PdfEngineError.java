package io.augusto.pdfgenerator.infra.exception;

/**
 * Created by j49u4r on 3/23/17.
 */
public enum PdfEngineError {

    /**
     * Cuando el nombre de una plantilla no puede ser nulo o vacío.
     */
    PDFGEN_2001("PDFGEN_2001", "El nombre de una plantilla XSLT no puede ser nulo o vacío"),

    /**
     * Cuando el contenido de una plantilla no puede ser nulo o vacío.
     */
    PDFGEN_2002("PDFGEN_2002", "El contenido de una plantilla XSLT no puede ser nulo o vacío"),

    /**
     * Utilizado para informar que el nombre de una plantilla ya existe.
     */
    PDFGEN_2003("PDFGEN_2003", "El nombre de la plantilla ya existe"),

    /**
     * Utilizado para informar que una plantilla no existe.
     */
    PDFGEN_2004("PDFGEN_2004", "La plantilla no existe"),

    /**
     * Envuelve cualquier error que no esté definido al momento de persistir una plantilla.
     */
    PDFGEN_2005("PDFGEN_2005", "Error al intentar persistir el archivo plantilla"),

    /**
     * Utilizado para informar que ocurrió un error al intentar devolver un transformer de la plantilla.
     */
    PDFGEN_2006("PDFGEN_2006", "Error al intentar devolver un nuevo transformer de la plantilla"),

    /**
     * Informa cuando ocurre un error no identificado al intentar agregar una plantilla.
     */
    PDFGEN_2007("PDFGEN_2007", "Ocurrió un error al intentar agregar la plantilla"),

    /**
     * Informa cuando se recibe un tipo de plantilla que no existe.
     */
    PDFGEN_2008("PDFGEN_2008", "En tipo de plantilla no existe"),

    /**
     * Para informar que el xml origen (del cual se extrae la información para generar el pdf) no puede
     * ser nulo o vacío.
     */
    PDFGEN_2101("PDFGEN_2101", "El xml origen es nulo o vacío"),

    /**
     * Envuelve cualquier error ocurrido en la generación del PDF.
     */
    PDFGEN_2000("PDFGEN_2000", "Error en la generación del PDF");

    private String code;
    private String description;

    private PdfEngineError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
