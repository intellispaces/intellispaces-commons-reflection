package intellispaces.common.javastatements.customtype;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatements.StatementType;
import intellispaces.common.javastatements.StatementTypes;
import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.reference.CustomTypeReference;
import intellispaces.common.javastatements.common.JavaModelFunctions;
import intellispaces.common.javastatements.method.MethodStatement;
import intellispaces.common.javastatements.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

/**
 * Adapter of {@link TypeElement} to {@link ClassType}.
 */
class ClassBasedOnTypeElement extends AbstractCustomTypeStatementBasedOnTypeElement implements ClassType {
  private final Getter<List<MethodStatement>> constructorGetter;
  private final Getter<Optional<CustomTypeReference>> extendedClassGetter;
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  ClassBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.constructorGetter = Actions.cachedLazyGetter(JavaModelFunctions::getConstructors, typeElement, this, customTypeContext(), session);
    this.extendedClassGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getExtendedClass, this);
    this.implementedInterfacesGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Class;
  }

  @Override
  public List<MethodStatement> constructors() {
    return constructorGetter.get();
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    return extendedClassGetter.get();
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
