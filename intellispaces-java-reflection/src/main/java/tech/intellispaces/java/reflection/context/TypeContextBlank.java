package tech.intellispaces.java.reflection.context;

import tech.intellispaces.java.reflection.reference.NamedReference;
import tech.intellispaces.java.reflection.reference.NotPrimitiveReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type, NotPrimitiveReference value);
}
