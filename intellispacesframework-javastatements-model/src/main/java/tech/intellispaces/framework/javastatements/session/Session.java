package tech.intellispaces.framework.javastatements.session;

import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

/**
 * Parsing session.
 */
public interface Session {

  CustomStatement getType(String typeName);

  void putType(String typeName, CustomStatement customStatement);

  void clear();
}
