package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;
import tech.intellispacesframework.javastatements.statement.reference.CustomTypeReference;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

class TypeElementToClassStatementAdapter extends CustomTypeStatementAdapter implements ClassStatement {
  private final Getter<List<MethodStatement>> constructorGetter;
  private final Getter<Optional<CustomTypeReference>> extendedClassGetter;
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  TypeElementToClassStatementAdapter(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.constructorGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getConstructors, typeElement, this, customTypeContext(), session);
    this.extendedClassGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getExtendedClass, this);
    this.implementedInterfacesGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
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
