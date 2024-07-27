package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.custom.CustomTypes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter of {@link Class} to {@link CustomType}.
 */
class CustomTypeBasedOnClass extends AbstractType implements CustomType {
  private final Class<?> aClass;
  private CustomStatement statement;

  CustomTypeBasedOnClass(Class<?> aClass) {
    this.aClass = aClass;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomType;
  }

  @Override
  public CustomStatement statement() {
    if (statement == null) {
      statement = CustomTypes.of(aClass);
    }
    return statement;
  }

  @Override
  public List<NotPrimitiveType> typeArguments() {
    return List.of();
  }

  @Override
  public CustomStatement effectiveStatement() {
    return statement;
  }

  @Override
  public Map<String, NotPrimitiveType> typeArgumentMapping() {
    return Map.of();
  }

  @Override
  public Type specify(Map<String, NotPrimitiveType> typeMapping) {
    return this;
  }

  @Override
  public String typeArgumentsDeclaration() {
    return "";
  }

  @Override
  public String typeArgumentsDeclaration(Function<String, String> simpleNameMapper) {
    return "";
  }
}
