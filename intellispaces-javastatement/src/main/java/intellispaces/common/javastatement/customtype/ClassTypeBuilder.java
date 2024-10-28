package intellispaces.common.javastatement.customtype;

import intellispaces.common.javastatement.instance.AnnotationInstance;
import intellispaces.common.javastatement.method.MethodStatement;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.reference.NamedReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassTypeBuilder {
  private boolean isAbstract = false;
  private boolean isFinal = false;
  private String canonicalName;
  private CustomType enclosingType;
  private List<AnnotationInstance> annotations = new ArrayList<>();
  private List<NamedReference> typeParameters = new ArrayList<>();
  private CustomTypeReference extendedClass = null;
  private List<CustomTypeReference> implementedInterfaces = new ArrayList<>();
  private List<MethodStatement> constructors = new ArrayList<>();
  private List<MethodStatement> declaredMethods = new ArrayList<>();

  public ClassTypeBuilder canonicalName(String canonicalName) {
    this.canonicalName = canonicalName;
    return this;
  }

  public ClassType get() {
    validate();
    return new ClassTypeImpl(
      isAbstract,
      isFinal,
      canonicalName,
      enclosingType,
      annotations,
      typeParameters,
      extendedClass,
      implementedInterfaces,
      constructors,
      declaredMethods
    );
  }

  private void validate() {
    Objects.requireNonNull(canonicalName);
  }
}
