package intellispaces.javastatements.model.context;

import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;

public interface ContextTypeParameter {

  NamedTypeReference type();

  NonPrimitiveTypeReference actualType();
}
