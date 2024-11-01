package intellispaces.common.javastatement.instance;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.customtype.AnnotationFunctions;
import intellispaces.common.javastatement.customtype.AnnotationType;
import intellispaces.common.javastatement.customtype.Annotations;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Adapter of {@link AnnotationMirror} to {@link AnnotationInstance}.
 */
class AnnotationInstanceBasedOnAnnotationMirror implements AnnotationInstance {
  private final Getter<AnnotationType> annotationStatementGetter;
  private final Getter<Map<String, AnnotationElement>> elementsGetter;

  AnnotationInstanceBasedOnAnnotationMirror(AnnotationMirror annotationMirror, Session session) {
    this.annotationStatementGetter = Actions.cachedLazyGetter(annMirror ->
        Annotations.of((TypeElement) annMirror.getAnnotationType().asElement(), session), annotationMirror);
    this.elementsGetter = Actions.cachedLazyGetter(annMirror ->
        AnnotationFunctions.getAnnotationElements(annMirror, session).stream()
            .collect(Collectors.toMap(AnnotationElement::name, Function.identity())), annotationMirror
    );
  }

  public StatementType statementType() {
    return StatementTypes.AnnotationInstance;
  }

  @Override
  public AnnotationType annotationStatement() {
    return annotationStatementGetter.get();
  }

  @Override
  public Collection<AnnotationElement> elements() {
    return elementsGetter.get().values();
  }

  @Override
  public Optional<Instance> value() {
    return valueOf("value");
  }

  @Override
  public Optional<Instance> valueOf(String elementName) {
    return Optional.ofNullable(elementsGetter.get().get(elementName)).map(AnnotationElement::value);
  }

  @Override
  public <A extends Annotation> A asAnnotationOf(Class<A> annotationClass) {
    return AnnotationFunctions.asAnnotation(this, annotationClass);
  }
}
