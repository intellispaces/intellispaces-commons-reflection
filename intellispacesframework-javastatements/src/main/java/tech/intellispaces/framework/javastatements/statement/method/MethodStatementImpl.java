package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;

import java.util.List;
import java.util.Map;

class MethodStatementImpl implements MethodStatement {
  private final CustomStatement owner;
  private final MethodSignature signature;
  private final Getter<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementImpl(CustomStatement owner, MethodSignature signature) {
    this.owner = owner;
    this.signature = signature;
    this.overrideMethodsGetter = Actions.cachedLazyGetter(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomStatement owner() {
    return owner;
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
  public MethodStatement specify(Map<String, NotPrimitiveType> typeMapping) {
    return new MethodStatementImpl(owner(), signature().specify(typeMapping));
  }
}
