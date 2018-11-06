import java.io.*;
import java.lang.reflect.*;


final class XmlSerializer implements Serializer {
	
	private static int tabCounter = 0;
	private XmlSerializer() {}

	public static void serialize(Serializable obj) {
		try {
			FileOutputStream fos = new FileOutputStream(obj.getClass().getSimpleName() + ".xml", true);
			OutputStreamWriter writer = new OutputStreamWriter(fos);

			StringBuilder sb = new StringBuilder();
			serialize(obj, sb);

			writer.write(sb.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void serialize(Serializable obj, StringBuilder sb) {
		try {			
			
			Class objectName = obj.getClass();
			Field[] fields = objectName.getDeclaredFields();
			getTab(sb);
			sb.append("<" + objectName.getSimpleName() + ">\n");

			for(Field field : fields) {
				if(Modifier.isPrivate(field.getModifiers())) {
					field.setAccessible(true);
				}
				
				
				if (Serializable.class.isAssignableFrom(field.getType())) {
					tabCounter++;
					serialize((Serializable)field.get(obj), sb);
				} else {
					getTab(sb);
					sb.append("\t<" + field.getName() + ">");
					sb.append(field.get(obj));
					sb.append("</" + field.getName() + ">\n");
				}
			}

			getTab(sb);
			sb.append("</" + objectName.getSimpleName() + ">\n");
			tabCounter--;
		
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void getTab(StringBuilder sb) {
		for (int i = 0; i < tabCounter; i++) {
			sb.append("\t");
		}
	}

}