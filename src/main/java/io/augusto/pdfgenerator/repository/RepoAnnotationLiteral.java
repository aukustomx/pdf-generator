package io.augusto.pdfgenerator.repository;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Created by j49u4r on 4/6/17.
 */
public class RepoAnnotationLiteral extends AnnotationLiteral<Repo> implements Repo {

    private String value;

    private RepoAnnotationLiteral(String theValue) {
        this.value = theValue;
    }

    @Override
    public String value() {
        return value;
    }

    public static RepoAnnotationLiteral repo(String theValue) {
        return new RepoAnnotationLiteral(theValue);
    }
}
