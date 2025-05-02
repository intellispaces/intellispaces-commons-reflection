package tech.intellispaces.statementsj;

/**
 * Java language statement.
 */
public interface Statement {

  /**
   * Statement type.
   */
  StatementType statementType();

  /**
   * Statement pretty declaration.
   */
  String prettyDeclaration();
}
