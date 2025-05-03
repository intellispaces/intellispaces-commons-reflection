package tech.intellispaces.jstatements.method;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.customtype.CustomType;
import tech.intellispaces.jstatements.customtype.CustomTypes;
import tech.intellispaces.jstatements.reference.NotPrimitiveReference;

/**
 * Adapter of {@link Method} to {@link MethodStatement}.
 */
class MethodStatementBasedOnLangMethod implements MethodStatement {
  private final CustomType owner;
  private final SupplierAction<MethodSignature> signatureGetter;
  private final SupplierAction<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementBasedOnLangMethod(Method method) {
    this.owner = CustomTypes.of(method.getDeclaringClass());
    this.signatureGetter = CachedSupplierActions.get(MethodSignatures::get, method);
    this.overrideMethodsGetter = CachedSupplierActions.get(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType owner() {
    return owner;
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
  public MethodStatement effective(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodStatementImpl(owner(), signature().effective(typeMapping));
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("+JcDAQ");
  }
}
