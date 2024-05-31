package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

public interface ContextTypeParameter {

  NamedTypeReference reference();

  NonPrimitiveTypeReference type();
}
