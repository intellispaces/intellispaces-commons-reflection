package tech.intellispaces.javastatements.method;

public interface MethodSignatureDeclarations {

  static MethodSignatureDeclarationBuilderBasedOnMethodPrototype build(MethodStatement method) {
    return new MethodSignatureDeclarationBuilderBasedOnMethodPrototype(method);
  }
}
