package tech.intellispaces.framework.javastatements.session;

import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
