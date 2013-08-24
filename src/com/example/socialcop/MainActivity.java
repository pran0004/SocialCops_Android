package com.example.socialcop;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
        private static final int TAKE_PICTURE = 0;
        private static final int RESULT_LOAD_IMAGE = 1;
        public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
        private Uri mUri;
        private File  f;
        private Bitmap mPhoto;
        private Button snap;
        private Button rotate;
        private Button upload;
        private Button browse;
        //private MobileServiceClient mClient;
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         snap=(Button) findViewById(R.id.snap);
         rotate=(Button)findViewById(R.id.rotate);
         upload=(Button) findViewById(R.id.upload);
         browse=(Button)findViewById(R.id.browse);
         snap.setOnClickListener(this);
         rotate.setOnClickListener(this);
         upload.setOnClickListener(this);
         browse.setOnClickListener(this);
         upload.setEnabled(false);
         /*
         try {
			mClient = new MobileServiceClient("https://socialcops.azure-mobile.net/","TAUNWAyPuXTVpHpmbSaAkgffEezbYf85",this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
     }
     
     @Override
     public void onClick(View v) {
             if (v.getId()== R.id.snap) {
                     Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
             f = new File(Environment.getExternalStorageDirectory(),  "photo.jpg");
             i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
             mUri = Uri.fromFile(f);
             startActivityForResult(i, TAKE_PICTURE);
             
             } else if(v.getId()==R.id.rotate){
                     if (mPhoto!=null) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                mPhoto = Bitmap.createBitmap(mPhoto , 0, 0, mPhoto.getWidth(), mPhoto.getHeight(), matrix, true);
                ((ImageView)findViewById(R.id.photo_holder)).setImageBitmap(mPhoto);
                    }
            }
             
             else if(v.getId()==R.id.upload)
             {
            	 Intent intent = new Intent(this, Uploadpage.class);
            	 intent.putExtra(EXTRA_MESSAGE, f);
            	 startActivity(intent);
             }
             else if(v.getId()==R.id.browse)
             {
            	 Intent i = new Intent(
            	 Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            	 startActivityForResult(i, RESULT_LOAD_IMAGE);
             }
     }
     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
             super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    getContentResolver().notifyChange(mUri, null);
                    ContentResolver cr = getContentResolver();
                    try {
                        mPhoto = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);
                     ((ImageView)findViewById(R.id.photo_holder)).setImageBitmap(mPhoto);
                     
                    } catch (Exception e) {
                         Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                  }
                
                
                
            case RESULT_LOAD_IMAGE:
            	if(resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
            
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
            
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    
                    ImageView imageView = (ImageView) findViewById(R.id.photo_holder);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));             
                    // String picturePath contains the path of selected Image
                }
                
            	if(mPhoto!=null)
               	 upload.setEnabled(true);  
            }
     }
}