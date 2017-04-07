package io.augusto.pdfgenerator.domain.service;

import io.augusto.pdfgenerator.repository.RepoLiteral;
import io.augusto.pdfgenerator.repository.TemplateRepository;
import io.augusto.pdfgenerator.domain.model.Template;
import io.augusto.pdfgenerator.infra.exception.PdfEngineError;
import io.augusto.pdfgenerator.infra.exception.PdfEngineException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by j49u4r on 3/22/17.
 */
@Dependent
public class TemplateServiceImpl implements TemplateService {

    private static Logger logger = LogManager.getLogger();

    @Inject
    @Any
    private Instance<TemplateRepository> repositories;

    @Override
    public void add(String name, String content, String type) throws PdfEngineException {

        TemplateRepository repository = repositories.select(RepoLiteral.repo(type)).get();

        if (StringUtils.isEmpty(name)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2001);
        }

        if (StringUtils.isEmpty(content)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2002);
        }

        if (StringUtils.isEmpty(type)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2008);
        }

        if (repository.existTemplate(name)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2003);
        }

        repository.add(name, content);
    }

    @Override
    public void addAndPersist(String name, String content, String type) throws PdfEngineException {
        TemplateRepository repository = repositories.select(RepoLiteral.repo(type)).get();
        add(name, content, type);
        repository.persist(name, content);
    }

    @Override
    public void delete(String name, String type) throws PdfEngineException {

        logger.debug("Eliminar plantilla {} de tipo {}", () -> name, () -> type);
        TemplateRepository repository = repositories.select(RepoLiteral.repo(type)).get();

        if (StringUtils.isEmpty(name)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2001);
        }

        if (!repository.existTemplate(name)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2004);
        }

        repository.delete(name);
    }

    @Override
    public Map<String, String> all() {
        return StreamSupport.stream(repositories.spliterator(), false)
                .map(TemplateRepository::all)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, String> allOfType(String type) {
        TemplateRepository repository = repositories.select(RepoLiteral.repo(type)).get();
        return repository.all();
    }

    @Override
    public Template byName(String name) throws PdfEngineException {

        if (StringUtils.isEmpty(name)) {
            throw new PdfEngineException(PdfEngineError.PDFGEN_2001);
        }

        return StreamSupport.stream(repositories.spliterator(), false)
                .map(r -> r.byName(name))
                .findFirst()
                .orElseThrow(() -> {
                    logger.debug("La plantilla {} no existe", () -> name);
                    return new PdfEngineException(PdfEngineError.PDFGEN_2004);
                });
    }
}
