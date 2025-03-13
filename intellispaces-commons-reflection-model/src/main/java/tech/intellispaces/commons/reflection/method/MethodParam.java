package tech.intellispaces.commons.reflection.method;

import tech.intellispaces.commons.reflection.AnnotatedStatement;
import tech.intellispaces.commons.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.commons.reflection.reference.TypeReference;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam effective(Map<String, NotPrimitiveReference> typeMapping);
}
