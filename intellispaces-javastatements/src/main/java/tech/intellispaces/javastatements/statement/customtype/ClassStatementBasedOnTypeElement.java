package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.javastatements.statement.method.MethodStatement;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

/**
 * Adapter of {@link TypeElement} to {@link ClassType}.
 */
class ClassStatementBasedOnTypeElement extends AbstractCustomTypeStatementBasedOnTypeElement implements ClassType {
  private final Getter<List<MethodStatement>> constructorGetter;
  private final Getter<Optional<CustomTypeReference>> extendedClassGetter;
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  ClassStatementBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.constructorGetter = Actions.cachedLazyGetter(TypeElementFunctions::getConstructors, typeElement, this, customTypeContext(), session);
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
