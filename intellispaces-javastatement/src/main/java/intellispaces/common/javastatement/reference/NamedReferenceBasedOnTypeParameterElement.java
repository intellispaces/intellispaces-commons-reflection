package intellispaces.common.javastatement.reference;

import tech.intellispaces.action.Actions;
import tech.intellispaces.action.cache.CacheActions;
import tech.intellispaces.action.supplier.SupplierAction;
import intellispaces.common.javastatement.JavaStatements;
import intellispaces.common.javastatement.Statement;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.common.JavaModelFunctions;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.TypeParameterElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link TypeParameterElement} to {@link NamedReference}.
 */
class NamedReferenceBasedOnTypeParameterElement extends AbstractTypeReference implements NamedReference {
  private final String name;
  private final SupplierAction<Statement> ownerGetter;
  private final SupplierAction<List<ReferenceBound>> extendedBoundsGetter;

  NamedReferenceBasedOnTypeParameterElement(
      TypeParameterElement typeParameter, TypeContext typeContext, Session session
  ) {
    super();
    ownerGetter = CacheActions.cachedLazySupplierAction(JavaStatements::statement, typeParameter.getGenericElement());
    this.name = typeParameter.getSimpleName().toString();
    this.extendedBoundsGetter = CacheActions.cachedLazySupplierAction(
        JavaModelFunctions::getExtendedBounds, typeParameter, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.NamedReference;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Statement owner() {
    return ownerGetter.get();
  }

  @Override
  public List<ReferenceBound> extendedBounds() {
    return extendedBoundsGetter.get();
  }

  @Override
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
    TypeReference specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<ReferenceBound> curExtendedBounds = extendedBounds();
    List<ReferenceBound> newExtendedBounds = new ArrayList<>();
    for (ReferenceBound curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((ReferenceBound) curExtendedBound.effective(typeMapping));
    }
    return new NamedReferenceImpl(name, owner(), newExtendedBounds);
  }
}
