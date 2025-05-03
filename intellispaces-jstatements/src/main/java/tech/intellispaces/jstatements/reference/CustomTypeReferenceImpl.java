package tech.intellispaces.jstatements.reference;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.exception.UnexpectedExceptions;
import tech.intellispaces.commons.type.ClassFunctions;
import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.customtype.CustomType;

class CustomTypeReferenceImpl extends AbstractCustomTypeReference {
  private final CustomType targetType;
  private final List<NotPrimitiveReference> typeArguments;
  private final SupplierAction<Map<String, NotPrimitiveReference>> typeArgumentMappingsGetter;
  private final SupplierAction<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceImpl(CustomType targetType, List<NotPrimitiveReference> typeArguments) {
    super();
    this.targetType = targetType;
    this.typeArguments = typeArguments;
    this.typeArgumentMappingsGetter = CachedSupplierActions.get(TypeReferenceFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = CachedSupplierActions.get(TypeReferenceFunctions::getTypeArgumentsDeclaration, this);
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
    return ClassFunctions.getClass(targetType.canonicalName()).orElseThrow(() -> UnexpectedExceptions.withMessage(
        "Cannot to get class by name {0}", targetType.canonicalName()
    ));
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

  @Override
  public String toString() {
    return targetType.canonicalName();
  }
}
