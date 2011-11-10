package com.wicketized.dojo;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class DojoTransparentPanel extends Panel {

  private static final ThreadLocal<Boolean> STRIP_TAGS_LOCAL = new ThreadLocal<Boolean>();

  public DojoTransparentPanel (final String id) {

    super(id);
  }

  public DojoTransparentPanel (final String id, final IModel<?> model) {

    super(id, model);
  }

  @Override
  protected void onBeforeRender () {

    super.onBeforeRender();

    STRIP_TAGS_LOCAL.set(Application.get().getMarkupSettings().getStripWicketTags());
    Application.get().getMarkupSettings().setStripWicketTags(true);
  }

  @Override
  protected void onAfterRender () {

    Application.get().getMarkupSettings().setStripWicketTags(STRIP_TAGS_LOCAL.get());

    super.onAfterRender();
  }
}

