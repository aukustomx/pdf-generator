package io.augusto.pdfgenerator.repository;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Created by j49u4r on 4/6/17.
 */
public class RepoLiteral extends AnnotationLiteral<Repo> implements Repo {

    private String value;

    private RepoLiteral(String theValue) {
        this.value = theValue;
    }

    @Override
    public String value() {
        return value;
    }

    public static RepoLiteral repo(String theValue) {
        return new RepoLiteral(theValue);
    }
}
