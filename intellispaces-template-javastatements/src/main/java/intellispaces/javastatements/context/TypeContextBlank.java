package intellispaces.javastatements.context;

import intellispaces.javastatements.statement.reference.NamedTypeReference;
import intellispaces.javastatements.statement.reference.NonPrimitiveTypeReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedTypeReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference value);
}
