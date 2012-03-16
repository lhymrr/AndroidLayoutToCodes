import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlPull {

	
	//'/media/BACKUP/UbuntuAPP/GitHub/Snow_Wallpaper/Snow_Wallpaper/res/layout/main.xml'
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("请将Xml文件拖动至此：");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();
		fileName = fileName.replace("\"", "");
		fileName = fileName.replace("'", "");
		File f = new File(fileName);
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(f));
			System.out.println("is = " + is.toString());
			PullParseService.getData(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("输出成功！");
		System.out.println("==============================================");
		System.out.println("Author:LiuHeyuan");
		System.out.println("==============================================");
		System.out.println("按任意键退出！");
		sc.nextLine();
	}

}

class PullParseService {
	public static void getData(InputStream inputStream) throws Exception {
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();// 产生第一个事件
		List<String> statement = new ArrayList<String>();
		List<String> assignment = new ArrayList<String>();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT: {

				break;
			}
			case XmlPullParser.START_TAG: {
				for (int i = 0; i < parser.getAttributeCount(); i++) {
					if (parser.getAttributeName(i).equals("android:id")) {
						statement.add("private " + parser.getName() + parser.getAttributeValue(i).replace("@+id/", " ") + ";");
						assignment.add(parser.getAttributeValue(i).replace("@+id/", "") + " = " + "(" + parser.getName() + ")findViewById(R.id." + parser.getAttributeValue(i).replace("@+id/", "") + ");");
					}
				}
				break;
			}
			case XmlPullParser.END_DOCUMENT: {
				System.out.println(statement.toString());
				System.out.println(assignment.toString());
				break;
			}

			}
			event = parser.next();
		}
		System.out.println("======================================声明======================================");
		for (String item : statement) {
			System.out.println(item);
		}
		System.out.println("======================================赋值======================================");
		for (String item : assignment) {
			System.out.println(item);
		}
	}
}
