package com.example.intae.client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.intae.client.AddListActivity.REQUEST_IMAGE_CAPTURE;


public class CameraActivity extends AppCompatActivity {

    private Button btnCapture;
    private Button btnSendImage;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;

    HttpConnection httpConn = HttpConnection.getInstance();

    boolean imageExist = false;

    private String imageFilePath;
    private Uri photoUri;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnCapture = (Button)findViewById(R.id.cameraBtn);
        btnSendImage = (Button)findViewById(R.id.sendImageBtn);
        imgCapture = (ImageView)findViewById(R.id.iv);
        btnCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*
                Intent intent = new Intent(getApplicationContext(), AddListActivity.class);
                intent.putExtra("shop", "hihi");
                intent.putExtra("category", "생활서비스");
                intent.putExtra("date_year", 2018);
                intent.putExtra("date_month", 3);
                intent.putExtra("date_day", 12);
                intent.putExtra("totalPrice", 1212);
                intent.putExtra("itemArray", new String[]{"aa", "bb"});
                intent.putExtra("priceArray", new int[]{123, 234});

                startActivity(intent);
                */

                int permissionCheck = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA);
                if(permissionCheck == PackageManager.PERMISSION_DENIED){
                    // 권한 없음
                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                }else{
                    // 권한 있음
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                        File photoFile = null;
                        try{
                            photoFile = createImageFile();
                        }catch(IOException ex){

                        }

                        if(photoFile != null){
                            photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                }
            }
        });
        btnSendImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(imageExist == true){
                    Toast.makeText(getApplicationContext(), "사진 전송!", Toast.LENGTH_LONG).show();
                    Bitmap bitmap = ((BitmapDrawable) imgCapture.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
                    byte[] imageInByte = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                    sendImageToServer(encodedImage);
                }
            }
        });
    }

    private void sendImageToServer(final String encodedImage){
        new Thread(){
            public void run(){
                httpConn.sendImageToWebServer(encodedImage, callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TEST", "실패: "+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.d("@@@@@","서버에서 뭔가 왔음.");
            String jsonData = response.body().string();
            Log.d("jsonData", jsonData+"");
            try {
                JSONObject jObject = new JSONObject(jsonData);
                Log.d("jObject", jObject+"");
                if(jObject.getBoolean("success")){//로그인 정보가 유효할 경우, 메인 액티비티로 전환

                    // 서버에서 받은 값을 intent에 추가한 후에, startActivity(intent) 해야 함.
                    String shop = jObject.getString("shop");
                    String category = jObject.getString("category");
                    String date = jObject.getString("date");
                    JSONArray info = (JSONArray) jObject.get("info");
                    int totalPrice = jObject.getInt("totalPrice");

                    int date_year = Integer.parseInt(date.substring(0, 4));
                    int date_month = Integer.parseInt(date.substring(5, 7));
                    int date_day = Integer.parseInt(date.substring(8, 10));

                    Intent intent = new Intent(getApplicationContext(), AddListActivity.class);
                    intent.putExtra("shop", shop);
                    intent.putExtra("category", category);
                    intent.putExtra("date_year", date_year);
                    intent.putExtra("date_month", date_month);
                    intent.putExtra("date_day", date_day);
                    intent.putExtra("totalPrice", totalPrice);

                    List<String> item = new ArrayList<String>();
                    List<String> price = new ArrayList<String>();

                    for(int i = 0;i < info.length();i++){
                        JSONObject jj = info.getJSONObject(i);
                        item.add(jj.getString("item"));
                        price.add(jj.getString("price"));
                    }

                    intent.putExtra("itemArray", item.toArray(new String[0]));
                    intent.putExtra("priceArray", price.toArray(new String[0]));

                    startActivity(intent);
                }else{
                    Log.d("@@@@@@@", "########");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            imgCapture.setImageBitmap(rotate(bitmap, exifDegree));
            //imgCapture.setImageURI(photoUri);
            imageExist = true;
        }else if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == 0){
            if(grantResults[0] == 0){
                Toast.makeText(this, "카메라 권한이 승인됨", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "카메라 권한이 거절되었습니다. 카메라를 이용하려면 권한을 승낙하세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
