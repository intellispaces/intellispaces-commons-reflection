package intellispaces.common.javastatements.reference;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatements.JavaStatements;
import intellispaces.common.javastatements.Statement;
import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.common.JavaModelFunctions;
import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.session.Session;

import javax.lang.model.element.TypeParameterElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link TypeParameterElement} to {@link NamedReference}.
 */
class NamedReferenceBasedOnTypeParameterElement extends AbstractTypeReference implements NamedReference {
  private final String name;
  private final Getter<Statement> ownerGetter;
  private final Getter<List<ReferenceBound>> extendedBoundsGetter;

  NamedReferenceBasedOnTypeParameterElement(
      TypeParameterElement typeParameter, TypeContext typeContext, Session session
  ) {
    super();
    ownerGetter = Actions.cachedLazyGetter(JavaStatements::statement, typeParameter.getGenericElement());
    this.name = typeParameter.getSimpleName().toString();
    this.extendedBoundsGetter = Actions.cachedLazyGetter(
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
  public TypeReference specify(Map<String, NotPrimitiveReference> typeMapping) {
    TypeReference specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<ReferenceBound> curExtendedBounds = extendedBounds();
    List<ReferenceBound> newExtendedBounds = new ArrayList<>();
    for (ReferenceBound curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((ReferenceBound) curExtendedBound.specify(typeMapping));
    }
    return new NamedReferenceImpl(name, owner(), newExtendedBounds);
  }
}
