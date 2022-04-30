package com.bizlogic.tools.functions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

@SuppressWarnings("all")
public class MapTools {

  private static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();
    for (java.beans.PropertyDescriptor pd : pds) {
      if (!pd.getPropertyType().equals(source.getClass())) {
        Object srcValue = src.getPropertyValue(pd.getName());
        if (srcValue == null)
          emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  public static void setNonNullProperties(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  public static void setNonNullFieldsProperties(Object source, Object target, String ignore) {
    String[] nullList = getNullPropertyNames(source);
    String[] ignoreList = ignore.split("//,");
    String[] excluded = new String[nullList.length + ignoreList.length];
    System.arraycopy(nullList, 0, excluded, 0, nullList.length);
    System.arraycopy(ignoreList, 0, excluded, nullList.length, ignoreList.length);
    BeanUtils.copyProperties(source, target, excluded);
  }

  public static void setAllProperties(Object source, Object target) {
    BeanUtils.copyProperties(source, target);
  }

  private static String[] getAllPropertyNamesButMe(Object source, String me) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();
    for (java.beans.PropertyDescriptor pd : pds) {
      if (!pd.getPropertyType().equals(source.getClass())) {
        if (!pd.getName().equals(me))
          emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  public static void setFieldsPropertiesIgnore(Object source, Object target, String ignore) {
    String[] ignoreList = ignore.split("//,");
    BeanUtils.copyProperties(source, target, ignoreList);
  }

  public static void setFieldsPropertiesIgnoreId(Object source, Object target) {
    setFieldsPropertiesIgnore(source, target, "id");
  }

  public static void setProperty(Object source, Object target, String key) {
    BeanUtils.copyProperties(source, target, getAllPropertyNamesButMe(source, key));
  }

  public static Map<String, Object> mapObj(Object source) {
    ObjectMapper oMapper = new ObjectMapper();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    oMapper.setDateFormat(df);
    return source == null ? new HashMap<String, Object>() : oMapper.convertValue(source, Map.class);
  }

  public static List<Object> listObj(Object source) {
    ObjectMapper oMapper = new ObjectMapper();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    oMapper.setDateFormat(df);
    return source == null ? new ArrayList<Object>() : oMapper.convertValue(source, List.class);
  }

  public static List<Map<String, Object>> listMapObj(Object source) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    List<Object> list = listObj(source);
    for (Object object : list) {
      Map<String, Object> map = mapObj(object);
      result.add(map);
    }
    return result;
  }

  public static String mapVariables(String input, Map<String, String> variables) {
    String output = input;
    for (Map.Entry<String, String> variable : variables.entrySet()) {
      output = output.replace("{" + variable.getKey() + "}", variable.getValue());
    }
    return output;
  }

  public static <T> T mapToClass(Class<T> dataClass, Object map) {
    // groupeMajeurService.deleteAll();
    try {
      ObjectMapper oMapper = new ObjectMapper();
      oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      T outputRec = oMapper.convertValue(map, dataClass);
      return outputRec;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public static Map<String, Object> addExtFields(String prefix, Object source, Map<String, Object> target) {
    Map<String, Object> output = target == null ? new HashMap<String, Object>() : target;
    if (source != null) {
      Map<String, Object> sourceFields = mapObj(source);
      for (Entry<String, Object> sourceField : sourceFields.entrySet()) {
        if (!sourceField.getKey().equals("id") && !sourceField.getKey().equals("basic")) {
          String keyName = prefix + "_" + sourceField.getKey();
          target.put(keyName, sourceField.getValue());
        }
      }
    }
    return output;
  }

  public static Object setFieldValue(String key, Object value, Object object) {
    Map<String, Object> fields = mapObj(object);
    fields.put(key, value);
    ObjectMapper oMapper = new ObjectMapper();
    object = oMapper.convertValue(fields, Object.class);
    return object;
  }

  public static String formatContent(String content, Object data) {
    String result = content;
    if (data != null) {
      Map<String, Object> mapD = mapObj(data);
      for (Entry<String, Object> mapF : mapD.entrySet()) {
        if (mapF.getValue() != null) {
          result = result.replace("{" + mapF.getKey() + "}", mapF.getValue().toString());
        }
      }
    }
    return result;

  }

}
