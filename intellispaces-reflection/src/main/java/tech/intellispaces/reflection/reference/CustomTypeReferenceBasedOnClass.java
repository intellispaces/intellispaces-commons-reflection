package tech.intellispaces.reflection.reference;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.customtype.CustomType;
import tech.intellispaces.reflection.customtype.CustomTypes;

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
  public CustomType effectiveTargetType() {
    return baseType;
  }

  @Override
  public Map<String, NotPrimitiveReference> typeArgumentMapping() {
    return Map.of();
  }

  @Override
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
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

  @Override
  public boolean isVoidType() {
    return false;
  }
}
