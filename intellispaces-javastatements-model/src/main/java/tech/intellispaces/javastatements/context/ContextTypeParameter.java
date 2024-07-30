package tech.intellispaces.javastatements.context;

import tech.intellispaces.javastatements.reference.NamedReference;
import tech.intellispaces.javastatements.reference.NotPrimitiveReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveReference actualType();
}
