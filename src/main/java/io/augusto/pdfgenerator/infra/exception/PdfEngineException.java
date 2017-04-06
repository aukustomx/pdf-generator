package io.augusto.pdfgenerator.infra.exception;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Created by j49u4r on 3/22/17.
 */
public class PdfEngineException extends Exception {

    private String code;

    public PdfEngineException(PdfEngineError error) {
        this(error.getDescription(), error.getCode());
    }

    public PdfEngineException(String message, String code) {
        super(message);
        this.code = code;
    }

    public PdfEngineException(PdfEngineError error, Throwable cause) {
        super(error.getDescription(), cause);
        this.code = error.getCode();
    }

    public String getErrorCode() {
        return this.code;
    }

    /**
     * Devuelve un objeto Json con el código y el mensaje.
     * @return JsonObject.
     */
    public String toJsonString() {
        return Json.createObjectBuilder()
                .add("Código", this.code)
                .add("Mensaje", this.getMessage())
                .build().toString();
    }
}
