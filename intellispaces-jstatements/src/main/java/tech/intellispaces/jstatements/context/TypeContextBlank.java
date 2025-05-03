package tech.intellispaces.jstatements.context;

import tech.intellispaces.jstatements.reference.NamedReference;
import tech.intellispaces.jstatements.reference.NotPrimitiveReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type, NotPrimitiveReference value);
}
