package tech.intellispacesframework.javastatements.context;

import tech.intellispacesframework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NonPrimitiveTypeReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedTypeReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference value);
}
