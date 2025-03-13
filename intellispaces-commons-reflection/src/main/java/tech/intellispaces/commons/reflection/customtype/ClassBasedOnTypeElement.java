package tech.intellispaces.commons.reflection.customtype;

import tech.intellispaces.commons.action.cache.CachedSupplierActions;
import tech.intellispaces.commons.action.supplier.SupplierAction;
import tech.intellispaces.commons.reflection.StatementType;
import tech.intellispaces.commons.reflection.StatementTypes;
import tech.intellispaces.commons.reflection.common.JavaModelFunctions;
import tech.intellispaces.commons.reflection.context.TypeContext;
import tech.intellispaces.commons.reflection.method.MethodStatement;
import tech.intellispaces.commons.reflection.reference.CustomTypeReference;
import tech.intellispaces.commons.reflection.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

/**
 * Adapter of {@link TypeElement} to {@link ClassType}.
 */
class ClassBasedOnTypeElement extends AbstractCustomTypeStatementBasedOnTypeElement implements ClassType {
  private final SupplierAction<List<MethodStatement>> constructorGetter;
  private final SupplierAction<Optional<CustomTypeReference>> extendedClassGetter;
  private final SupplierAction<List<CustomTypeReference>> implementedInterfacesGetter;

  ClassBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.constructorGetter = CachedSupplierActions.get(JavaModelFunctions::getConstructors, typeElement, this, customTypeContext(), session);
    this.extendedClassGetter = CachedSupplierActions.get(CustomTypeFunctions::getExtendedClass, this);
    this.implementedInterfacesGetter = CachedSupplierActions.get(CustomTypeFunctions::getImplementedInterfaces, this);
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
