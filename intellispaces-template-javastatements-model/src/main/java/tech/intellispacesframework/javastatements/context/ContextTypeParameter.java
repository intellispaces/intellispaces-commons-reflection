package tech.intellispacesframework.javastatements.context;

import tech.intellispacesframework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NonPrimitiveTypeReference;

public interface ContextTypeParameter {

  NamedTypeReference reference();

  NonPrimitiveTypeReference type();
}
