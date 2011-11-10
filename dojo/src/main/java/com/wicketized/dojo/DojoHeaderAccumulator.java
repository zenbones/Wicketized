package com.wicketized.dojo;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class DojoHeaderAccumulator {

  private static final DojoNamespaceComparator DOJO_NAMESPACE_COMPARATOR = new DojoNamespaceComparator();
  private static final ThreadLocal<Set<String>> DOJO_TYPE_SET_LOCAL = new ThreadLocal<Set<String>>() {

    @Override
    protected Set<String> initialValue () {

      return new HashSet<String>();
    }
  };

  public static void register (DojoTyped dojoTyped) {

    DOJO_TYPE_SET_LOCAL.get().add(dojoTyped.getDojoType());
  }

  public static void register (String... dojoTypes) {

    if (dojoTypes != null) {
      for (String dojoType : dojoTypes) {
        DOJO_TYPE_SET_LOCAL.get().add(dojoType);
      }
    }
  }

  public static String getHeaderContribution () {

    LinkedList<String> requireList;
    StringBuilder contributionBuilder = new StringBuilder();

    contributionBuilder.append("<script src=\"http://ajax.googleapis.com/ajax/libs/dojo/1.6.1/dojo/dojo.xd.js\" djConfig=\"parseOnLoad: true\"></script>\n");
    contributionBuilder.append("<script type=\"text/javascript\">\n");

    requireList = new LinkedList<String>(DOJO_TYPE_SET_LOCAL.get());
    Collections.sort(requireList, DOJO_NAMESPACE_COMPARATOR);
    for (String dojoType : requireList) {
      contributionBuilder.append("dojo.require(\"").append(dojoType).append("\");\n");
    }

    contributionBuilder.append("</script>\n");
    contributionBuilder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/claro.css\"/>\n")
      .append("<script>\n  dojo.ready(function() {\n});\n</script>\n");

    return contributionBuilder.toString();
  }
}
