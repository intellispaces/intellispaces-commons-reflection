package intellispaces.javastatements.object.custom;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.CustomTypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.EnumStatement;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;

class EnumStatementAdapter extends CustomTypeStatementAdapter implements EnumStatement {
  private final GetterAction<List<CustomTypeReference>> implementedInterfacesGetter;

  EnumStatementAdapter(TypeElement typeElement, NameContext nameContext, Session session) {
    super(typeElement, nameContext, session);
    this.implementedInterfacesGetter = ActionFunctions.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.execute();
  }
}
