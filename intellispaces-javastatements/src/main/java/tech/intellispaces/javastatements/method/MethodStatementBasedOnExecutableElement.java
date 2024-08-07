package tech.intellispaces.javastatements.method;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.getter.Getter;
import tech.intellispaces.javastatements.JavaStatements;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.reference.NotPrimitiveReference;
import tech.intellispaces.javastatements.session.Session;

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
  public MethodStatement specify(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodStatementImpl(owner(), signature().specify(typeMapping));
  }
}
