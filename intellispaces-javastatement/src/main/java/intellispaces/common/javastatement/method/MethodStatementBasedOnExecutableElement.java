package intellispaces.common.javastatement.method;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.base.exception.NotImplementedExceptions;
import intellispaces.common.javastatement.JavaStatements;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.context.TypeContexts;
import intellispaces.common.javastatement.customtype.CustomType;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link ExecutableElement} to {@link MethodStatement}.
 */
class MethodStatementBasedOnExecutableElement implements MethodStatement {
  private final Getter<CustomType> ownerGetter;
  private final Getter<MethodSignature> signatureGetter;
  private final Getter<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementBasedOnExecutableElement(ExecutableElement executableElement, Session session) {
    this(
        executableElement,
        Actions.cachedLazyGetter(
            JavaStatements::customTypeStatement, (TypeElement) executableElement.getEnclosingElement()),
        TypeContexts.empty(),
        session
    );
  }

  MethodStatementBasedOnExecutableElement(
      ExecutableElement executableElement, CustomType owner, TypeContext typeContext, Session session
  ) {
    this(executableElement, Actions.getter(owner), typeContext, session);
  }

  MethodStatementBasedOnExecutableElement(
      ExecutableElement executableElement, Getter<CustomType> ownerGetter, TypeContext typeContext, Session session
  ) {
    this.ownerGetter = ownerGetter;
    this.signatureGetter = Actions.cachedLazyGetter(
        MethodSignatures::of, executableElement, typeContext, session);
    this.overrideMethodsGetter = Actions.cachedLazyGetter(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType owner() {
    return ownerGetter.get();
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
    throw NotImplementedExceptions.withCode("QLOEfw");
  }
}
