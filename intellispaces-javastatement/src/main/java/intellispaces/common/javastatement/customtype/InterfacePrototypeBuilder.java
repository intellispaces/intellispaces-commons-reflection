package intellispaces.common.javastatement.customtype;

import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.method.MethodSignature;
import intellispaces.common.javastatement.method.MethodStatement;
import intellispaces.common.javastatement.method.Methods;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.reference.NamedReference;

import java.util.ArrayList;
import java.util.List;

public class InterfacePrototypeBuilder {
  private String canonicalName;
  private List<AnnotationInstance> annotations;
  private List<NamedReference> typeParameters;
  private List<CustomTypeReference> extendedInterfaces;
  private List<MethodStatement> declaredMethods;
  private InterfaceImpl resultInterface;

  InterfacePrototypeBuilder(InterfaceType prototype) {
    this.resultInterface = new InterfaceImpl();
    this.canonicalName = prototype.canonicalName();
    this.annotations = new ArrayList<>(prototype.annotations());
    this.typeParameters = new ArrayList<>(prototype.typeParameters());
    this.extendedInterfaces = new ArrayList<>(prototype.extendedInterfaces());
    this.declaredMethods = new ArrayList<>(prototype.declaredMethods());
  }

  public InterfacePrototypeBuilder canonicalName(String canonicalName) {
    this.canonicalName = canonicalName;
    return this;
  }

  public InterfacePrototypeBuilder extendedInterfaces(List<CustomTypeReference> extendedInterfaces) {
    this.extendedInterfaces = extendedInterfaces;
    return this;
  }

  public InterfacePrototypeBuilder addDeclaredMethod(CustomType owner, MethodSignature signature) {
    declaredMethods.add(Methods.get(owner, signature));
    return this;
  }

  public InterfacePrototypeBuilder addDeclaredMethod(MethodSignature signature) {
    addDeclaredMethod(resultInterface, signature);
    return this;
  }

  public InterfacePrototypeBuilder addDeclaredMethod(MethodStatement method) {
    addDeclaredMethod(method.owner(), method.signature());
    return this;
  }

  public InterfaceType get() {
    resultInterface.setCanonicalName(canonicalName);
    resultInterface.setAnnotations(List.copyOf(annotations));
    resultInterface.setTypeParameters(List.copyOf(typeParameters));
    resultInterface.setParentTypes(List.copyOf(extendedInterfaces));
    resultInterface.setDeclaredMethods(List.copyOf(declaredMethods));
    return resultInterface;
  }
}
