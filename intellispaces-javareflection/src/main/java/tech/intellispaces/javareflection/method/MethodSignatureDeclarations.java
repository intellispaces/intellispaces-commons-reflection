package tech.intellispaces.javareflection.method;

public interface MethodSignatureDeclarations {

  static MethodSignatureDeclarationBuilderBasedOnMethodPrototype build(MethodStatement method) {
    return new MethodSignatureDeclarationBuilderBasedOnMethodPrototype(method);
  }
}
