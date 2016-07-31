package com.example.assignmentxmlimage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView listImage;
	ArrayList<ImageData> listData = new ArrayList<ImageData>();
	ArrayAdapter adapter;
	String filePath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		listImage = (ListView) findViewById(R.id.listView1);

		ImageData img = new ImageData(
				"Jerry",
				"http://allcartooncharacters.com/wp-content/uploads/2014/05/Tweety-310x310.png",
				"a.png");
		ImageData img1 = new ImageData(
				"Duck",
				"http://allcartooncharacters.com/wp-content/uploads/2014/05/Tweety-310x310.png",
				"b.png");
		ImageData img2 = new ImageData(
				"Prince",
				"http://cf.ltkcdn.net/cheerleading/images/std/51009-332x361-CheerleadingCartoons.jpg",
				"c.png");

		listData.add(img);
		listData.add(img1);
		listData.add(img2);
		adapter = new ArrayAdapter<ImageData>(MainActivity.this,
				android.R.layout.simple_list_item_1, listData);
		listImage.setAdapter(adapter);

		listImage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String URI = listData.get(arg2).getImageUrl();
				String fileName = listData.get(arg2).getImageFilename();
				ImageDownloadTask task = new ImageDownloadTask();
				task.execute(URI, fileName);
			}
		});

	}

	class ImageDownloadTask extends AsyncTask<String, Void, String[]> {
		

		@Override
		protected String[] doInBackground(String... params) {
			String url = params[0];
			String fileName = params[1];

			String array[] = new String[2];

			ApplicationInfo appInfo = getApplicationInfo();
			filePath = appInfo.dataDir + "/" + fileName;
			File file = new File(filePath);

			try {

				if (!file.exists()) {
					HttpGet httpGetRequest = new HttpGet(url);
					HttpClient httpClient = new DefaultHttpClient();

					Bitmap bmp = null;

					HttpResponse response = httpClient.execute(httpGetRequest);
					InputStream is = response.getEntity().getContent();
					bmp = BitmapFactory.decodeStream(is);
					FileOutputStream out = new FileOutputStream(file);
					bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
					out.flush();
					out.close();
				}

				array[0] = filePath;
				array[1] = fileName;

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return array;
		}

		@Override
		protected void onPostExecute(String result[]) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			final Dialog dialog = new Dialog(MainActivity.this);
			dialog.setTitle(result[1]);
			dialog.setContentView(R.layout.dialog_view);
			ImageView image = (ImageView) dialog.findViewById(R.id.imageView1);
			Bitmap bitmap = BitmapFactory.decodeFile(result[0]);
			image.setImageBitmap(bitmap);

			Button btnClose = (Button) dialog.findViewById(R.id.button1);

			btnClose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.setCancelable(false);
			dialog.show();
		}
	}
}
