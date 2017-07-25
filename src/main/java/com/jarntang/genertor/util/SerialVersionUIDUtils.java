package com.jarntang.genertor.util;

import com.jarntang.genertor.core.model.ClassInfo.FieldInfo;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * java serializable uuid generate utils.
 *
 * @author Qinmu
 * @since 2016/12/5 13:18
 */
public class SerialVersionUIDUtils {

  private SerialVersionUIDUtils() {
  }

  public static long computeSerialVersionUID(String className, List<FieldInfo> fields) {
    try {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream outputStream = new DataOutputStream(bout);

      outputStream.writeUTF(className);

      int classMods = Modifier.PUBLIC | Modifier.ABSTRACT;

      outputStream.writeInt(classMods);

      MemberSignature[] fieldSigs = new MemberSignature[fields.size()];
      for (int i = 0; i < fields.size(); i++) {
        fieldSigs[i] = new MemberSignature(fields.get(i));
      }
      Arrays.sort(fieldSigs, Comparator.comparing(ms -> ms.name));

      for (MemberSignature sig : fieldSigs) {
        int mods = Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED |
            Modifier.STATIC | Modifier.FINAL | Modifier.VOLATILE |
            Modifier.TRANSIENT;
        if (((mods & Modifier.PRIVATE) == 0) ||
            ((mods & (Modifier.STATIC | Modifier.TRANSIENT)) == 0)) {
          outputStream.writeUTF(sig.name);
          outputStream.writeInt(mods);
          outputStream.writeUTF(sig.signature);
        }
      }

      for (FieldInfo field : fields) {
        int mods = (Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED |
            Modifier.STATIC | Modifier.FINAL |
            Modifier.SYNCHRONIZED | Modifier.NATIVE |
            Modifier.ABSTRACT | Modifier.STRICT);
        if ((mods & Modifier.PRIVATE) == 0) {
          outputStream.writeUTF(field.getName());
          outputStream.writeInt(mods);
        }
      }

      outputStream.flush();

      MessageDigest md = MessageDigest.getInstance("SHA");
      byte[] hashBytes = md.digest(bout.toByteArray());
      long hash = 0;
      for (int i = Math.min(hashBytes.length, 8) - 1; i >= 0; i--) {
        hash = (hash << 8) | (hashBytes[i] & 0xFF);
      }
      return hash;
    } catch (Exception ex) {
      return -1;
    }
  }

  private static class MemberSignature {

    private final String name;
    private final String signature;

    MemberSignature(FieldInfo field) {
      name = field.getName();
      signature = getClassSignature(field.getTypeClass());
    }

    private static String getClassSignature(Class clazz) {
      StringBuilder builder = new StringBuilder();
      Class classType = clazz;
      while (clazz.isArray()) {
        builder.append('[');
        classType = clazz.getComponentType();
      }
      if (classType.isPrimitive()) {
        if (classType == Integer.TYPE) {
          builder.append('I');
        } else if (classType == Byte.TYPE) {
          builder.append('B');
        } else if (classType == Long.TYPE) {
          builder.append('J');
        } else if (classType == Float.TYPE) {
          builder.append('F');
        } else if (classType == Double.TYPE) {
          builder.append('D');
        } else if (classType == Short.TYPE) {
          builder.append('S');
        } else if (classType == Character.TYPE) {
          builder.append('C');
        } else if (classType == Boolean.TYPE) {
          builder.append('Z');
        } else if (classType == Void.TYPE) {
          builder.append('V');
        } else {
          throw new InternalError();
        }
      } else {
        builder.append('L').append(classType.getName().replace('.', '/')).append(';');
      }
      return builder.toString();
    }

  }

}
