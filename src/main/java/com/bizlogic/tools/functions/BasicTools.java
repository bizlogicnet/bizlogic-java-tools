package com.bizlogic.tools.functions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

import com.bizlogic.tools.constants.BasicConstants;

import org.apache.commons.lang3.StringUtils;

public class BasicTools {

  public static Integer valI(String value) {
    return value != null ? Integer.valueOf(value) : null;
  }

  public static Double valR(String value) {
    return value != null ? Double.valueOf(value) : null;
  }

  public static Integer listPosition(String title) {
    Boolean dynamic = title.contains("[") && title.contains("]");
    if (dynamic) {
      int iniPos = title.indexOf("[");
      int endPos = title.indexOf("]");
      if (iniPos < endPos) {
        String position = title.substring(iniPos + 1, endPos);
        if (StringUtils.isNumeric(position)) {
          return Integer.parseInt(position);
        } else {
          return null;
        }
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public static Boolean compareAndNull(Object t1, Object t2) {
    return (t1 != null && t2 != null && t1.toString().equals(t2.toString()));
  }

  public static int generalCompare(String v1, String v2) {
    int result = 0;
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    boolean numeric1 = pattern.matcher(v1).matches();
    boolean numeric2 = pattern.matcher(v2).matches();
    if (numeric1 && numeric2) {
      Double d1 = Double.parseDouble(v1);
      Double d2 = Double.parseDouble(v2);
      result = d1.compareTo(d2);
    } else {
      result = v1.compareTo(v2);
    }

    return result;
  }

  public static String nowSec(String format) {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
  }

  public static String numValue(double d) {
    if (d == (long) d)
      return String.format("%d", (long) d);
    else
      return String.format("%s", d);
  }

  public static Date valD(String date) {
    try {
      return new SimpleDateFormat(BasicConstants.FORMAT_DATE).parse(date);
    } catch (Exception e) {
      return null;
    }
  }

  public static Date valDT(String dateTime) {
    try {
      return new SimpleDateFormat(BasicConstants.FORMAT_DATE_TIME).parse(dateTime);
    } catch (Exception e) {
      return null;
    }
  }

  public static Date valDT_UTC(String dateTime) {
    try {
      return new SimpleDateFormat(BasicConstants.FORMAT_DATE_TIME).parse(dateTime);
    } catch (Exception e) {
      return null;
    }
  }

  public static String strD(Date date) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(BasicConstants.FORMAT_DATE);
      return dateFormat.format(date);
    } catch (Exception e) {
      return null;
    }
  }

  public static String strDT(Date dateTime) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(BasicConstants.FORMAT_DATE_TIME);
      return dateFormat.format(dateTime);
    } catch (Exception e) {
      return null;
    }
  }

  public static String localTimeZone() {
    return ZoneId.getAvailableZoneIds().stream().filter(z -> z.contains("Toronto")).findAny()
        .orElse(null);
  }

  public static Date toLocalDate(Date date) {
    if (ZoneId.systemDefault().equals(ZoneId.of("UTC"))) {
      LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(localTimeZone()));
      return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    } else {
      return date;
    }
  }

}
