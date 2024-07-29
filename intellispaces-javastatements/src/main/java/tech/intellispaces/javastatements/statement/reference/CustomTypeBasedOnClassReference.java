package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.customtype.CustomType;
import tech.intellispaces.javastatements.statement.customtype.CustomTypes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter of {@link Class} to {@link CustomTypeReference}.
 */
class CustomTypeBasedOnClassReference extends AbstractTypeReference implements CustomTypeReference {
  private final Class<?> aClass;
  private CustomType baseType;

  CustomTypeBasedOnClassReference(Class<?> aClass) {
    this.aClass = aClass;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomReference;
  }

  @Override
  public CustomType customType() {
    if (baseType == null) {
      baseType = CustomTypes.of(aClass);
    }
    return baseType;
  }

  @Override
  public List<NotPrimitiveTypeReference> typeArguments() {
    return List.of();
  }

  @Override
  public CustomType effectiveStatement() {
    return baseType;
  }

  @Override
  public Map<String, NotPrimitiveTypeReference> typeArgumentMapping() {
    return Map.of();
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
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
