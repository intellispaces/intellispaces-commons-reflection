package tech.intellispaces.javastatements.context;

import tech.intellispaces.javastatements.statement.reference.NamedReference;
import tech.intellispaces.javastatements.statement.reference.NotPrimitiveTypeReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveTypeReference actualType();
}
