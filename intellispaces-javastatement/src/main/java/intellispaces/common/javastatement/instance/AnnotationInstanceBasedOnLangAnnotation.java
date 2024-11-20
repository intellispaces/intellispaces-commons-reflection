package intellispaces.common.javastatement.instance;

import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.customtype.AnnotationType;
import intellispaces.common.javastatement.customtype.Annotations;
import intellispaces.common.javastatement.session.Sessions;
import tech.intellispaces.entity.exception.NotImplementedExceptions;
import tech.intellispaces.entity.exception.UnexpectedExceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

/**
 * Adapter of {@link Annotation} to {@link AnnotationInstance}.
 */
class AnnotationInstanceBasedOnLangAnnotation implements AnnotationInstance {
  private final Annotation annotation;

  AnnotationInstanceBasedOnLangAnnotation(Annotation annotation) {
    this.annotation = annotation;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.AnnotationInstance;
  }

  @Override
  public AnnotationType annotationStatement() {
    return Annotations.of(annotation.annotationType());
  }

  @Override
  public Collection<AnnotationElement> elements() {
    throw NotImplementedExceptions.withCode("8boSmQ");
  }

  @Override
  public Optional<Instance> value() {
    return valueOf("value");
  }

  @Override
  public Optional<Instance> valueOf(String elementName) {
    Class<?> annotationClass = annotation.annotationType();
    try {
      Method method = annotationClass.getDeclaredMethod(elementName);
      return Optional.of(InstanceFunctions.objectToInstance(method.invoke(annotation), Sessions.get()));
    } catch (NoSuchMethodException e) {
      return Optional.empty();
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw UnexpectedExceptions.withMessage("Failed to invoke method '{0}' of the class {1}",
          elementName, annotationClass.getCanonicalName());
    }
  }

  @Override
  public <A extends Annotation> A asAnnotationOf(Class<A> aClass) {
    throw NotImplementedExceptions.withCode("iRc2Dw");
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("MNhIMQ");
  }
}
