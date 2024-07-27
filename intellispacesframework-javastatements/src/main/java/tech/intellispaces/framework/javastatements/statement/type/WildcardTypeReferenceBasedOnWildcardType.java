package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;

import java.util.Map;
import java.util.Optional;

/**
 * Adapter of {@link javax.lang.model.type.WildcardType} to {@link WildcardType}.
 */
class WildcardTypeReferenceBasedOnWildcardType extends AbstractType implements WildcardType {
  private final Getter<Optional<TypeBound>> extendedBoundGetter;
  private final Getter<Optional<TypeBound>> superBoundGetter;

  WildcardTypeReferenceBasedOnWildcardType(javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session) {
    super();
    this.extendedBoundGetter = Actions.cachedLazyGetter(TypeElementFunctions::getExtendedBound, wildcardType, typeContext, session);
    this.superBoundGetter = Actions.cachedLazyGetter(TypeElementFunctions::getSuperBound, wildcardType, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardType;
  }

  @Override
  public Optional<TypeBound> extendedBound() {
    return extendedBoundGetter.get();
  }

  @Override
  public Optional<TypeBound> superBound() {
    return superBoundGetter.get();
  }

  @Override
  public Type specify(Map<String, NonPrimitiveType> typeMapping) {
    TypeBound extendedBound = extendedBound().orElse(null);
    if (extendedBound != null) {
      extendedBound = (TypeBound) extendedBound.specify(typeMapping);
    }
    TypeBound superBound = superBound().orElse(null);
    if (superBound != null) {
      superBound = (TypeBound) superBound.specify(typeMapping);
    }
    return new WildcardTypeImpl(extendedBound, superBound);
  }
}
