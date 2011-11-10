package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;

public class DojoContainerBehavior extends AbstractTransformerBehavior {

  @Override
  public CharSequence transform (Component component, CharSequence output) throws Exception {

    return new StringBuilder("<wicket:container id=\"").append(((DojoComponent)component).getDijitContainerMarkupId()).append("\" style=\"margin: 0px; padding: 0px\">").append(output).append("</wicket:container>");
  }
}
