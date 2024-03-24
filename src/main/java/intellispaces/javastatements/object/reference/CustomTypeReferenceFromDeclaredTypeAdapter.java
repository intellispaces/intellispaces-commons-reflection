package intellispaces.javastatements.object.reference;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.ElementFunctions;
import intellispaces.javastatements.function.TypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;

class CustomTypeReferenceFromDeclaredTypeAdapter extends AbstractTypeReference implements CustomTypeReference {
  private final GetterAction<CustomType> targetTypeGetter;
  private final GetterAction<List<NonPrimitiveTypeReference>> typeArgumentsGetter;

  CustomTypeReferenceFromDeclaredTypeAdapter(DeclaredType declaredType, NameContext nameContext, Session session) {
    super();
    TypeElement typeElement = (TypeElement) declaredType.asElement();
    this.targetTypeGetter = ActionFunctions.cachedLazyGetter(ElementFunctions::asCustomTypeStatement, typeElement, session);
    this.typeArgumentsGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getTypeArguments, declaredType, nameContext, session);
  }

  @Override
  public CustomType targetType() {
    return targetTypeGetter.execute();
  }

  @Override
  public List<NonPrimitiveTypeReference> typeArguments() {
    return typeArgumentsGetter.execute();
  }
}
