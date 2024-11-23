package tech.intellispaces.java.reflection.reference;

import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.customtype.ClassType;
import tech.intellispaces.java.reflection.customtype.Classes;
import tech.intellispaces.java.reflection.customtype.CustomType;
import tech.intellispaces.java.reflection.customtype.InterfaceType;
import tech.intellispaces.java.reflection.customtype.Interfaces;
import tech.intellispaces.entity.exception.NotImplementedExceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractCustomTypeReference extends AbstractTypeReference implements CustomTypeReference {

  AbstractCustomTypeReference() {
    super();
  }

  @Override
  public CustomType effectiveTargetType() {
    Map<String, NotPrimitiveReference> typeMapping = typeArgumentMapping();
    CustomType type = targetType();
    if (type.statementType() == StatementTypes.Class) {
      return Classes.effectiveOf((ClassType) type, typeMapping);
    } else if (type.statementType() == StatementTypes.Interface) {
      return Interfaces.effectiveOf((InterfaceType) type, typeMapping);
    } else {
      throw NotImplementedExceptions.withCode("s0rZVg");
    }
  }

  @Override
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
    List<NotPrimitiveReference> curTypeArguments = typeArguments();
    List<NotPrimitiveReference> newTypeArguments = new ArrayList<>();
    for (NotPrimitiveReference curTypeArgument : curTypeArguments) {
      newTypeArguments.add((NotPrimitiveReference) curTypeArgument.effective(typeMapping));
    }
    return new CustomTypeReferenceImpl(targetType(), newTypeArguments);
  }
}
