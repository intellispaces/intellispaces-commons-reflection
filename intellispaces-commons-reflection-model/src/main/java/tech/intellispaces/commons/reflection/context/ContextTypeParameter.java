package tech.intellispaces.commons.reflection.context;

import tech.intellispaces.commons.reflection.reference.NamedReference;
import tech.intellispaces.commons.reflection.reference.NotPrimitiveReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveReference actualType();
}
