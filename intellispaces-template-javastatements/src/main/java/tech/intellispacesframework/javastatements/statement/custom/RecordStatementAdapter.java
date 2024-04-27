package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;

class RecordStatementAdapter extends CustomTypeStatementAdapter implements RecordStatement {
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  RecordStatementAdapter(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.implementedInterfacesGetter = ActionBuilders.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Record;
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
