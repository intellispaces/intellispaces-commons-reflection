package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;
import tech.intellispaces.framework.javastatements.statement.type.Type;

import java.util.Map;

/**
 * Formal method parameter.
 */
public interface MethodParam extends AnnotatedStatement {

  String name();

  Type type();

  MethodParam specify(Map<String, NotPrimitiveType> typeMapping);
}
