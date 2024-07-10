package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import java.util.List;
import java.util.Map;

class MethodStatementImpl implements MethodStatement {
  private final CustomType holder;
  private final MethodSignature signature;
  private final Getter<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementImpl(CustomType holder, MethodSignature signature) {
    this.holder = holder;
    this.signature = signature;
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
    return signature;
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
