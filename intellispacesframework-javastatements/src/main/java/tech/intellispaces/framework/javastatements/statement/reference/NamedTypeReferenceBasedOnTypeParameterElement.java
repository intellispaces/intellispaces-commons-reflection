package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.JavaStatements;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.Statement;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;

import javax.lang.model.element.TypeParameterElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link TypeParameterElement} to {@link NamedTypeReference}.
 */
class NamedTypeReferenceBasedOnTypeParameterElement extends AbstractTypeReference implements NamedTypeReference {
  private final String name;
  private final Getter<Statement> ownerGetter;
  private final Getter<List<TypeBoundReference>> extendedBoundsGetter;

  NamedTypeReferenceBasedOnTypeParameterElement(
      TypeParameterElement typeParameter, TypeContext typeContext, Session session
  ) {
    super();
    ownerGetter = Actions.cachedLazyGetter(JavaStatements::statement, typeParameter.getGenericElement());
    this.name = typeParameter.getSimpleName().toString();
    this.extendedBoundsGetter = Actions.cachedLazyGetter(
        TypeElementFunctions::getExtendedBounds, typeParameter, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.NamedTypeReference;
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
  public List<TypeBoundReference> extendedBounds() {
    return extendedBoundsGetter.get();
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    TypeReference specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<TypeBoundReference> curExtendedBounds = extendedBounds();
    List<TypeBoundReference> newExtendedBounds = new ArrayList<>();
    for (TypeBoundReference curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((TypeBoundReference) curExtendedBound.specify(typeMapping));
    }
    return new NamedTypeReferenceImpl(name, owner(), newExtendedBounds);
  }
}
