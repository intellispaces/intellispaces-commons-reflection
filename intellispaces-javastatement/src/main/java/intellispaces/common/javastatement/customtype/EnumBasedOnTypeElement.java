package intellispaces.common.javastatement.customtype;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.session.Session;

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
