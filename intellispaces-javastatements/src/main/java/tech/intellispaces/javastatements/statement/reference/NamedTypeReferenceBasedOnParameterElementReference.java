package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.JavaStatements;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.Statement;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.element.TypeParameterElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link TypeParameterElement} to {@link NamedReference}.
 */
class NamedTypeReferenceBasedOnParameterElementReference extends AbstractTypeReference implements NamedReference {
  private final String name;
  private final Getter<Statement> ownerGetter;
  private final Getter<List<TypeReferenceBound>> extendedBoundsGetter;

  NamedTypeReferenceBasedOnParameterElementReference(
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
  public List<TypeReferenceBound> extendedBounds() {
    return extendedBoundsGetter.get();
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    TypeReference specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<TypeReferenceBound> curExtendedBounds = extendedBounds();
    List<TypeReferenceBound> newExtendedBounds = new ArrayList<>();
    for (TypeReferenceBound curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((TypeReferenceBound) curExtendedBound.specify(typeMapping));
    }
    return new NamedReferenceImpl(name, owner(), newExtendedBounds);
  }
}
