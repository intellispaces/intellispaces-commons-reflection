package intellispaces.javastatements.object.instance;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.ElementFunctions;
import intellispaces.javastatements.function.AnnotationFunctions;
import intellispaces.javastatements.model.custom.AnnotationStatement;
import intellispaces.javastatements.model.instance.AnnotationInstance;
import intellispaces.javastatements.model.instance.AnnotationElement;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class AnnotationInstanceAdapter implements AnnotationInstance {
  private final GetterAction<AnnotationStatement> annotationStatementGetter;
  private final GetterAction<Map<String, AnnotationElement>> elementsGetter;

  AnnotationInstanceAdapter(AnnotationMirror annotationMirror, Session session) {
    this.annotationStatementGetter = ActionFunctions.cachedLazyGetter(annMirror ->
        ElementFunctions.asAnnotationStatement((TypeElement) annMirror.getAnnotationType().asElement(), session), annotationMirror);
    this.elementsGetter = ActionFunctions.cachedLazyGetter(annMirror ->
        AnnotationFunctions.getAnnotationElements(annMirror, session).stream()
            .collect(Collectors.toMap(AnnotationElement::name, Function.identity())), annotationMirror
    );
  }

  @Override
  public AnnotationStatement annotationStatement() {
    return annotationStatementGetter.execute();
  }

  @Override
  public Collection<AnnotationElement> elements() {
    return elementsGetter.execute().values();
  }

  @Override
  public Optional<Instance> elementValue(String elementName) {
    return Optional.ofNullable(elementsGetter.execute().get(elementName)).map(AnnotationElement::value);
  }

  @Override
  public <A extends Annotation> A asAnnotationOf(Class<A> annotationClass) {
    return AnnotationFunctions.asAnnotation(this, annotationClass);
  }
}
