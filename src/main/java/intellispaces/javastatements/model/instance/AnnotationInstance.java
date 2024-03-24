package intellispaces.javastatements.model.instance;

import intellispaces.javastatements.model.StatementType;
import intellispaces.javastatements.model.custom.AnnotationStatement;
import intellispaces.javastatements.object.StatementTypes;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;

/**
 * The annotation instance.
 */
public interface AnnotationInstance extends Instance {

  default StatementType statementType() {
    return StatementTypes.AnnotationInstance;
  }

  @Override
  default Optional<AnnotationInstance> asAnnotation() {
    return Optional.of(this);
  }

  /**
   * Annotation type statement.
   */
  AnnotationStatement annotationStatement();

  /**
   * Annotation elements.
   */
  Collection<AnnotationElement> elements();

  Optional<Instance> elementValue(String elementName);

  <A extends Annotation> A asAnnotationOf(Class<A> aClass);
}
