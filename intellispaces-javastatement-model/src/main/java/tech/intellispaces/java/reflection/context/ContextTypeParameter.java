package tech.intellispaces.java.reflection.context;

import tech.intellispaces.java.reflection.reference.NamedReference;
import tech.intellispaces.java.reflection.reference.NotPrimitiveReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveReference actualType();
}
