package intellispaces.javastatements.object.custom;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.CustomTypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.ClassStatement;
import intellispaces.javastatements.model.reference.CustomTypeReference;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

class TypeElementToClassStatementAdapter extends CustomTypeStatementAdapter implements ClassStatement {
  private final GetterAction<Optional<CustomTypeReference>> extendedClassGetter;
  private final GetterAction<List<CustomTypeReference>> implementedInterfacesGetter;

  TypeElementToClassStatementAdapter(TypeElement typeElement, NameContext nameContext, Session session) {
    super(typeElement, nameContext, session);
    this.extendedClassGetter = ActionFunctions.cachedLazyGetter(CustomTypeFunctions::getExtendedClass, this);
    this.implementedInterfacesGetter = ActionFunctions.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    return extendedClassGetter.execute();
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.execute();
  }
}
