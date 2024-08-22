package intellispaces.javastatements.customtype;

import intellispaces.actions.Actions;
import intellispaces.actions.getter.Getter;
import intellispaces.javastatements.StatementType;
import intellispaces.javastatements.StatementTypes;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.reference.CustomTypeReference;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Adapter of {@link TypeElement} to {@link EnumType}.
 */
class EnumBasedOnTypeElement
    extends AbstractCustomTypeStatementBasedOnTypeElement
    implements EnumType
{
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  EnumBasedOnTypeElement(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    super(typeElement, typeContext, session);
    this.implementedInterfacesGetter = Actions.cachedLazyGetter(
        CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Enum;
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
