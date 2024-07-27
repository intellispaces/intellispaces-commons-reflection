package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter od {@link DeclaredType} to {@link CustomType}.
 */
class CustomTypeReferenceBasedOnDeclaredType extends AbstractCustomType {
  private final Getter<CustomStatement> satementGetter;
  private final Getter<List<NonPrimitiveType>> typeArgumentsGetter;
  private final Getter<Map<String, NonPrimitiveType>> typeArgumentMappingsGetter;
  private final Getter<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceBasedOnDeclaredType(DeclaredType declaredType, TypeContext typeContext, Session session) {
    super();
    TypeElement typeElement = (TypeElement) declaredType.asElement();
    this.satementGetter = Actions.cachedLazyGetter(TypeElementFunctions::asCustomStatement, typeElement, session);
    this.typeArgumentsGetter = Actions.cachedLazyGetter(TypeElementFunctions::getTypeArguments, declaredType, typeContext, session);
    this.typeArgumentMappingsGetter = Actions.cachedLazyGetter(TypeFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = Actions.cachedLazyGetter(TypeFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomType;
  }

  @Override
  public CustomStatement statement() {
    return satementGetter.get();
  }

  @Override
  public List<NonPrimitiveType> typeArguments() {
    return typeArgumentsGetter.get();
  }

  @Override
  public Map<String, NonPrimitiveType> typeArgumentMapping() {
    return typeArgumentMappingsGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration() {
    return typeArgumentsDeclarationGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration(Function<String, String> simpleNameMapper) {
    return TypeFunctions.getTypeArgumentsDeclaration(this, simpleNameMapper);
  }
}
