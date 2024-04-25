package intellispaces.javastatements.context;

import intellispaces.javastatements.statement.reference.NamedTypeReference;
import intellispaces.javastatements.statement.reference.NonPrimitiveTypeReference;

public interface ContextTypeParameter {

  NamedTypeReference reference();

  NonPrimitiveTypeReference type();
}
