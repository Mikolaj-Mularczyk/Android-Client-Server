package com.example.klient;

import java.io.File;
import java.io.IOException;

import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	Button loadIm;
	Client client;
	ImageView jpgView;
	static boolean state = false;
	static boolean image = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadIm = (Button)findViewById(R.id.loadImage);
		jpgView = (ImageView)findViewById(R.id.imageView1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void launchClient(View v) throws IOException{
		if(state == false){
			client = new Client();
			client.runClient();
		
			state = true;
		}
		else
		{
			client.close();
			state = false;
		}
	}
	public void up(View v) {
		if(state == true){
			client.send("Up");
		}
	}
	public void down(View v) {
		if(state == true){
			client.send("Down");
		}
	}
	public void left(View v) {
		if(state == true){
			client.send("Left");
		}
	}
	public void right(View v) {
		if(state == true){
			client.send("Right");
		}
	}
	public void loadImage(final View v){
			File imageFile;
			if(image)
				imageFile = new File("/sdcard/rose.jpg");
			else
				imageFile = new File("/sdcard/kosmos.jpg");
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
			jpgView.setImageBitmap(bitmap);
			client.sendImage(imageFile);
			image = !image;
			
//			Handler handler = new Handler();
//			handler.postDelayed(new Runnable() {
//			   @Override
//			   public void run() {
//			      loadImage(v);
//			   }
//			 }, 150);
	}
}
