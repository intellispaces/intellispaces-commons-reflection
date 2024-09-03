package intellispaces.common.javastatements.context;

import intellispaces.common.javastatements.reference.NamedReference;
import intellispaces.common.javastatements.reference.NotPrimitiveReference;

public interface ContextTypeParameter {

  NamedReference namedType();

  NotPrimitiveReference actualType();
}
