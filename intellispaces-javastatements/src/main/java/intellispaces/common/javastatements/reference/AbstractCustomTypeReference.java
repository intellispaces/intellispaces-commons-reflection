package intellispaces.common.javastatements.reference;

import intellispaces.common.exception.UnexpectedViolationException;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.customtype.Classes;
import intellispaces.common.javastatements.customtype.Interfaces;
import intellispaces.common.javastatements.customtype.ClassType;
import intellispaces.common.javastatements.customtype.CustomType;
import intellispaces.common.javastatements.customtype.InterfaceType;

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
