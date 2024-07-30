package tech.intellispaces.javastatements.reference;

import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.customtype.ClassStatements;
import tech.intellispaces.javastatements.customtype.ClassType;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.customtype.InterfaceStatements;
import tech.intellispaces.javastatements.customtype.InterfaceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractCustomTypeReference extends AbstractTypeReference implements CustomTypeReference {

  AbstractCustomTypeReference() {
    super();
  }

  @Override
  public CustomType effectiveStatement() {
    Map<String, NotPrimitiveTypeReference> typeMapping = typeArgumentMapping();
    CustomType type = customType();
    if (type.statementType() == StatementTypes.Class) {
      return ClassStatements.effectiveOf((ClassType) type, typeMapping);
    } else if (type.statementType() == StatementTypes.Interface) {
      return InterfaceStatements.effectiveOf((InterfaceType) type, typeMapping);
    } else {
      throw UnexpectedViolationException.withMessage("Not implemented yet");
    }
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    List<NotPrimitiveTypeReference> curTypeArguments = typeArguments();
    List<NotPrimitiveTypeReference> newTypeArguments = new ArrayList<>();
    for (NotPrimitiveTypeReference curTypeArgument : curTypeArguments) {
      newTypeArguments.add((NotPrimitiveTypeReference) curTypeArgument.specify(typeMapping));
    }
    return new CustomTypeReferenceImpl(customType(), newTypeArguments);
  }
}
