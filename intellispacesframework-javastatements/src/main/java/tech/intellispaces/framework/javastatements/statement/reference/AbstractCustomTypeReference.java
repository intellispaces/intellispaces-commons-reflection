package tech.intellispaces.framework.javastatements.statement.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractCustomTypeReference extends AbstractTypeReference implements CustomTypeReference {

  public AbstractCustomTypeReference() {
    super();
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    List<NonPrimitiveTypeReference> curTypeArguments = typeArguments();
    List<NonPrimitiveTypeReference> newTypeArguments = new ArrayList<>();
    for (NonPrimitiveTypeReference curTypeArgument : curTypeArguments) {
      newTypeArguments.add((NonPrimitiveTypeReference) curTypeArgument.specify(typeMapping));
    }
    return new CustomTypeReferenceImpl(targetType(), newTypeArguments);
  }
}
