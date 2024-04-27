package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.statement.AnnotatedStatement;
import tech.intellispacesframework.javastatements.statement.reference.TypeReference;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();
}
