package tech.intellispaces.commons.java.reflection.method;

import tech.intellispaces.commons.action.cache.CachedSupplierActions;
import tech.intellispaces.commons.action.supplier.SupplierAction;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.commons.java.reflection.StatementType;
import tech.intellispaces.commons.java.reflection.StatementTypes;
import tech.intellispaces.commons.java.reflection.customtype.CustomType;
import tech.intellispaces.commons.java.reflection.reference.NotPrimitiveReference;

import java.util.List;
import java.util.Map;

class MethodStatementImpl implements MethodStatement {
  private final CustomType owner;
  private final MethodSignature signature;
  private final SupplierAction<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementImpl(CustomType owner, MethodSignature signature) {
    this.owner = owner;
    this.signature = signature;
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
    return signature;
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
    throw NotImplementedExceptions.withCode("nyHkqQ");
  }
}
