package intellispaces.javastatements.object.custom;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.AnnotationFunctions;
import intellispaces.javastatements.function.MethodFunctions;
import intellispaces.javastatements.function.TypeFunctions;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.MethodParam;
import intellispaces.javastatements.model.custom.MethodSignature;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.context.NameContextBuilder;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

class MethodSignatureAdapter implements MethodSignature {
  private final ExecutableElement executableElement;
  private final List<NamedTypeReference> typeParams;
  private final GetterAction<Optional<TypeReference>> returnTypeGetter;
  private final GetterAction<Optional<Instance>> defaultValueGetter;
  private final GetterAction<List<MethodParam>> paramsGetter;
  private final GetterAction<List<ExceptionCompatibleTypeReference>> exceptionsGetter;
  private final GetterAction<List<AnnotationInstance>> annotationsGetter;

  MethodSignatureAdapter(ExecutableElement executableElement, NameContext nameContext, Session session) {
    this.executableElement = executableElement;
    this.typeParams = TypeFunctions.getTypeParameters(executableElement, nameContext, session);
    NameContext classNameContext = createNameContext(nameContext, this.typeParams);
    this.returnTypeGetter = ActionFunctions.cachedLazyGetter(MethodFunctions::getMethodReturnType, executableElement, classNameContext, session);
    this.defaultValueGetter = ActionFunctions.cachedLazyGetter(MethodFunctions::getMethodDefaultValueInstance, executableElement, session);
    this.paramsGetter = ActionFunctions.cachedLazyGetter(MethodFunctions::getMethodParams, executableElement, classNameContext, session);
    this.exceptionsGetter = ActionFunctions.cachedLazyGetter(MethodFunctions::getMethodExceptions, executableElement, classNameContext, session);
    this.annotationsGetter = ActionFunctions.cachedLazyGetter(TypeFunctions::getAnnotations, executableElement, session);
  }

  private NameContext createNameContext(NameContext parentContext, List<NamedTypeReference> typeParams) {
    NameContextBuilder builder = NameContextBuilder.get().parentContext(parentContext);
    typeParams.forEach(typeParam -> builder.addTypeParam(typeParam.name(), typeParam));
    return builder.build();
  }

  @Override
  public String name() {
    return executableElement.getSimpleName().toString();
  }

  @Override
  public List<NamedTypeReference> typeParameters() {
    return typeParams;
  }

  @Override
  public Optional<TypeReference> returnType() {
    return returnTypeGetter.execute();
  }

  @Override
  public Optional<Instance> defaultValue() {
    return defaultValueGetter.execute();
  }

  @Override
  public List<MethodParam> params() {
    return paramsGetter.execute();
  }

  @Override
  public List<ExceptionCompatibleTypeReference> exceptions() {
    return exceptionsGetter.execute();
  }

  @Override
  public List<AnnotationInstance> annotations() {
    return annotationsGetter.execute();
  }

  @Override
  public Optional<AnnotationInstance> selectAnnotation(String annotationClass) {
    return AnnotationFunctions.selectAnnotation(this, annotationClass);
  }

  @Override
  public <A extends Annotation> Optional<A> selectAnnotation(Class<A> annotationClass) {
    return AnnotationFunctions.selectAnnotation(this, annotationClass);
  }

  @Override
  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return AnnotationFunctions.hasAnnotation(this, annotationClass);
  }

  @Override
  public boolean isPublic() {
    return executableElement.getModifiers().contains(Modifier.PUBLIC);
  }

  @Override
  public boolean isDefault() {
    return executableElement.isDefault();
  }

  @Override
  public boolean isStatic() {
    return executableElement.getModifiers().contains(Modifier.STATIC);
  }
}
