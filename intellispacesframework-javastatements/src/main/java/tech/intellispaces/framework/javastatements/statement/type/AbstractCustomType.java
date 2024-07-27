package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.exception.UnexpectedViolationException;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatement;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatements;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatement;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractCustomType extends AbstractType implements CustomType {

  AbstractCustomType() {
    super();
  }

  @Override
  public CustomStatement effectiveTargetType() {
    Map<String, NonPrimitiveType> typeMapping = typeArgumentMapping();
    CustomStatement targetType = targetType();
    if (targetType.statementType() == StatementTypes.Class) {
      return ClassStatements.effectiveOf((ClassStatement) targetType, typeMapping);
    } else if (targetType.statementType() == StatementTypes.Interface) {
      return InterfaceStatements.effectiveOf((InterfaceStatement) targetType, typeMapping);
    } else {
      throw UnexpectedViolationException.withMessage("Not implemented yet");
    }
  }

  @Override
  public Type specify(Map<String, NonPrimitiveType> typeMapping) {
    List<NonPrimitiveType> curTypeArguments = typeArguments();
    List<NonPrimitiveType> newTypeArguments = new ArrayList<>();
    for (NonPrimitiveType curTypeArgument : curTypeArguments) {
      newTypeArguments.add((NonPrimitiveType) curTypeArgument.specify(typeMapping));
    }
    return new CustomTypeImpl(targetType(), newTypeArguments);
  }
}
