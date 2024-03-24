package intellispaces.javastatements.object.reference;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.TypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.ArrayTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.type.ArrayType;

class ArrayTypeReferenceAdapter extends AbstractTypeReference implements ArrayTypeReference {
  private final GetterAction<TypeReference> elementTypeGetter;

  ArrayTypeReferenceAdapter(ArrayType arrayType, NameContext nameContext, Session session) {
    super();
    this.elementTypeGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getTypeReference, arrayType.getComponentType(), nameContext, session);
  }

  @Override
  public TypeReference elementType() {
    return elementTypeGetter.execute();
  }
}
