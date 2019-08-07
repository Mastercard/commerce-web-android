package com.mastercard.mp.switchservices;

import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@code JsonSerializer} can transform data between Objects and JSON strings. Simply pass an
 * object
 * to {@link #serialize(Object)} and a JSON string representation of that object will be returned.
 * Need an Object from JSON? Using {@link #deserialize(Class, String)}, let {@code JsonSerializer}
 * know what class you'd like in return and the JSON that represents an instance of that class and
 * you will get a new instance of that class!
 */

class JsonSerializer {
  private static final String TAG = JsonSerializer.class.getSimpleName();
  private static final String SERIAL_VERSION_UID = "serialVersionUID";

  JsonSerializer() {
    //Empty constructor as this class has no state
  }

  /**
   * Convert an object of any type into a JSON representation of its data.
   *
   * @param object the object who structure will be converted to JSON representation
   * @return a String representation of the object passed in
   */
  <T> String serialize(T object) throws JSONException {
    try {
      Class<?> clazz = object.getClass();
      Field[] fields = clazz.getDeclaredFields();
      JSONObject jsonObject = new JSONObject();

      if (isPrimitiveOrString(clazz)) {
        return null;
      }

      for (Field field : fields) {
        if (field.isSynthetic() || field.getName().equals(SERIAL_VERSION_UID)) {
          continue;
        }

        boolean fieldRequired =
            field.isAnnotationPresent(Element.class) && field.getAnnotation(Element.class)
                .required();

        field.setAccessible(true);
        Class<?> type = field.getType();
        String fieldName = field.getName();
        Object fieldObject = field.get(object);

        if (fieldObject == null && fieldRequired) {
          throw new JSONException("Required field " + fieldName + " is null");
        } else if (!fieldRequired && fieldObject == null) {
          continue;
        }

        if (isPrimitiveOrString(type)) {
          jsonObject.put(fieldName, fieldObject);
        } else if (type.isArray()) {
          jsonObject.put(fieldName, serializeArray(fieldObject));
        } else if (type == List.class) {
          jsonObject.put(fieldName, serializeList((List) fieldObject));
        } else if (type.isEnum()) {
          jsonObject.put(fieldName, ((Enum) fieldObject).name());
        } else {
          JSONObject objectified = new JSONObject(serialize(fieldObject));
          jsonObject.put(fieldName, objectified);
        }
      }

      return jsonObject.toString();
    } catch (IllegalAccessException e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
      throw new JSONException(e.getLocalizedMessage());
    }
  }

  /**
   * Transform a JSON string representing an object of a given type to that object itself
   *
   * @param clazz the type represented by the JSON string
   * @param json JSON string representation of the object to be returned
   * @return the object represented by the JSON string
   */
  <T> T deserialize(Class<T> clazz, String json) throws JSONException {
    if (isPrimitiveOrString(clazz)) {
      return null;
    }

    try {
      JSONObject jsonObject = new JSONObject(json);
      Constructor<T> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      T object = constructor.newInstance();
      Field[] fields = clazz.getDeclaredFields();

      for (Field field : fields) {
        if (field.isSynthetic() || field.getName().equals(SERIAL_VERSION_UID)) {
          continue;
        }

        setField(field, jsonObject, object);
      }

      return object;
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | TypeNotPresentException | MalformedParameterizedTypeException e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
      throw new JSONException(e.getLocalizedMessage());
    }
  }

  private void setField(Field field, JSONObject jsonObject, Object object) throws JSONException {
    boolean fieldRequired =
        field.isAnnotationPresent(Element.class) && field.getAnnotation(Element.class).required();

    try {
      field.setAccessible(true);
      String fieldName;

      if(field.isAnnotationPresent(SerializedName.class)) {
        fieldName = field.getAnnotation(SerializedName.class).name();
      } else {
        fieldName = field.getName();
      }

      Class<?> type = field.getType();
      Object fieldValue;

      if (type.isArray()) {
        JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
        fieldValue = deserializeArray(type.getComponentType(), jsonArray);
      } else if (type == List.class) {
        type = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
        fieldValue = deserializeList(type, jsonArray);
      } else if (type == Integer.TYPE || type == Float.TYPE || type == Boolean.TYPE) {
        fieldValue = jsonObject.get(fieldName);
      } else if (type == String.class) {
        fieldValue = jsonObject.getString(fieldName);
      } else {
        fieldValue = deserialize(type, jsonObject.getJSONObject(fieldName).toString());
      }

      field.set(object, fieldValue);
    } catch (JSONException e) {
      Log.e(TAG, e.getLocalizedMessage());
      if (fieldRequired) {
        throw e;
      }
    } catch (IllegalAccessException e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }
  }

  private JSONArray serializeList(List list) throws JSONException {
    JSONArray jsonArray = new JSONArray();
    for (Object item : list) {
      Class<?> type = item.getClass();
      if (isPrimitiveOrString(type)) {
        jsonArray.put(item);
      } else if (type.isArray()) {
        jsonArray.put(serializeArray(item));
      } else if (type == List.class) {
        jsonArray.put(serializeList((List) item));
      } else {
        jsonArray.put(new JSONObject(serialize(item)));
      }
    }

    return jsonArray;
  }

  private JSONArray serializeArray(Object array) throws JSONException {
    JSONArray jsonArray = new JSONArray();
    int length = Array.getLength(array);
    Class<?> type = array.getClass().getComponentType();

    for (int i = 0; i < length; i++) {
      if (isPrimitiveOrString(type)) {
        jsonArray.put(Array.get(array, i));
      } else if (type.isArray()) {
        jsonArray.put(serializeArray(Array.get(array, i)));
      } else if (type == List.class) {
        jsonArray.put(serializeList((List) Array.get(array, i)));
      } else {
        jsonArray.put(new JSONObject(serialize(Array.get(array, i))));
      }
    }

    return jsonArray;
  }

  private <T> List<T> deserializeList(Class<T> type, JSONArray jsonArray) throws JSONException {
    if (jsonArray == null) {
      return new ArrayList<>();
    } else {
      int length = jsonArray.length();
      List<T> listValue = new ArrayList<>(length);

      for (int i = 0; i < length; i++) {
        if (isPrimitiveOrString(type)) {
          listValue.add(i, (T) jsonArray.get(i));
        } else if (type == List.class) {
          Class<?> parameterType = type.getClass().getTypeParameters()[0].getGenericDeclaration();
          listValue.add(i, (T) deserializeList(parameterType, jsonArray.getJSONArray(i)));
        } else if (type.isArray()) {

        } else {
          listValue.add(i, deserialize(type, jsonArray.get(i).toString()));
        }
      }

      return listValue;
    }
  }

  private Object deserializeArray(Class<?> type, JSONArray array) throws JSONException {
    if (array == null) {
      return Array.newInstance(type, 0);
    }

    int length = array.length();
    Object arrayValue = Array.newInstance(type, length);

    for (int i = 0; i < length; i++) {
      if (isPrimitiveOrString(type)) {
        Array.set(arrayValue, i, array.get(i));
      } else if (type.isArray()) {
        Array.set(arrayValue, i, deserializeArray(type, array.getJSONArray(i)));
      } else if (type == List.class) {
        Class<?> parameterType = type.getClass().getTypeParameters()[0].getGenericDeclaration();
        Array.set(arrayValue, i, deserializeList(parameterType, array.getJSONArray(i)));
      } else {
        Array.set(arrayValue, i, deserialize(type, array.get(i).toString()));
      }
    }

    return arrayValue;
  }

  private boolean isPrimitiveOrString(Class<?> clazz) {
    return clazz == Integer.class
        || clazz == Double.class
        || clazz == Float.class
        || clazz == Boolean.class
        || clazz == Integer.TYPE
        || clazz == Double.TYPE
        || clazz == Float.TYPE
        || clazz == Boolean.TYPE
        || clazz == String.class;
  }
}