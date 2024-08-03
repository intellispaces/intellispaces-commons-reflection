package tech.intellispaces.javastatements.reference;

import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.customtype.ClassType;
import tech.intellispaces.javastatements.customtype.Classes;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.customtype.InterfaceType;
import tech.intellispaces.javastatements.customtype.Interfaces;

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
      throw UnexpectedViolationException.withMessage("Not implemented yet");
    }
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveReference> typeMapping) {
    List<NotPrimitiveReference> curTypeArguments = typeArguments();
    List<NotPrimitiveReference> newTypeArguments = new ArrayList<>();
    for (NotPrimitiveReference curTypeArgument : curTypeArguments) {
      newTypeArguments.add((NotPrimitiveReference) curTypeArgument.specify(typeMapping));
    }
    return new CustomTypeReferenceImpl(targetType(), newTypeArguments);
  }
}
