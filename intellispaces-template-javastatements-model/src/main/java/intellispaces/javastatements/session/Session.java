package intellispaces.javastatements.session;

import intellispaces.javastatements.statement.custom.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
