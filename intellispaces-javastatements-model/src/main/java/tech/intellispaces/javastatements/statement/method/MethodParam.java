package tech.intellispaces.javastatements.statement.method;

import tech.intellispaces.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.javastatements.statement.reference.NotPrimitiveTypeReference;
import tech.intellispaces.javastatements.statement.reference.TypeReference;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam specify(Map<String, NotPrimitiveTypeReference> typeMapping);
}
