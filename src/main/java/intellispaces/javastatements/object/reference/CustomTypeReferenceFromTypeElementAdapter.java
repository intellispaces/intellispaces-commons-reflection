package intellispaces.javastatements.object.reference;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.ElementFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;

class CustomTypeReferenceFromTypeElementAdapter extends AbstractTypeReference implements CustomTypeReference {
  private final GetterAction<CustomType> targetTypeGetter;

  CustomTypeReferenceFromTypeElementAdapter(TypeElement typeElement, NameContext nameContext, Session session) {
    super();
    this.targetTypeGetter = ActionFunctions.cachedLazyGetter(ElementFunctions::asCustomTypeStatement, typeElement, session);
  }

  @Override
  public CustomType targetType() {
    return targetTypeGetter.execute();
  }

  @Override
  public List<NonPrimitiveTypeReference> typeArguments() {
    return List.of();
  }
}
