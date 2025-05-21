package tech.intellispaces.javareflection.customtype;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.javareflection.StatementType;
import tech.intellispaces.javareflection.StatementTypes;
import tech.intellispaces.javareflection.common.JavaModelFunctions;
import tech.intellispaces.javareflection.context.TypeContext;
import tech.intellispaces.javareflection.method.MethodStatement;
import tech.intellispaces.javareflection.reference.CustomTypeReference;
import tech.intellispaces.javareflection.session.Session;

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
