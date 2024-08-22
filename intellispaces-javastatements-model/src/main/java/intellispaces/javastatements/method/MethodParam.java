package intellispaces.javastatements.method;

import intellispaces.javastatements.AnnotatedStatement;
import intellispaces.javastatements.reference.NotPrimitiveReference;
import intellispaces.javastatements.reference.TypeReference;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  TypeReference type();

  MethodParam specify(Map<String, NotPrimitiveReference> typeMapping);
}
