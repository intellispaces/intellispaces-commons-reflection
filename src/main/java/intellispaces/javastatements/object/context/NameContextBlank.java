package intellispaces.javastatements.object.context;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;

public interface NameContextBlank extends NameContext {

  NameContextBlank setParentContext(NameContext parentContext);

  NameContextBlank addTypeParam(String typeParamName, NamedTypeReference type);

  NameContextBlank addTypeParam(String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference value);
}
