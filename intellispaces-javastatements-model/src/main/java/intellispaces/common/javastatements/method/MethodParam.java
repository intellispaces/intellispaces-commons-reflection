package intellispaces.common.javastatements.method;

import intellispaces.common.javastatements.AnnotatedStatement;
import intellispaces.common.javastatements.reference.NotPrimitiveReference;
import intellispaces.common.javastatements.reference.TypeReference;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam specify(Map<String, NotPrimitiveReference> typeMapping);
}
