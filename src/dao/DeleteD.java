package dao;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class DeleteD {
	public String Table(String table, String id) throws IOException {
		String path ="https://serverjavashop.herokuapp.com/delete/"+table+"/"+id;
		URL url = new URL(path);
		Scanner scan = new Scanner(url.openStream());
		String json = "";
		while(scan.hasNext())
		{
			json = json + scan.nextLine();
		}
		scan.close();
		JSONObject jsonarr = new JSONObject(json);
		String message = jsonarr.getString("message");
		return message;
	}
}
