package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.JavaStatements;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.Statement;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.element.TypeParameterElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link TypeParameterElement} to {@link NamedType}.
 */
class NamedTypeReferenceBasedOnTypeParameterElement extends AbstractType implements NamedType {
  private final String name;
  private final Getter<Statement> ownerGetter;
  private final Getter<List<TypeBound>> extendedBoundsGetter;

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
    return StatementTypes.NamedType;
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
  public List<TypeBound> extendedBounds() {
    return extendedBoundsGetter.get();
  }

  @Override
  public Type specify(Map<String, NonPrimitiveType> typeMapping) {
    Type specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<TypeBound> curExtendedBounds = extendedBounds();
    List<TypeBound> newExtendedBounds = new ArrayList<>();
    for (TypeBound curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((TypeBound) curExtendedBound.specify(typeMapping));
    }
    return new NamedTypeImpl(name, owner(), newExtendedBounds);
  }
}
