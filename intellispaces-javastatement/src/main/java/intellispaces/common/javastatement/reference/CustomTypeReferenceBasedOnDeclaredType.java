package intellispaces.common.javastatement.reference;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.base.type.TypeFunctions;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.common.JavaModelFunctions;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.customtype.CustomType;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Adapter od {@link DeclaredType} to {@link CustomTypeReference}.
 */
class CustomTypeReferenceBasedOnDeclaredType extends AbstractCustomTypeReference {
  private final Getter<CustomType> targetTypeGetter;
  private final Getter<List<NotPrimitiveReference>> typeArgumentsGetter;
  private final Getter<Map<String, NotPrimitiveReference>> typeArgumentMappingsGetter;
  private final Getter<String> typeArgumentsDeclarationGetter;

  CustomTypeReferenceBasedOnDeclaredType(DeclaredType declaredType, TypeContext typeContext, Session session) {
    super();
    TypeElement typeElement = (TypeElement) declaredType.asElement();
    this.targetTypeGetter = Actions.cachedLazyGetter(JavaModelFunctions::asCustomStatement, typeElement, session);
    this.typeArgumentsGetter = Actions.cachedLazyGetter(JavaModelFunctions::getTypeArguments, declaredType, typeContext, session);
    this.typeArgumentMappingsGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = Actions.cachedLazyGetter(TypeReferenceFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomReference;
  }

  @Override
  public CustomType targetType() {
    return targetTypeGetter.get();
  }

  @Override
  public Class<?> targetClass() {
    return TypeFunctions.getClass(targetType().canonicalName()).orElseThrow();
  }

  @Override
  public List<NotPrimitiveReference> typeArguments() {
    return typeArgumentsGetter.get();
  }

  @Override
  public Map<String, NotPrimitiveReference> typeArgumentMapping() {
    return typeArgumentMappingsGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration() {
    return typeArgumentsDeclarationGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration(Function<String, String> simpleNameMapper) {
    return TypeReferenceFunctions.getTypeArgumentsDeclaration(this, simpleNameMapper);
  }
}
