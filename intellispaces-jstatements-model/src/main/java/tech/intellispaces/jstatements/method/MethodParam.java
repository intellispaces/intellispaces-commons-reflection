package tech.intellispaces.jstatements.method;

import java.util.Map;

import tech.intellispaces.jstatements.AnnotatedStatement;
import tech.intellispaces.jstatements.reference.NotPrimitiveReference;
import tech.intellispaces.jstatements.reference.TypeReference;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam effective(Map<String, NotPrimitiveReference> typeMapping);
}
