package io.augusto.pdfgenerator.repository;

import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;

import java.util.List;

/**
 * Created by j49u4r on 3/23/17.
 */
public interface TemplateRepository {

    /**
     * Verifica si una plantilla con nombre templateName existe o no.
     *
     * @param templateName Nombre de plantilla.
     * @return true/false en caso de que la plantilla exista o no respectivamente.
     */
    boolean existTemplate(String templateName);

    /**
     * Expone la funcionalidad de alta de una nueva plantilla.
     *
     * @param templateName    Nombre de platilla que se asigna a la plantilla agregada.
     * @param templateContent Contenido (xml) de la plantilla como String.
     * @throws PdfEngineException Se lanza en caso de error.
     */
    void add(String templateName, String templateContent) throws PdfEngineException;

    /**
     * Al igual que el método add, este agrega la plantilla y además las persiste en el file
     * system donde corre la aplicación.
     *
     * @param templateName    Nombre de platilla que se asigna a la plantilla agregada.
     * @param templateContent Contenido (xml) de la plantilla como String.
     * @throws PdfEngineException Se lanza en caso de error.
     */
    void persist(String templateName, String templateContent) throws PdfEngineException;

    /**
     * Si existe, elimina la plantilla con el nombre templateName.
     *
     * @param templateName Nombre de platilla que se asigna a la plantilla agregada.
     */
    void delete(String templateName);

    /**
     * Devuelve la lista de todos los nombres de plantilla fo cargadas en memoria
     * en un momento en el tiempo.
     *
     * @return Lista de nombres de plantillas.
     */
    List<String> all();

    /**
     * Responsable de regresar la plantilla (compilada) con el nombre de plantilla que recibe
     * como parámetro; esto si la plantilla existe.
     *
     * @param templateName Nombre de la plantilla a recuperar.
     * @return Plantilla compilada.
     */
    Template byName(String templateName);
}
