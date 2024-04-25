package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.statement.AnnotatedStatement;
import intellispaces.javastatements.statement.reference.TypeReference;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();
}
