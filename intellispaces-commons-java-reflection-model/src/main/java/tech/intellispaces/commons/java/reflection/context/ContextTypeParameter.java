package tech.intellispaces.commons.java.reflection.context;

import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveReference actualType();
}
