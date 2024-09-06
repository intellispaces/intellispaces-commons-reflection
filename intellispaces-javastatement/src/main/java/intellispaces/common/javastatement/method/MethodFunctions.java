package intellispaces.common.javastatement.method;

import intellispaces.common.javastatement.JavaStatements;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.customtype.CustomType;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.reference.ThrowableReference;
import intellispaces.common.javastatement.common.JavaModelFunctions;
import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.instance.AnnotationInstances;
import intellispaces.common.javastatement.instance.Instance;
import intellispaces.common.javastatement.instance.InstanceFunctions;
import intellispaces.common.javastatement.reference.TypeReference;
import intellispaces.common.javastatement.reference.TypeReferenceFunctions;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MethodFunctions {

  static MethodStatement getMethod(ExecutableElement executableElement, Session session) {
    return new MethodStatementBasedOnExecutableElement(executableElement, session);
  }

  static MethodStatement getMethod(Method method) {
    return new MethodStatementBasedOnLangMethod(method);
  }

  static MethodSignature getMethodSignature(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    return new MethodSignatureBasedOnExecutableElement(executableElement, typeContext, session);
  }

  static MethodParam getMethodParam(Parameter parameter) {
    List<AnnotationInstance> annotations = Arrays.stream(parameter.getAnnotations())
        .map(AnnotationInstances::of)
        .toList();
    return MethodParams.build()
        .name(parameter.getName())
        .type(JavaStatements.customTypeReference(parameter.getType()))
        .annotations(annotations)
        .get();
  }

  static Optional<TypeReference> getMethodReturnType(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    TypeMirror returnTypeMirror = executableElement.getReturnType();
    if (returnTypeMirror.getKind() == TypeKind.VOID || returnTypeMirror.getKind() == TypeKind.NONE
        || returnTypeMirror.getKind() == TypeKind.NULL
    ) {
      return Optional.empty();
    }
    return Optional.of(JavaModelFunctions.getTypeReference(returnTypeMirror, typeContext, session));
  }

  static Optional<Object> getMethodDefaultValue(
      ExecutableElement executableElement
  ) {
    if (executableElement.getDefaultValue() == null || executableElement.getDefaultValue().getValue() == null) {
      return Optional.empty();
    }
    return Optional.of(executableElement.getDefaultValue().getValue());
  }

  static Optional<Instance> getMethodDefaultValueInstance(
      ExecutableElement executableElement, Session session
  ) {
    return getMethodDefaultValue(executableElement).map(v -> InstanceFunctions.objectToInstance(v, session));
  }

  static List<MethodParam> getMethodParams(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    return executableElement.getParameters().stream()
        .map(variableElement -> MethodParams.of(variableElement, typeContext, session))
        .toList();
  }

  static List<ThrowableReference> getMethodExceptions(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    return executableElement.getThrownTypes().stream()
        .map(type -> (ThrowableReference) JavaModelFunctions.getTypeReference(type, typeContext, session))
        .collect(Collectors.toList());
  }

  static Optional<MethodStatement> getOverrideMethod(CustomType type, MethodStatement method) {
    for (MethodStatement declaredMethod : type.declaredMethods()) {
      if (isEquivalentMethods(declaredMethod, method)) {
        return Optional.of(declaredMethod);
      }
    }
    return Optional.empty();
  }

  static List<MethodStatement> getOverrideMethods(MethodStatement method) {
    return getOverrideMethods(method.owner(), method, false);
  }

  private static List<MethodStatement> getOverrideMethods(
      CustomType type, MethodStatement method, boolean included
  ) {
    List<MethodStatement> overrideMethods = new ArrayList<>();
    if (included) {
      for (MethodStatement parentMethod : type.declaredMethods()) {
        if (isEquivalentMethods(method, parentMethod)) {
          overrideMethods.add(parentMethod);
        }
      }
    }
    for (CustomTypeReference parent : type.parentTypes()) {
      if (parent.isCustomTypeReference()) {
        overrideMethods.addAll(getOverrideMethods(parent.asCustomTypeReferenceOrElseThrow().targetType(), method, true));
      }
    }
    return overrideMethods;
  }

  static boolean isEquivalentMethods(MethodStatement method1, MethodStatement method2) {
    if (!method1.name().equals(method2.name())) {
      return false;
    }
    if (method1.params().size() != method2.params().size()) {
      return false;
    }
    Iterator<MethodParam> paramIterator1 = method1.params().iterator();
    Iterator<MethodParam> paramIterator2 = method2.params().iterator();
    while (paramIterator1.hasNext() && paramIterator2.hasNext()) {
      MethodParam param1 = paramIterator1.next();
      MethodParam param2 = paramIterator2.next();
      if (!TypeReferenceFunctions.isEquivalentTypes(param1.type(), param2.type())) {
        return false;
      }
    }
    return true;
  }

  static boolean isEquivalentMethods(Method method1, Method method2) {
    if (!method1.getName().equals(method2.getName())) {
      return false;
    }
    if (method1.getParameterCount() != method2.getParameterCount()) {
      return false;
    }
    for (int i = 0; i < method1.getParameterCount(); i++) {
      Class<?> typeParam1 = method1.getParameters()[i].getType();
      Class<?> typeParam2 = method2.getParameters()[i].getType();
      if (typeParam1 != typeParam2) {
        return false;
      }
    }
    return true;
  }
}
