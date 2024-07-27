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
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter of {@link TypeElement} to {@link CustomType}.
 */
class CustomTypeReferenceBasedOnTypeElement extends AbstractCustomType {
  private final Getter<CustomStatement> targetTypeGetter;

  CustomTypeReferenceBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super();
    this.targetTypeGetter = Actions.cachedLazyGetter(TypeElementFunctions::asCustomStatement, typeElement, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomType;
  }

  @Override
  public CustomStatement statement() {
    return targetTypeGetter.get();
  }

  @Override
  public List<NonPrimitiveType> typeArguments() {
    return List.of();
  }

  @Override
  public Map<String, NonPrimitiveType> typeArgumentMapping() {
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
