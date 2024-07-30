package tech.intellispaces.javastatements.reference;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.customtype.CustomType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

class CustomTypeReferenceImpl extends AbstractCustomTypeReference {
  private final CustomType baseType;
  private final List<NotPrimitiveTypeReference> typeArguments;
  private final Getter<Map<String, NotPrimitiveTypeReference>> typeArgumentMappingsGetter;
  private final Getter<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceImpl(CustomType baseType, List<NotPrimitiveTypeReference> typeArguments) {
    super();
    this.baseType = baseType;
    this.typeArguments = typeArguments;
    this.typeArgumentMappingsGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomReference;
  }

  @Override
  public CustomType customType() {
    return baseType;
  }

  @Override
  public List<NotPrimitiveTypeReference> typeArguments() {
    return typeArguments;
  }

  @Override
  public Map<String, NotPrimitiveTypeReference> typeArgumentMapping() {
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
