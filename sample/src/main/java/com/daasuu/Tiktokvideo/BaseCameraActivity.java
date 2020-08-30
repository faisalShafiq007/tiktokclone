package com.daasuu.Tiktokvideo;

import PackageTiktok.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLException;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.daasuu.Tiktokvideo.Tiktok.camerarecorder.CameraRecordListener;
import com.daasuu.Tiktokvideo.Tiktok.camerarecorder.CustomListview;
import com.daasuu.Tiktokvideo.Tiktok.camerarecorder.GPUCameraRecorderBuilder;
import com.daasuu.Tiktokvideo.Tiktok.camerarecorder.LensFacing;
import com.daasuu.Tiktokvideo.Tiktok.egl.filter.GlWatermarkFilter;
import com.daasuu.Tiktokvideo.widget.SampleCameraGLView;


import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BaseCameraActivity extends AppCompatActivity {

    private SampleCameraGLView sampleGLView;
    protected com.daasuu.Tiktokvideo.Tiktok.camerarecorder.GPUCameraRecorder GPUCameraRecorder;
    private String filepath;
   TextView filterbtn;
    private ImageView recordBtn;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    protected int videoWidth = 720;
    protected int videoHeight = 720;
    int i=0;
    TextView songbtn;

    private boolean toggleClick = false;
    private ListView lv;
    ListView lssong;
    MediaPlayer mediaPlayer;

    protected void onCreateActivity() {
        getSupportActionBar().hide();

        songbtn=findViewById(R.id.songsbutton);
        lv=findViewById(R.id.filter_list);
        String[] places={"Madhubala","Paise le lo","Do Dharri talwar","Jind Mahi",
                "Hulla Re","Angrej Tappe","Pehla Pehla pyar"};
        Integer[] imageid={R.drawable.music,R.drawable.music,R.drawable.music,R.drawable.music,R.drawable.music,R.drawable.music
        ,R.drawable.music};
        mediaPlayer = new MediaPlayer();
        filterbtn=findViewById(R.id.safilterterbutn);
        lssong=findViewById(R.id.song_list);
        CustomListview customListview=new CustomListview(BaseCameraActivity.this, imageid, places);
        lssong.setAdapter(customListview);
        lssong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





                mediaPlayer.stop();
if(places[position]=="Madhubala"){
    mediaPlayer = MediaPlayer.create(BaseCameraActivity.this, R.raw.three);
    Toast.makeText(BaseCameraActivity.this, places[position] +"selected Press record to start song &record" , Toast.LENGTH_SHORT).show();

}
else if(places[position]=="Paise le lo"){
    mediaPlayer = MediaPlayer.create(BaseCameraActivity.this, R.raw.four);
    Toast.makeText(BaseCameraActivity.this, places[position] +"selected Press record to start song &record" , Toast.LENGTH_SHORT).show();


}
else if(places[position]=="Do Dharri talwar"){
    mediaPlayer = MediaPlayer.create(BaseCameraActivity.this, R.raw.two);

    Toast.makeText(BaseCameraActivity.this, places[position] +"selected Press record to start song &record" , Toast.LENGTH_SHORT).show();

}
else if(places[position]=="Jind Mahi"){
    mediaPlayer = MediaPlayer.create(BaseCameraActivity.this, R.raw.five);
    Toast.makeText(BaseCameraActivity.this, places[position] +"selected Press record to start song &record" , Toast.LENGTH_SHORT).show();

}
else if(places[position]=="Hulla Re"){
    mediaPlayer = MediaPlayer.create(BaseCameraActivity.this, R.raw.six);
    Toast.makeText(BaseCameraActivity.this, places[position] +"selected Press record to start song &record" , Toast.LENGTH_SHORT).show();

}
else if(places[position]=="Angrej Tappe"){
    mediaPlayer = MediaPlayer.create(BaseCameraActivity.this, R.raw.one);
    Toast.makeText(BaseCameraActivity.this, places[position] +"selected Press record to start song &record" , Toast.LENGTH_SHORT).show();

}
else if(places[position]=="Pehla Pehla pyar"){
    mediaPlayer = MediaPlayer.create(BaseCameraActivity.this, R.raw.seven);
    Toast.makeText(BaseCameraActivity.this, places[position] +"selected Press record to start song &record" , Toast.LENGTH_SHORT).show();

}

            }
        });
        recordBtn = findViewById(R.id.btn_record);
        recordBtn.setOnClickListener(v -> {
            recordBtn.setVisibility(View.VISIBLE);
            if (i==0) {
                new CountDownTimer(18000, 1000) {

                    public void onTick(long millisUntilFinished) {
                      /*  String  a=("seconds remaining: " + millisUntilFinished / 2500);*/
                        //here you can have your logic to set text to edittext
                        int check= (int) millisUntilFinished;
Log.e("original", String.valueOf(check));
                        int time= (int) (millisUntilFinished/50);
                                Log.e("timeis", String.valueOf(time));
                        if(time==359){
                            recordBtn.setImageResource(R.drawable.three);
                        }
                        if(time==339){
                            recordBtn.setImageResource(R.drawable.two);
                        }
                        if(time==319){
                            recordBtn.setImageResource(R.drawable.one);
                        }
if(time==299){
    Toast.makeText(BaseCameraActivity.this,"Start Recording",Toast.LENGTH_SHORT).show();
    if(mediaPlayer!=null){mediaPlayer.start();}
    filepath = getVideoFilePath();
    recordBtn.setVisibility(View.INVISIBLE);
    GPUCameraRecorder.start(filepath);
    lssong.setVisibility(View.GONE);
    songbtn.setVisibility(View.INVISIBLE);
    lv.setVisibility(View.GONE);
    filterbtn.setVisibility(View.GONE);
    i=1;
    recordBtn.setImageResource(R.drawable.pause);
}
                    }

                    public void onFinish() {
                        recordBtn.setVisibility(View.VISIBLE);
                        if(mediaPlayer!=null){mediaPlayer.stop();}
                        Toast.makeText(BaseCameraActivity.this,"Stop And saved in gallery",Toast.LENGTH_SHORT).show();
                        GPUCameraRecorder.stop();
                        recordBtn.setImageResource(R.drawable.play);
                        i=0;  lssong.setVisibility(View.VISIBLE);
                        songbtn.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.VISIBLE);
                        filterbtn.setVisibility(View.VISIBLE);

                    }

                }.start();


            } else {
                if(mediaPlayer!=null){mediaPlayer.stop();}
                Toast.makeText(BaseCameraActivity.this,"Stop And saved in gallery",Toast.LENGTH_SHORT).show();
                GPUCameraRecorder.stop();
                recordBtn.setImageResource(R.drawable.play);
                i=0;  lssong.setVisibility(View.VISIBLE);
                songbtn.setVisibility(View.VISIBLE);
                lv.setVisibility(View.VISIBLE);
                filterbtn.setVisibility(View.VISIBLE);

            }

        });
        findViewById(R.id.btn_flash).setOnClickListener(v -> {
            if (GPUCameraRecorder != null && GPUCameraRecorder.isFlashSupport()) {
                GPUCameraRecorder.switchFlashMode();
                GPUCameraRecorder.changeAutoFocus();
            }
            Toast.makeText(BaseCameraActivity.this,"Flash",Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_switch_camera).setOnClickListener(v -> {
            Toast.makeText(BaseCameraActivity.this,"Camera Rotated",Toast.LENGTH_SHORT).show();
            releaseCamera();
            if (lensFacing == LensFacing.BACK) {
                lensFacing = LensFacing.FRONT;
            } else {
                lensFacing = LensFacing.BACK;
            }
            toggleClick = true;
        });

        findViewById(R.id.btn_image_capture).setOnClickListener(v -> {
            Toast.makeText(BaseCameraActivity.this,"Image captured",Toast.LENGTH_SHORT).show();
            captureBitmap(bitmap -> {
                new Handler().post(() -> {
                    String imagePath = getImageFilePath();
                    saveAsPngImage(bitmap, imagePath);
                    exportPngToGallery(getApplicationContext(), imagePath);
                });
            });
        });

        lv = findViewById(R.id.filter_list);

        final List<FilterType> filterTypes = FilterType.createFilterList();
        lv.setAdapter(new FilterAdapter(this, R.layout.row_white_text, filterTypes).whiteMode());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (GPUCameraRecorder != null) {
                    GPUCameraRecorder.setFilter(FilterType.createGlFilter(filterTypes.get(position), getApplicationContext()));

                }
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void countdown(int o,int time) {

Log.e("harami",String.valueOf(o) );

         new Thread(new Runnable() {
             @Override
             public void run() {
            try{
                Thread.sleep(time);
            }   catch (Exception e){
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recordBtn.setImageResource(o);
                }
            });
             }
         });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }

    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (GPUCameraRecorder != null) {
            GPUCameraRecorder.stop();
            GPUCameraRecorder.release();
            GPUCameraRecorder = null;
        }

        if (sampleGLView != null) {
            ((FrameLayout) findViewById(R.id.wrap_view)).removeView(sampleGLView);
            sampleGLView = null;
        }
    }

    @Override
    public void onBackPressed() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
        super.onPause();
    }


    private void setUpCameraView() {
        runOnUiThread(() -> {
            FrameLayout frameLayout = findViewById(R.id.wrap_view);
            frameLayout.removeAllViews();
            sampleGLView = null;
            sampleGLView = new SampleCameraGLView(getApplicationContext());
            sampleGLView.setTouchListener((event, width, height) -> {
                if (GPUCameraRecorder == null) return;
                GPUCameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
            });
            frameLayout.addView(sampleGLView);
        });
    }


    private void setUpCamera() {
        setUpCameraView();

        GPUCameraRecorder = new GPUCameraRecorderBuilder(this, sampleGLView)
                //.recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(boolean flashSupport) {
                        runOnUiThread(() -> {
                            findViewById(R.id.btn_flash).setEnabled(flashSupport);
                        });
                    }

                    @Override
                    public void onRecordComplete() {
                        exportMp4ToGallery(getApplicationContext(), filepath);
                    }

                    @Override
                    public void onRecordStart() {
                        runOnUiThread(() -> {
                            lv.setVisibility(View.GONE);
                        });
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("GPUCameraRecorder", exception.toString());
                    }

                    @Override
                    public void onCameraThreadFinish() {
                        if (toggleClick) {
                            runOnUiThread(() -> {
                                setUpCamera();
                            });
                        }
                        toggleClick = false;
                    }

                    @Override
                    public void onVideoFileReady() {

                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .build();


    }

//    private void changeFilter(Filters filters) {
//        GPUCameraRecorder.setFilter(Filters.getFilterInstance(filters, getApplicationContext()));
//    }


    private interface BitmapReadyCallbacks {
        void onBitmapReady(Bitmap bitmap);
    }

    private void captureBitmap(final BitmapReadyCallbacks bitmapReadyCallbacks) {
        sampleGLView.queueEvent(() -> {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();
            Bitmap snapshotBitmap = createBitmapFromGLSurface(sampleGLView.getMeasuredWidth(), sampleGLView.getMeasuredHeight(), gl);

            runOnUiThread(() -> {
                bitmapReadyCallbacks.onBitmapReady(snapshotBitmap);
            });
        });
    }

    private Bitmap createBitmapFromGLSurface(int w, int h, GL10 gl) {

        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2, texturePixel, blue, red, pixel;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    texturePixel = bitmapBuffer[offset1 + j];
                    blue = (texturePixel >> 16) & 0xff;
                    red = (texturePixel << 16) & 0x00ff0000;
                    pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e("CreateBitmap", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }

    public void saveAsPngImage(Bitmap bitmap, String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void exportMp4ToGallery(Context context, String filePath) {
        final ContentValues values = new ContentValues(2);
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                values);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + filePath)));
    }

    public static String getVideoFilePath() {
        return getAndroidMoviesFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "GPUCameraRecorder.mp4";
    }

    public static File getAndroidMoviesFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    private static void exportPngToGallery(Context context, String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static String getImageFilePath() {
        return getAndroidImageFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "GPUCameraRecorder.png";
    }

    public static File getAndroidImageFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}
