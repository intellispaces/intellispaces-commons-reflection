package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.customtype.CustomTypes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter of {@link Class} to {@link CustomTypeReference}.
 */
class CustomTypeReferenceBasedOnClass extends AbstractTypeReference implements CustomTypeReference {
  private final Class<?> baseClass;
  private CustomType baseType;

  CustomTypeReferenceBasedOnClass(Class<?> baseClass) {
    this.baseClass = baseClass;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomReference;
  }

  @Override
  public CustomType targetType() {
    if (baseType == null) {
      baseType = CustomTypes.of(baseClass);
    }
    return baseType;
  }

  @Override
  public Class<?> targetClass() {
    return baseClass;
  }

  @Override
  public List<NotPrimitiveReference> typeArguments() {
    return List.of();
  }

  @Override
  public CustomType effectiveStatement() {
    return baseType;
  }

  @Override
  public Map<String, NotPrimitiveReference> typeArgumentMapping() {
    return Map.of();
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveReference> typeMapping) {
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
