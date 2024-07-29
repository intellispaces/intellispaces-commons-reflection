package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.customtype.ClassStatements;
import tech.intellispaces.javastatements.statement.customtype.ClassType;
import tech.intellispaces.javastatements.statement.customtype.CustomType;
import tech.intellispaces.javastatements.statement.customtype.InterfaceStatements;
import tech.intellispaces.javastatements.statement.customtype.InterfaceType;

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
