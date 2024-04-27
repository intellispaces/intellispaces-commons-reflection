package tech.intellispacesframework.javastatements.session;

import tech.intellispacesframework.javastatements.statement.custom.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
