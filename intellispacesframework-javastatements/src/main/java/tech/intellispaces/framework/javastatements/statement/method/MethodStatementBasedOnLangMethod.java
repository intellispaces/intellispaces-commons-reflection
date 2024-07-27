package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.type.NonPrimitiveType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link Method} to {@link MethodStatement}.
 */
class MethodStatementBasedOnLangMethod implements MethodStatement {
  private final Getter<MethodSignature> signatureGetter;
  private final Getter<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementBasedOnLangMethod(Method method) {
    this.signatureGetter = Actions.cachedLazyGetter(MethodSignatures::of, method);
    this.overrideMethodsGetter = Actions.cachedLazyGetter(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomStatement owner() {
    throw new RuntimeException("Not implemented");
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
  public MethodStatement specify(Map<String, NonPrimitiveType> typeMapping) {
    return new MethodStatementImpl(owner(), signature().specify(typeMapping));
  }
}
