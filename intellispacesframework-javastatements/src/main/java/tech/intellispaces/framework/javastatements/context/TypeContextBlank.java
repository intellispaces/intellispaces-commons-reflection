package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedType type);

  TypeContextBlank addTypeParam(String typeParamName, NamedType type, NotPrimitiveType value);
}
