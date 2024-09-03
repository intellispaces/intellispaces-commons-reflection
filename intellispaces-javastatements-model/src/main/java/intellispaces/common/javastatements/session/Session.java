package intellispaces.common.javastatements.session;

import intellispaces.common.javastatements.customtype.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
