package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();
}
