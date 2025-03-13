package tech.intellispaces.commons.reflection.context;

import tech.intellispaces.commons.reflection.reference.NamedReference;
import tech.intellispaces.commons.reflection.reference.NotPrimitiveReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type, NotPrimitiveReference value);
}
