package intellispaces.javastatements.object.reference;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.TypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.TypeBoundReference;
import intellispaces.javastatements.model.reference.WildcardTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.type.WildcardType;
import java.util.Optional;

class WildcardTypeReferenceAdapter extends AbstractTypeReference implements WildcardTypeReference {
  private final GetterAction<Optional<TypeBoundReference>> extendedBoundGetter;
  private final GetterAction<Optional<TypeBoundReference>> superBoundGetter;

  WildcardTypeReferenceAdapter(WildcardType wildcardType, NameContext nameContext, Session session) {
    super();
    this.extendedBoundGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getExtendedBound, wildcardType, nameContext, session);
    this.superBoundGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getSuperBound, wildcardType, nameContext, session);
  }

  @Override
  public Optional<TypeBoundReference> extendedBound() {
    return extendedBoundGetter.execute();
  }

  @Override
  public Optional<TypeBoundReference> superBound() {
    return superBoundGetter.execute();
  }
}
