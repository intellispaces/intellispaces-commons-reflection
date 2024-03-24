package intellispaces.javastatements.object.reference;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.TypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.TypeBoundReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeParameterElement;
import java.util.List;

class NamedTypeReferenceAdapter extends AbstractTypeReference implements NamedTypeReference {
  private final String name;
  private final GetterAction<List<TypeBoundReference>> extendedBoundsGetter;

  NamedTypeReferenceAdapter(TypeParameterElement typeParameter, NameContext nameContext, Session session) {
    super();
    this.name = typeParameter.getSimpleName().toString();
    this.extendedBoundsGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getExtendedBounds, typeParameter, nameContext, session);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public List<TypeBoundReference> extendedBounds() {
    return extendedBoundsGetter.execute();
  }
}
