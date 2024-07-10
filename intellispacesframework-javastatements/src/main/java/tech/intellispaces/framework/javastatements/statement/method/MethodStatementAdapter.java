package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Map;

class MethodStatementAdapter implements MethodStatement {
  private final CustomType holder;
  private final Getter<MethodSignature> signatureGetter;
  private final Getter<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementAdapter(ExecutableElement executableElement, CustomType holder, TypeContext typeContext, Session session) {
    this.holder = holder;
    this.signatureGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::asMethodSignature, executableElement, typeContext, session);
    this.overrideMethodsGetter = ActionBuilders.cachedLazyGetter(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType holder() {
    return holder;
  }

  @Override
  public MethodSignature signature() {
    return signatureGetter.get();
  }

  @Override
  public List<MethodStatement> overrideMethods() {
    return overrideMethodsGetter.get();
  }

  @Override
  public MethodStatement specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    return new MethodStatementImpl(holder, signature().specify(typeMapping));
  }
}
