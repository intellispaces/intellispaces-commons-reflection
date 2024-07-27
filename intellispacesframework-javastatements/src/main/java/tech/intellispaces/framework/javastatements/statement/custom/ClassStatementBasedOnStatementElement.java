package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

/**
 * Adapter of {@link TypeElement} to {@link ClassStatement}.
 */
class ClassStatementBasedOnStatementElement extends AbstractCustomTypeStatementBasedOnStatementElement implements ClassStatement {
  private final Getter<List<MethodStatement>> constructorGetter;
  private final Getter<Optional<CustomType>> extendedClassGetter;
  private final Getter<List<CustomType>> implementedInterfacesGetter;

  ClassStatementBasedOnStatementElement(TypeElement typeElement, TypeContext typeContext, Session session) {
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
  public Optional<CustomType> extendedClass() {
    return extendedClassGetter.get();
  }

  @Override
  public List<CustomType> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
