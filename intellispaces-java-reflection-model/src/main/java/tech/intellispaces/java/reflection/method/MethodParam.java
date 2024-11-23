package tech.intellispaces.java.reflection.method;

import tech.intellispaces.java.reflection.AnnotatedStatement;
import tech.intellispaces.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.java.reflection.reference.TypeReference;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam effective(Map<String, NotPrimitiveReference> typeMapping);
}
