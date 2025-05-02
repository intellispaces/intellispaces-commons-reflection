package tech.intellispaces.statementsj.reference;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.type.ClassFunctions;
import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.common.JavaModelFunctions;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.customtype.CustomType;
import tech.intellispaces.statementsj.session.Session;

/**
 * Adapter od {@link DeclaredType} to {@link CustomTypeReference}.
 */
class CustomTypeReferenceBasedOnDeclaredType extends AbstractCustomTypeReference {
  private final SupplierAction<CustomType> targetTypeGetter;
  private final SupplierAction<List<NotPrimitiveReference>> typeArgumentsGetter;
  private final SupplierAction<Map<String, NotPrimitiveReference>> typeArgumentMappingsGetter;
  private final SupplierAction<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceBasedOnDeclaredType(DeclaredType declaredType, TypeContext typeContext, Session session) {
    super();
    TypeElement typeElement = (TypeElement) declaredType.asElement();
    this.targetTypeGetter = CachedSupplierActions.get(JavaModelFunctions::asCustomStatement, typeElement, session);
    this.typeArgumentsGetter = CachedSupplierActions.get(JavaModelFunctions::getTypeArguments, declaredType, typeContext, session);
    this.typeArgumentMappingsGetter = CachedSupplierActions.get(TypeReferenceFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = CachedSupplierActions.get(TypeReferenceFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomReference;
  }

  @Override
  public CustomType targetType() {
    return targetTypeGetter.get();
  }

  @Override
  public Class<?> targetClass() {
    return ClassFunctions.getClass(targetType().canonicalName()).orElseThrow();
  }

  @Override
  public List<NotPrimitiveReference> typeArguments() {
    return typeArgumentsGetter.get();
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
