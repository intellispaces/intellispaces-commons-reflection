package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;
import tech.intellispacesframework.javastatements.statement.custom.AnnotationFunctions;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.custom.AnnotationStatement;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class AnnotationInstanceAdapter implements AnnotationInstance {
  private final Getter<AnnotationStatement> annotationStatementGetter;
  private final Getter<Map<String, AnnotationElement>> elementsGetter;

  AnnotationInstanceAdapter(AnnotationMirror annotationMirror, Session session) {
    this.annotationStatementGetter = ActionBuilders.cachedLazyGetter(annMirror ->
        TypeElementFunctions.asAnnotationStatement((TypeElement) annMirror.getAnnotationType().asElement(), session), annotationMirror);
    this.elementsGetter = ActionBuilders.cachedLazyGetter(annMirror ->
        AnnotationFunctions.getAnnotationElements(annMirror, session).stream()
            .collect(Collectors.toMap(AnnotationElement::name, Function.identity())), annotationMirror
    );
  }

  public StatementType statementType() {
    return StatementTypes.AnnotationInstance;
  }

  @Override
  public AnnotationStatement annotationStatement() {
    return annotationStatementGetter.get();
  }

  @Override
  public Collection<AnnotationElement> elements() {
    return elementsGetter.get().values();
  }

  @Override
  public Optional<Instance> elementValue(String elementName) {
    return Optional.ofNullable(elementsGetter.get().get(elementName)).map(AnnotationElement::value);
  }

  @Override
  public <A extends Annotation> A asAnnotationOf(Class<A> annotationClass) {
    return AnnotationFunctions.asAnnotation(this, annotationClass);
  }
}
