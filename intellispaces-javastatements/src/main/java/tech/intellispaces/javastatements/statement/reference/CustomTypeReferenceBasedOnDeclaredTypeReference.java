package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.javastatements.statement.customtype.CustomType;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter od {@link DeclaredType} to {@link CustomTypeReference}.
 */
class CustomTypeReferenceBasedOnDeclaredTypeReference extends AbstractCustomTypeReference {
  private final Getter<CustomType> baseTypeGetter;
  private final Getter<List<NotPrimitiveTypeReference>> typeArgumentsGetter;
  private final Getter<Map<String, NotPrimitiveTypeReference>> typeArgumentMappingsGetter;
  private final Getter<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceBasedOnDeclaredTypeReference(DeclaredType declaredType, TypeContext typeContext, Session session) {
    super();
    TypeElement typeElement = (TypeElement) declaredType.asElement();
    this.baseTypeGetter = Actions.cachedLazyGetter(TypeElementFunctions::asCustomStatement, typeElement, session);
    this.typeArgumentsGetter = Actions.cachedLazyGetter(TypeElementFunctions::getTypeArguments, declaredType, typeContext, session);
    this.typeArgumentMappingsGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomReference;
  }

  @Override
  public CustomType customType() {
    return baseTypeGetter.get();
  }

  @Override
  public List<NotPrimitiveTypeReference> typeArguments() {
    return typeArgumentsGetter.get();
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
