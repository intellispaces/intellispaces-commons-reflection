package intellispaces.javastatements.context;

import intellispaces.javastatements.reference.NamedReference;
import intellispaces.javastatements.reference.NotPrimitiveReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveReference actualType();
}
