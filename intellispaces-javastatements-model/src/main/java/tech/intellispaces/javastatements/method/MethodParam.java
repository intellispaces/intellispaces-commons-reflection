package tech.intellispaces.javastatements.method;

import tech.intellispaces.javastatements.AnnotatedStatement;
import tech.intellispaces.javastatements.reference.NotPrimitiveReference;
import tech.intellispaces.javastatements.reference.TypeReference;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam specify(Map<String, NotPrimitiveReference> typeMapping);
}
