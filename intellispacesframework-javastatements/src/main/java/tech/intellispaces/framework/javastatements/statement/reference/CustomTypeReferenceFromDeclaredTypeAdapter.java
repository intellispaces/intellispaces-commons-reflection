package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class CustomTypeReferenceFromDeclaredTypeAdapter extends AbstractTypeReference implements CustomTypeReference {
  private final Getter<CustomType> targetTypeGetter;
  private final Getter<List<NonPrimitiveTypeReference>> typeArgumentsGetter;
  private final Getter<Map<String, NonPrimitiveTypeReference>> typeArgumentMappingsGetter;
  private final Getter<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceFromDeclaredTypeAdapter(DeclaredType declaredType, TypeContext typeContext, Session session) {
    super();
    TypeElement typeElement = (TypeElement) declaredType.asElement();
    this.targetTypeGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::asCustomTypeStatement, typeElement, session);
    this.typeArgumentsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getTypeArguments, declaredType, typeContext, session);
    this.typeArgumentMappingsGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomTypeReference;
  }

  @Override
  public CustomType targetType() {
    return targetTypeGetter.get();
  }

  @Override
  public List<NonPrimitiveTypeReference> typeArguments() {
    return typeArgumentsGetter.get();
  }

  @Override
  public Map<String, NonPrimitiveTypeReference> typeArgumentMapping() {
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
