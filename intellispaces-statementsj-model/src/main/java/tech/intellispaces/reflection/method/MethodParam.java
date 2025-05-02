package tech.intellispaces.statementsj.method;

import java.util.Map;

import tech.intellispaces.statementsj.AnnotatedStatement;
import tech.intellispaces.statementsj.reference.NotPrimitiveReference;
import tech.intellispaces.statementsj.reference.TypeReference;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam effective(Map<String, NotPrimitiveReference> typeMapping);
}
