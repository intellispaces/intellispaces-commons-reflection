package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.ClassFunctions;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

class ClassToCustomTypeReferenceAdapter extends AbstractTypeReference implements CustomTypeReference {
  private final Class<?> aClass;
  private CustomType targetType;

  public ClassToCustomTypeReferenceAdapter(Class<?> aClass) {
    this.aClass = aClass;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomTypeReference;
  }

  @Override
  public CustomType targetType() {
    if (targetType == null) {
      targetType = ClassFunctions.asCustomTypeStatement(aClass);
    }
    return targetType;
  }

  @Override
  public List<NonPrimitiveTypeReference> typeArguments() {
    return List.of();
  }

  @Override
  public CustomType effectiveTargetType() {
    return targetType;
  }

  @Override
  public Map<String, NonPrimitiveTypeReference> typeArgumentMapping() {
    return Map.of();
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
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
