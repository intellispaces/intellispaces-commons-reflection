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
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter of {@link TypeElement} to {@link CustomTypeReference}.
 */
class CustomTypeReferenceBasedOnTypeElementReference extends AbstractCustomTypeReference {
  private final Getter<CustomType> baseTypeGetter;

  CustomTypeReferenceBasedOnTypeElementReference(TypeElement typeElement, TypeContext typeContext, Session session) {
    super();
    this.baseTypeGetter = Actions.cachedLazyGetter(TypeElementFunctions::asCustomStatement, typeElement, session);
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
    return List.of();
  }

  @Override
  public Map<String, NotPrimitiveTypeReference> typeArgumentMapping() {
    return Map.of();
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
