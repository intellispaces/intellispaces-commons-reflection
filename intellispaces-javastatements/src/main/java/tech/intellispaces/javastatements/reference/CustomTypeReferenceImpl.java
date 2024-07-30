package tech.intellispaces.javastatements.reference;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.commons.type.TypeFunctions;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.customtype.CustomType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

class CustomTypeReferenceImpl extends AbstractCustomTypeReference {
  private final CustomType targetType;
  private final List<NotPrimitiveReference> typeArguments;
  private final Getter<Map<String, NotPrimitiveReference>> typeArgumentMappingsGetter;
  private final Getter<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceImpl(CustomType targetType, List<NotPrimitiveReference> typeArguments) {
    super();
    this.targetType = targetType;
    this.typeArguments = typeArguments;
    this.typeArgumentMappingsGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomReference;
  }

  @Override
  public CustomType targetType() {
    return targetType;
  }

  @Override
  public Class<?> targetClass() {
    return TypeFunctions.getClass(targetType.canonicalName()).orElseThrow();
  }

  @Override
  public List<NotPrimitiveReference> typeArguments() {
    return typeArguments;
  }

  @Override
  public Map<String, NotPrimitiveReference> typeArgumentMapping() {
    return typeArgumentMappingsGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration() {
    return typeArgumentsDeclarationGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration(Function<String, String> simpleNameMapper) {
    return TypeReferenceFunctions.getTypeArgumentsDeclaration(this, simpleNameMapper);
  }
}
