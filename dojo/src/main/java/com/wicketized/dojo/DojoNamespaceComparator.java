package com.wicketized.dojo;

import java.util.Comparator;

public class DojoNamespaceComparator implements Comparator<String> {

  @Override
  public int compare (String string1, String string2) {

    String[] segments1 = string1.split("\\.", -1);
    String[] segments2 = string2.split("\\.", -1);
    int commonSegments = Math.min(segments1.length, segments2.length);
    int comparison;

    for (int index = 0; index < commonSegments; index++) {
      if ((comparison = segments1[index].compareTo(segments2[index])) != 0) {

        return comparison;
      }
    }

    return (segments1.length == segments2.length) ? 0 : (segments1.length > segments2.length) ? 1 : -1;
  }
}
