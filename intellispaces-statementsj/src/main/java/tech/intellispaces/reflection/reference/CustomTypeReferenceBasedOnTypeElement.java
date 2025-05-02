package tech.intellispaces.statementsj.reference;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.lang.model.element.TypeElement;

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
 * Adapter of {@link TypeElement} to {@link CustomTypeReference}.
 */
class CustomTypeReferenceBasedOnTypeElement extends AbstractCustomTypeReference {
  private final SupplierAction<CustomType> targetTypeGetter;

  CustomTypeReferenceBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super();
    this.targetTypeGetter = CachedSupplierActions.get(JavaModelFunctions::asCustomStatement, typeElement, session);
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
