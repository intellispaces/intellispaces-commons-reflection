package tech.intellispaces.reflection.method;

import java.util.Map;

import tech.intellispaces.reflection.AnnotatedStatement;
import tech.intellispaces.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.reflection.reference.TypeReference;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam effective(Map<String, NotPrimitiveReference> typeMapping);
}
