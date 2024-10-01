package intellispaces.common.javastatement.context;

import intellispaces.common.javastatement.reference.NamedReference;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;

public interface TypeContextBlank extends TypeContext {

  TypeContextBlank setParentContext(TypeContext parentContext);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type);

  TypeContextBlank addTypeParam(String typeParamName, NamedReference type, NotPrimitiveReference value);
}
