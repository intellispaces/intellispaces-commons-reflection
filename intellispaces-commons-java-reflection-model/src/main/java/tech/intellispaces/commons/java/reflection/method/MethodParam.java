package tech.intellispaces.commons.java.reflection.method;

import tech.intellispaces.commons.java.reflection.AnnotatedStatement;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.commons.java.reflection.reference.TypeReference;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam effective(Map<String, NotPrimitiveReference> typeMapping);
}
