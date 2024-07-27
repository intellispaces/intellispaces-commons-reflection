package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;

public interface ContextTypeParameter {

  NamedType namedType();

  NotPrimitiveType actualType();
}
