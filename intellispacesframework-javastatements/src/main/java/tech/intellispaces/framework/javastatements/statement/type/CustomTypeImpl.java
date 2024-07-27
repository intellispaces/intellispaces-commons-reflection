package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

class CustomTypeImpl extends AbstractCustomType {
  private final CustomStatement statement;
  private final List<NotPrimitiveType> typeArguments;
  private final Getter<Map<String, NotPrimitiveType>> typeArgumentMappingsGetter;
  private final Getter<String> typeArgumentsDeclarationGetter;

  CustomTypeImpl(CustomStatement statement, List<NotPrimitiveType> typeArguments) {
    super();
    this.statement = statement;
    this.typeArguments = typeArguments;
    this.typeArgumentMappingsGetter = Actions.cachedLazyGetter(TypeFunctions::getTypeArgumentMapping, this);
    this.typeArgumentsDeclarationGetter = Actions.cachedLazyGetter(TypeFunctions::getTypeArgumentsDeclaration, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomType;
  }

  @Override
  public CustomStatement statement() {
    return statement;
  }

  @Override
  public List<NotPrimitiveType> typeArguments() {
    return typeArguments;
  }

  @Override
  public Map<String, NotPrimitiveType> typeArgumentMapping() {
    return typeArgumentMappingsGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration() {
    return typeArgumentsDeclarationGetter.get();
  }

  @Override
  public String typeArgumentsDeclaration(Function<String, String> simpleNameMapper) {
    return TypeFunctions.getTypeArgumentsDeclaration(this, simpleNameMapper);
  }
}
