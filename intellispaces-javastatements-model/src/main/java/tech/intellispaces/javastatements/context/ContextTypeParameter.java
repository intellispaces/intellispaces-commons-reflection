package tech.intellispaces.javastatements.context;

import tech.intellispaces.javastatements.reference.NamedReference;
import tech.intellispaces.javastatements.reference.NotPrimitiveTypeReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveTypeReference actualType();
}
