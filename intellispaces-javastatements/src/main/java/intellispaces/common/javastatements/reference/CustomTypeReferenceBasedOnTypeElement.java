package intellispaces.common.javastatements.reference;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.common.JavaModelFunctions;
import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.customtype.CustomType;
import intellispaces.common.javastatements.session.Session;
import intellispaces.common.type.TypeFunctions;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter of {@link TypeElement} to {@link CustomTypeReference}.
 */
class CustomTypeReferenceBasedOnTypeElement extends AbstractCustomTypeReference {
  private final Getter<CustomType> targetTypeGetter;

  CustomTypeReferenceBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super();
    this.targetTypeGetter = Actions.cachedLazyGetter(JavaModelFunctions::asCustomStatement, typeElement, session);
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
    return TypeFunctions.getClass(targetType().canonicalName()).orElseThrow();
  }

  @Override
  public List<NotPrimitiveReference> typeArguments() {
    return List.of();
  }

  @Override
  public Map<String, NotPrimitiveReference> typeArgumentMapping() {
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
