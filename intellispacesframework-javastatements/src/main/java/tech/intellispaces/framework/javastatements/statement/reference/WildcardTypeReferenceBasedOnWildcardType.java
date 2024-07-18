package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.type.WildcardType;
import java.util.Map;
import java.util.Optional;

/**
 * Adapter of {@link WildcardType} to {@link WildcardTypeReference}.
 */
class WildcardTypeReferenceBasedOnWildcardType extends AbstractTypeReference implements WildcardTypeReference {
  private final Getter<Optional<TypeBoundReference>> extendedBoundGetter;
  private final Getter<Optional<TypeBoundReference>> superBoundGetter;

  WildcardTypeReferenceBasedOnWildcardType(WildcardType wildcardType, TypeContext typeContext, Session session) {
    super();
    this.extendedBoundGetter = Actions.cachedLazyGetter(TypeElementFunctions::getExtendedBound, wildcardType, typeContext, session);
    this.superBoundGetter = Actions.cachedLazyGetter(TypeElementFunctions::getSuperBound, wildcardType, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardTypeReference;
  }

  @Override
  public Optional<TypeBoundReference> extendedBound() {
    return extendedBoundGetter.get();
  }

  @Override
  public Optional<TypeBoundReference> superBound() {
    return superBoundGetter.get();
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    TypeBoundReference extendedBound = extendedBound().orElse(null);
    if (extendedBound != null) {
      extendedBound = (TypeBoundReference) extendedBound.specify(typeMapping);
    }
    TypeBoundReference superBound = superBound().orElse(null);
    if (superBound != null) {
      superBound = (TypeBoundReference) superBound.specify(typeMapping);
    }
    return new WildcardTypeReferenceImpl(extendedBound, superBound);
  }
}
