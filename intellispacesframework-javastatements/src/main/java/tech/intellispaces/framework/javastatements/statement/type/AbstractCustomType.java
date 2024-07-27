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
  public CustomStatement effectiveStatement() {
    Map<String, NotPrimitiveType> typeMapping = typeArgumentMapping();
    CustomStatement statement = statement();
    if (statement.statementType() == StatementTypes.Class) {
      return ClassStatements.effectiveOf((ClassStatement) statement, typeMapping);
    } else if (statement.statementType() == StatementTypes.Interface) {
      return InterfaceStatements.effectiveOf((InterfaceStatement) statement, typeMapping);
    } else {
      throw UnexpectedViolationException.withMessage("Not implemented yet");
    }
  }

  @Override
  public Type specify(Map<String, NotPrimitiveType> typeMapping) {
    List<NotPrimitiveType> curTypeArguments = typeArguments();
    List<NotPrimitiveType> newTypeArguments = new ArrayList<>();
    for (NotPrimitiveType curTypeArgument : curTypeArguments) {
      newTypeArguments.add((NotPrimitiveType) curTypeArgument.specify(typeMapping));
    }
    return new CustomTypeImpl(statement(), newTypeArguments);
  }
}
