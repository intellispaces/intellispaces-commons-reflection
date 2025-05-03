package tech.intellispaces.jstatements.context;

import tech.intellispaces.jstatements.reference.NamedReference;
import tech.intellispaces.jstatements.reference.NotPrimitiveReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveReference actualType();
}
