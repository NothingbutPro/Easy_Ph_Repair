package com.ics.easy_ph_repair.LoginSign;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.easy_ph_repair.NavigationActivity;
import com.ics.easy_ph_repair.R;
import com.ics.easy_ph_repair.Session.SessionManager;
import com.ics.easy_ph_repair.Utilssss.CameraUtils__TRD;
import com.ics.easy_ph_repair.Utilssss.CommonUtils_TRD;
import com.ics.easy_ph_repair.Utilssss.Utilities;
import com.ics.easy_ph_repair.Utilssss.Utility;
import com.ics.easy_ph_repair.WebUrls.Urls;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class UpdateProfile extends AppCompatActivity {
    Button upbtn;
    EditText otpedt;
    TextView opttxt;
    SessionManager sessionManager;
    //for uplaod
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE_TRD = 200;
    public static final int BITMAP_SAMPLE_SIZE_TRD = 8;
    public static final int MEDIA_TYPE_IMAGE_TRD = 1;
//    List<BranchesData> branchesList = new ArrayList<>();
    public static final String KEY_IMAGE_STORAGE_PATH_TRD = "image_path";
    List<String>  branchstringList = new ArrayList<>();
    private static String imageStoragePath_TRD;
    public static final String IMAGE_EXTENSION_TRD = "jpg";
    private Bundle savedInstanceState;
    /* Image done */
    ImageView proimg;
    String Date, Inward_time, Outward_time, Lorry_no, Chalan_no, Party_name, Quantity, Amount, Rate, Gst, Gross_amount,
            Reamark, Attachment,Address;
    ProgressDialog progressDialog;
   // ImageView proimg;
    private int income_tax_id = 0;
//    SessionManager sessionManager;
    int Gallery_view = 2;
    TextView Name,Lname,Town,State,Distric,Email,Mobet,Passedt,Conpass;
    private File pdfFile;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sessionManager = new SessionManager(this);
        proimg = findViewById(R.id.proimg);
        Name = findViewById(R.id.name);
        Lname = findViewById(R.id.lname);
        Town = findViewById(R.id.town);
        State = findViewById(R.id.state);
        Distric = findViewById(R.id.distric);
        Email = findViewById(R.id.email);
        Mobet = findViewById(R.id.mobet);
        Passedt = findViewById(R.id.passedt);
        Conpass = findViewById(R.id.conpass);
        upbtn = findViewById(R.id.upbtn);
        new GETMYDATA().execute();

//        opttxt.setText(opttxt.getText().toString()+" "+sessionManager.getMobile());
        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UPdateMyProfile().execute();
            }
        });
        proimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    private void previewCapturedImage() {

        Bitmap bitmap = CameraUtils__TRD.optimizeBitmap_TRD(BITMAP_SAMPLE_SIZE_TRD, imageStoragePath_TRD);
        if (imageStoragePath_TRD != null) {
            proimg.setImageResource(R.drawable.boyprofile);
            //   Toast.makeText(this, "Uploading", Toast.LENGTH_SHORT).show();
            new ImageCompression().execute(imageStoragePath_TRD);
        }

    }
    //selecccccccccccccctttttttttttttttt image
    private void selectImage() {
        final CharSequence[] items = {"Upload", "Upload from Gallery","Back"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
        builder.setTitle("Upload!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(UpdateProfile.this);

                if (items[item].equals("Upload")) {
                    if (result)
                        if (CameraUtils__TRD.checkPermissions(UpdateProfile.this)) {
                            //    Toast.makeText(Single_user_act_TRD.this, "Capture image called", Toast.LENGTH_SHORT).show();
                            captureImage();
                            //     Toast.makeText(Single_user_act_TRD.this, "we got result back", Toast.LENGTH_SHORT).show();

                            restoreFromBundle(savedInstanceState);
                        } else {
                            requestCameraPermission(MEDIA_TYPE_IMAGE_TRD);
                        }
                    // cameraIntent();

                } else if (items[item].equals("Back")) {

                    dialog.dismiss();
                }else if(items[item].equals("Upload from Gallery"))
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),Gallery_view);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

    }
    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH_TRD)) {
                imageStoragePath_TRD = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH_TRD);
                if (!TextUtils.isEmpty(imageStoragePath_TRD)) {
                    if (imageStoragePath_TRD.substring(imageStoragePath_TRD.lastIndexOf(".")).equals("." + IMAGE_EXTENSION_TRD)) {
                        previewCapturedImage();
                    }
                }
            }
        }
    }


    private void captureImage() {

        int ask_again = 0;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils__TRD.getOutputMediaFile_TRD(MEDIA_TYPE_IMAGE_TRD);
        if (file != null) {
            imageStoragePath_TRD = file.getAbsolutePath();
            //  Toast.makeText(this, " Uploading", Toast.LENGTH_SHORT).show();
//            CameraUtils__TRD.refreshGallery(getApplicationContext(), imageStoragePath);
            // start the image capture Intent

//                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            //   Toast.makeText(getApplicationContext() ,"Step 2",Toast.LENGTH_LONG).show();
        }
        if (file == null) {
            Toast.makeText(this, "Not getting scan images please try again", Toast.LENGTH_SHORT).show();
        }
        Uri fileUri = CameraUtils__TRD.getOutputMediaFileUri_TRD(UpdateProfile.this, file);
        //    Toast.makeText(this, ""+fileUri, Toast.LENGTH_SHORT).show();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE_TRD);

    }

    private void requestCameraPermission(final int mediaTypeImageTrd) {

        Dexter.withActivity(this)
                .withPermissions(android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (mediaTypeImageTrd == MEDIA_TYPE_IMAGE_TRD) {
                                // capture picture
                                captureImage();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }



    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils__TRD.openSettings(UpdateProfile.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private class ImageCompression extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length == 0 || strings[0] == null)
                return null;

            return CommonUtils_TRD.compressImage_TRD(strings[0]);
        }

        protected void onPostExecute(String imagePath) {
            // imagePath is path of new compressed image.
//            mivImage.setImageBitmap(BitmapFactory.decodeFile(new File(imagePath).getAbsolutePath()));
            try {


                pdfFile = new File(imagePath);
                //Toast.makeText(Single_user_act_TRD.this, ""+imgFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                if (pdfFile.exists()) {
                    Toast.makeText(UpdateProfile.this, "successfully taken", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UpdateProfile.this, "Please select an image first", Toast.LENGTH_SHORT).show();
                }
            } catch (NullPointerException e) {
                Toast.makeText(UpdateProfile.this, "Having some problem with image ,please try again registering", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class UPdateMyProfile extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String result = "";



        protected void onPreExecute() {
            dialog = new ProgressDialog(UpdateProfile.this);
            dialog.show();

        }

        @SuppressLint("WrongThread")
        protected String doInBackground(String... arg0) {



            try {


                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                entity.addPart("user_id", new StringBody("" +new SessionManager(UpdateProfile.this).getWaiterName()));
                if(pdfFile != null) {
                    entity.addPart("profile_image", new FileBody(pdfFile));
                }else {
                    entity.addPart("profile_image", new StringBody(""));
                }
                entity.addPart("name", new StringBody(Name.getText().toString()));
                entity.addPart("mobile", new StringBody(Mobet.getText().toString()));
                entity.addPart("email", new StringBody(Email.getText().toString()));
                entity.addPart("password", new StringBody(Passedt.getText().toString()));
                entity.addPart("state", new StringBody(State.getText().toString()));
                entity.addPart("town", new StringBody(Town.getText().toString()));
                entity.addPart("district", new StringBody(Distric.getText().toString()));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                result = Utilities.postEntityAndFindJson(Urls.Base_Url +Urls.User_update, entity);

//                    entity.addPart("return_copy_upload", new FileBody(pic0));
//                    result = Utilities.postEntityAndFindJson("http://ihisaab.com/ihisaabv2/Api/taxreturn", entity);
//                    c1=0;


                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    //  String response = jsonObject.getString("responce");
                    //   Log.e("Response is", response);
                    if(jsonObject.getBoolean("responce")) {
                        String massage = jsonObject.getJSONObject("massage").getString("user_id");
                        Toast.makeText(UpdateProfile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfile.this , NavigationActivity.class);
                        sessionManager.setLogin(true);
                        sessionManager.serverEmailLogin(jsonObject.getJSONObject("massage").getString("user_id"));
//                    final int uSer_id = Log.e("USer id", "" + new SessionManager(LoginActivity.this).getWaiterName());
                        sessionManager.serverEmailLogin(jsonObject.getJSONObject("massage").getString("name"),jsonObject.getJSONObject("massage").getString("email"),jsonObject.getJSONObject("massage").getString("mobile"));
                        startActivity(intent);
                        NavigationActivity.navController.popBackStack();
                        NavigationActivity.navController=null;
                        finish();
                    }else {
                        Toast.makeText(UpdateProfile.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    // on ACCCCCCCCCCCCCCCCCCCCCCCCCCCCTIVTY RESULT///////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       /* if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else */
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE_TRD) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                //   Toast.makeText(Single_user_act_TRD.this, " your pan no is "+pan, Toast.LENGTH_SHORT).show();
                // CameraUtils__TRD.refreshGallery(getApplicationContext(), imageStoragePath);
                //  Toast.makeText(this, "called once", Toast.LENGTH_SHORT).show();
                previewCapturedImage();
                captureImage();

            } else if (resultCode == RESULT_CANCELED) {

                //  selectImage();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to upload image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == Gallery_view && data != null) {
            try{
                Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                Uri pickedImage = data.getData();
                pdfFile = null;
                String[] filePath = { MediaStore.Images.Media.DATA };
                if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N){
                    //     getRealPathFromURI(Single_user_act_TRD.this , pickedImage);


//                  pickedImage =   FileProvider.getUriForFile(Single_user_act_TRD.this, BuildConfig.APPLICATION_ID + ".provider",pdfFile);
                    //   pickedImage =   FileProvider.getUriForFile(Single_user_act_TRD.this, getApplicationContext().getPackageName() + ".provider",pdfFile);

                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.e("Gallery image path is:" , ""+imagePath);
                    imageStoragePath_TRD = imagePath;
                    pdfFile = new File(imagePath);

                    cursor.close();
                    if (pdfFile!=null) {

                        if(pdfFile.exists()){
                            Log.e("PDF ",""+pdfFile.exists());
                            //   new ImageUploadPDF(pdfFile).execute();
                            imageStoragePath_TRD =imagePath;
                        }
                        Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                        //   new ImageCompression().execute(imagePath);
                        //   select_registrationplate.setImageURI(Uri.fromFile(imgFile));
                        //  show(imgFile);
                        // new ImageUploadTask(new File(imagePath)).execute();
                        //   Toast.makeText(ReportSummary.this, "data:=" + imgFile, Toast.LENGTH_LONG).show();
                    }

                }
                else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                    if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(getApplicationContext(), pickedImage)) {
//                        if (isExternalStorageDocument(uri)) {
//                            final String docId = DocumentsContract.getDocumentId(uri);
//                            final String[] split = docId.split(":");
//                            return Environment.getExternalStorageDirectory() + "/" + split[1];
//                        }
//                    }
                    File extStore = Environment.getExternalStorageDirectory();
//                    File myFile = new File(extStore.getAbsolutePath() + "/book1/page2.html");
                    String pdf_file_path;
                    Uri uri = data.getData();
                    pdfFile = new File(uri.getPath());//create path from uri
                    final String[] split = pdfFile.getPath().split(":");//split the path.
                    pdf_file_path = split[1];
                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.e("Gallery image path is:" , ""+imagePath);
                    imageStoragePath_TRD = imagePath;

                    pdfFile = new File(extStore.getAbsolutePath(),pdf_file_path);

                    cursor.close();
                    if (pdfFile!=null) {

                        if (pdfFile.exists()) {
                            Log.e("PDF ", "" + pdfFile.exists());
                            //   new ImageUploadPDF(pdfFile).execute();

//                            new ImageUploadTask(pdfFile).execute();
                        }
                        Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                    }


                } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.e("Gallery image path is:" , ""+imagePath);
                    imageStoragePath_TRD = imagePath;
                    pdfFile = new File(imagePath);
                    if (pdfFile!=null) {

                        if(pdfFile.exists()){
                            Log.e("PDF ",""+pdfFile.exists());


                            //   new ImageUploadPDF(pdfFile).execute();
//                            new ImageUploadTask(pdfFile).execute();
                        }

                    }
                    Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                }



            }catch (Exception e){
                Toast.makeText(this, "Not getting file from phone please upload valid .pdf", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class GETMYDATA extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;



        protected void onPreExecute() {
            dialog = new ProgressDialog(UpdateProfile.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Userdata+"/"+new SessionManager(UpdateProfile.this).getWaiterName());

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("email", EMail);
//                postDataParams.put("password", Pass);



                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result).getJSONObject("responce");
                    //  String response = jsonObject.getString("responce");
                    //   Log.e("Response is", response);
                    String massage = jsonObject.getString("user_id");

                    String customer_id = jsonObject.getString("customer_id");

                    String user_type = jsonObject.getString("user_type");
                    String name = jsonObject.getString("name");
                    Name.setText(name);
                    String email = jsonObject.getString("email");
                    Email.setText(email);
                    String mobile = jsonObject.getString("mobile");
                    Mobet.setText(mobile);
                    String state = jsonObject.getString("state");
                    State.setText(state);
                    String password = jsonObject.getString("password");
                    Passedt.setText(password);
                    String town = jsonObject.getString("town");
                    Town.setText(town);
                    String district = jsonObject.getString("district");
                    Distric.setText(district);
                //    Toast.makeText(UpdateProfile.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateProfile.this , NavigationActivity.class);
                    sessionManager.setLogin(true);
                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("data").getString("user_id"));
//                    final int uSer_id = Log.e("USer id", "" + new SessionManager(LoginActivity.this).getWaiterName());
                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("data").getString("name"),jsonObject.getJSONObject("data").getString("email"),jsonObject.getJSONObject("data").getString("mobile"));
                    startActivity(intent);
                    NavigationActivity.navController.popBackStack();
                    NavigationActivity.navController=null;
                    finish();
                    // String cus_code = dt.getString("cust_code");
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    // Toast.makeText(SingleUser.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    //     Log.e("Your Customer code is", cus_code);



                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }
}
