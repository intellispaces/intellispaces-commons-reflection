package tech.intellispaces.jstatements.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.customtype.ClassType;
import tech.intellispaces.jstatements.customtype.Classes;
import tech.intellispaces.jstatements.customtype.CustomType;
import tech.intellispaces.jstatements.customtype.InterfaceType;
import tech.intellispaces.jstatements.customtype.Interfaces;

abstract class AbstractCustomTypeReference extends AbstractTypeReference implements CustomTypeReference {

  AbstractCustomTypeReference() {
    super();
  }

  @Override
  public boolean isVoidType() {
    return Void.class.getCanonicalName().equals(targetType().canonicalName());
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
