package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.instance.AnnotationInstance;
import tech.intellispaces.javastatements.method.MethodSignature;
import tech.intellispaces.javastatements.method.MethodStatement;
import tech.intellispaces.javastatements.method.Methods;
import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.reference.NamedReference;

import java.util.ArrayList;
import java.util.List;

public class InterfaceBuilderBasedOnPrototype {
  private String canonicalName;
  private List<AnnotationInstance> annotations;
  private List<NamedReference> typeParameters;
  private List<CustomTypeReference> extendedInterfaces;
  private List<MethodStatement> declaredMethods;
  private InterfaceImpl resultInterface;

  InterfaceBuilderBasedOnPrototype(InterfaceType prototype) {
    this.resultInterface = new InterfaceImpl();
    this.canonicalName = prototype.canonicalName();
    this.annotations = new ArrayList<>(prototype.annotations());
    this.typeParameters = new ArrayList<>(prototype.typeParameters());
    this.extendedInterfaces = new ArrayList<>(prototype.extendedInterfaces());
    this.declaredMethods = new ArrayList<>(prototype.declaredMethods());
  }

  public InterfaceBuilderBasedOnPrototype canonicalName(String canonicalName) {
    this.canonicalName = canonicalName;
    return this;
  }

  public InterfaceBuilderBasedOnPrototype extendedInterfaces(List<CustomTypeReference> extendedInterfaces) {
    this.extendedInterfaces = extendedInterfaces;
    return this;
  }

  public InterfaceBuilderBasedOnPrototype addDeclaredMethod(CustomType owner, MethodSignature signature) {
    declaredMethods.add(Methods.get(owner, signature));
    return this;
  }

  public InterfaceBuilderBasedOnPrototype addDeclaredMethod(MethodSignature signature) {
    addDeclaredMethod(resultInterface, signature);
    return this;
  }

  public InterfaceBuilderBasedOnPrototype addDeclaredMethod(MethodStatement method) {
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
