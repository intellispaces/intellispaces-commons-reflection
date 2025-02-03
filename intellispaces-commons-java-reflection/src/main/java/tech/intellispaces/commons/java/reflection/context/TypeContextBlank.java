package tech.intellispaces.commons.java.reflection.context;

import tech.intellispaces.commons.java.reflection.reference.NamedReference;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type, NotPrimitiveReference value);
}
