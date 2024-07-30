package tech.intellispaces.javastatements.context;

import tech.intellispaces.javastatements.reference.NamedReference;
import tech.intellispaces.javastatements.reference.NotPrimitiveTypeReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type, NotPrimitiveTypeReference value);
}
