package tech.intellispaces.commons.java.reflection.reference;

import tech.intellispaces.commons.action.cache.CachedSupplierActions;
import tech.intellispaces.commons.action.supplier.SupplierAction;
import tech.intellispaces.commons.base.type.ClassFunctions;
import tech.intellispaces.commons.java.reflection.StatementType;
import tech.intellispaces.commons.java.reflection.StatementTypes;
import tech.intellispaces.commons.java.reflection.common.JavaModelFunctions;
import tech.intellispaces.commons.java.reflection.context.TypeContext;
import tech.intellispaces.commons.java.reflection.customtype.CustomType;
import tech.intellispaces.commons.java.reflection.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
