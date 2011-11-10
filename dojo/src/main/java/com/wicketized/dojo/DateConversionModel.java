package com.wicketized.dojo;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateConversionModel extends AbstractReadOnlyModel<String> {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

  private IModel<DateMidnight> dateModel;

  public static DateMidnight parseMilliseconds (String asMilliseconds) {

    return new DateMidnight(Long.parseLong(asMilliseconds));
  }

  public static DateMidnight parseDate (String asDate) {

    return DATE_TIME_FORMATTER.parseDateTime(asDate).toDateMidnight();
  }

  public static String print (DateMidnight dateMidnight) {

    return DATE_TIME_FORMATTER.print(dateMidnight);
  }

  public DateConversionModel (IModel<DateMidnight> dateModel) {

    this.dateModel = dateModel;
  }

  public synchronized void setDateModel (IModel<DateMidnight> dateModel) {

    this.dateModel = dateModel;
  }

  @Override
  public synchronized String getObject () {

    DateMidnight dateMidnight;

    return ((dateMidnight = dateModel.getObject()) == null) ? null : DATE_TIME_FORMATTER.print(dateMidnight);
  }
}
