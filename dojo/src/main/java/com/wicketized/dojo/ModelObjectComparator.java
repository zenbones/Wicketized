package com.wicketized.dojo;

public class ModelObjectComparator {

  public static boolean isEqual (Object a, Object b) {

    return ((a == null) && (b == null)) || ((!((a == null) || (b == null))) && a.equals(b));
  }
}
