package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.exception.UnexpectedViolationException;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.custom.EffectiveClassStatement;
import tech.intellispaces.framework.javastatements.statement.custom.EffectiveInterfaceStatement;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractCustomTypeReference extends AbstractTypeReference implements CustomTypeReference {

  public AbstractCustomTypeReference() {
    super();
  }

  @Override
  public CustomType effectiveTargetType() {
    Map<String, NonPrimitiveTypeReference> typeMapping = typeArgumentMapping();
    CustomType targetType = targetType();
    if (targetType.statementType() == StatementTypes.Class) {
      return new EffectiveClassStatement((ClassStatement) targetType, typeMapping);
    } else if (targetType.statementType() == StatementTypes.Interface) {
      return new EffectiveInterfaceStatement((InterfaceStatement) targetType, typeMapping);
    } else {
      throw UnexpectedViolationException.withMessage("Not implemented yet");
    }
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
