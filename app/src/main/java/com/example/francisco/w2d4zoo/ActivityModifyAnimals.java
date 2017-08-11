package com.example.francisco.w2d4zoo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ActivityModifyAnimals extends AppCompatActivity implements View.OnClickListener {
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private static final String TAG = "Main";
    private WebView webView;
    ImageButton img;
    Button btnSave, btnDel;
    EditText etname, etdescription, ethabitat;
    Bitmap bitmap;
    String filepath = "";
    String source = "";
    DatabaseHelper databaseHelper;
    String id = "-1";
    MediaPlayer mp3;
    ImageButton btnPlay, btnPause, btnStop;
    byte [] song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_animals);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        etname = (EditText) findViewById(R.id.etname);
        etdescription = (EditText) findViewById(R.id.etdescription);
        ethabitat = (EditText) findViewById(R.id.ethabitat);

//        btnSave = (Button) findViewById(R.id.btnSave);
//        btnDel = (Button) findViewById(R.id.btnDel);
        img = (ImageButton) findViewById(R.id.img);

//        btnSave.setOnClickListener(this);
//        btnDel.setOnClickListener(this);

//        img.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);

        try {
            Intent intent = getIntent();
            Animals a = intent.getParcelableExtra(getString(R.string.Key2));
            id = ""+a.getCategory_id();
            if(Integer.parseInt(id)>=0){
//                btnSave.setText("Edit");
//                btnSave.setBackgroundColor(Color.parseColor("#F0AD4E"));
            }
            if(!id.equals("")) {
                ArrayList<Animals> animals = databaseHelper.getAnimals(id);
                if (animals.size() > 0) {
                    etname.setText(animals.get(0).getAnimal_name());
                    etdescription.setText(animals.get(0).getDescription());
                    ethabitat.setText(animals.get(0).getHabitat());
                    try {
                        byte[] b = animals.get(0).getImg();
                        bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                        img.setImageBitmap(bitmap);
                    }catch(Exception e){}

                    btnPlay = (ImageButton) findViewById(R.id.btnPlay);
                    btnPause = (ImageButton) findViewById(R.id.btnPause);
                    btnStop = (ImageButton) findViewById(R.id.btnStop);

                    btnPlay.setOnClickListener(this);
                    btnPause.setOnClickListener(this);
                    btnStop.setOnClickListener(this);
                    song = animals.get(0).getSound();
                    playMp3(song);


                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            Log.d(TAG, "onCreate: "+ex.getMessage());
        }

        ArrayList <Categories> categories = databaseHelper.getCategories("-1");
        List<String> c = new ArrayList<String>();

        for(int i=0;i<categories.size();i++){
            c.add(categories.get(i).getCategory_name());
        }
        Spinner spinner = (Spinner) findViewById(R.id.scategory);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, c);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed() {
        if(mp3 != null) {
            mp3.stop();
            mp3.release();
            mp3 = null;
            Log.d(TAG, "onClick: " + mp3);
        }
        super.onBackPressed();
    }

    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            mp3 = new MediaPlayer();
//            File someFile = new File(Environment.getExternalStorageDirectory() + File.separator + "tours2.mp3");
//            FileOutputStream fos2 = new FileOutputStream(someFile);
//            fos2.write(mp3SoundByteArray);
//            fos2.flush();
//            fos2.close();
//
//            Log.d(TAG, "playMp3:  "+Environment.getExternalStorageDirectory() + File.separator+" "+song);
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("temp_song", ".mp3", getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            // resetting mediaplayer instance to evade problems
            if(mp3!=null)
                mp3.reset();

            // In case you run into issues with threading consider new instance like:
            // MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            Log.d(TAG, "playMp3: "+tempMp3.getAbsoluteFile());
            mp3.setDataSource(fis.getFD());

            mp3.prepare();
            mp3.start();
        } catch (Exception ex) {
            String s = ex.toString();
            ex.printStackTrace();
            Log.d(TAG, "playMp3: "+ex.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bitmap!=null) {
            outState.putString("img", "uploaded");
            outState.putString("source", source);
            outState.putString("filepath", filepath);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            if (savedInstanceState.getString("img").equals("uploaded")) {
                filepath = savedInstanceState.getString("filepath");
                File file = new File(filepath);
                source = savedInstanceState.getString("source");
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    //Landscape do some stuff

                    if(!savedInstanceState.getString("source").equals("landscape")){
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        img.setImageBitmap(bitmap2);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024, 768);
                        img.setImageBitmap(bitmap);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                } else {
                    //portrait

                    //rotates img
                    if(savedInstanceState.getString("source").equals("portrait")) {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        img.setImageBitmap(bitmap2);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024 , 768);
                        img.setImageBitmap(bitmap);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            }
        }catch(Exception ex){}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
            //Get our saved file into a bitmap object:

            File file = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "image.jpg");
            filepath = file.getAbsolutePath();
            Log.d(TAG, "onActivityResult: "+file.getAbsolutePath());

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //Landscape do some stuff
                source = "landscape";
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                img.setImageBitmap(bitmap);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            else{
                //portrait
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024 , 768);
                //rotates img
                source = "portrait";
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                img.setImageBitmap(bitmap2);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
            }



        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnPlay:
                playMp3(song);
            break;
            case R.id.btnPause:
                if(mp3 != null)
                    if(mp3.isPlaying())
                        mp3.pause();
                break;
            case R.id.btnStop:
                if(mp3 != null) {
                    mp3.stop();
                    mp3.release();
                    mp3 = null;
                    Log.d(TAG, "onClick: " + mp3);
                }
                break;
//            case R.id.btnSave:
//                if(etname.getText().toString().equals("") || etdescription.getText().toString().equals("")){
//                    Toast.makeText(this, "Name and description are required", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    byte[] b = null;
//                    if (bitmap != null) {
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//                        b = stream.toByteArray();
//                    }
//                    Animals animal = new Animals(-1, 1,etname.getText().toString(), etdescription.getText().toString(),
//                            ethabitat.getText().toString(), song, b);
//                    Log.d(TAG, "onClick: " + animal.getAnimal_name());
//                    try {
//                        if (Integer.parseInt(id) >= 0) {
//                            databaseHelper.uploadNewAnimal(animal, id);
//                            Toast.makeText(this, "Element updated successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            databaseHelper.saveNewAnimal(animal);
//                            Toast.makeText(this, "Element created successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception ex) {
//                        databaseHelper.saveNewAnimal(animal);
//                        Toast.makeText(this, "Element created successfully", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                break;
//            case R.id.btnDel:
//                try {
//                    if (Integer.parseInt(id) >= 0) {
//                        databaseHelper.DeleteAnimal(id);
//                        bitmap = null;
//                        etname.setText("");
//                        etdescription.setText("");
//                        img.setImageResource(R.drawable.ic_person);
//                        Toast.makeText(this, "Element deleted successfully", Toast.LENGTH_SHORT).show();
//                        if(mp3 != null) {
//                            song = null;
//                            mp3.stop();
//                            mp3.release();
//                            mp3 = null;
//                        }
//                    }
//                }catch(Exception ex){}
//
//                break;
//            case R.id.img:
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//                startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
//                break;
        }
    }
}
