//package com.nexto.myapplicationt;
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.FileProvider;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.huawei.hiai.vision.common.ConnectionCallback;
//import com.huawei.hiai.vision.common.VisionBase;
//import com.huawei.hiai.vision.cvdemo.R;
//import com.huawei.hiai.vision.cvdemo.View.SemSegView;
//import com.huawei.hiai.vision.image.detector.SceneDetector;
//import com.huawei.hiai.vision.image.docrefine.DocRefine;
//import com.huawei.hiai.vision.image.landmark.LandMark;
//
//import com.huawei.hiai.vision.image.sr.TxtImageSuperResolution;
//import com.huawei.hiai.vision.visionkit.common.Frame;
//import com.huawei.hiai.vision.visionkit.image.detector.LandMarkInfo;
//
//
//import org.json.JSONObject;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * Created by c00316505 on 2018/2/7.
// */
//
//public class TestActivity extends Activity {
//    private static final String LOG_TAG = "face_demo";
//    private static final int REQUEST_IMAGE_CAPTURE = 100;
//    private static final int REQUEST_IMAGE_SELECT = 200;
//    public static final int MEDIA_TYPE_IMAGE = 1;
//    private Button btnCamera;
//    private Button btnSelect;
//    private ImageView ivCaptured;
//    private TextView tvLabel;
//    private Uri fileUri;
//    private Bitmap bmp;
//
//    private ImageView dstView;
//    private Handler mMyHandler = null;
//    private MyHandlerThread mMyHandlerThread = null;
//    private Button btnLm;
//    String result;
//    LandMark lm;
//    private void initPrediction() {
//        btnCamera.setEnabled(false);
//        btnSelect.setEnabled(false);
//        tvLabel.setText("");
//    }
//    SemSegView ssv;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wgjc);
//        mMyHandlerThread = new MyHandlerThread();
//        mMyHandlerThread.start();
//        mMyHandler = new Handler(mMyHandlerThread.getLooper(), mMyHandlerThread);
//        ivCaptured = (ImageView) findViewById(R.id.ivCaptured);
//        dstView = (ImageView) findViewById(R.id.dst);
//        tvLabel = (TextView) findViewById(R.id.tvLabel);
//
//        ssv = (SemSegView)findViewById(R.id.landmarkpaint) ;
//
//        btnCamera = (Button) findViewById(R.id.btnCamera);
//        btnCamera.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                initPrediction();
//                //Log.d(LOG_TAG, "get uri");
//                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                Log.d(LOG_TAG, "end get uri = " + fileUri);
//                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
//            }
//        });
//
//        btnSelect = (Button) findViewById(R.id.btnSelect);
//        btnSelect.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                initPrediction();
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, REQUEST_IMAGE_SELECT);
//            }
//        });
//
//        btnLm = (Button) findViewById(R.id.docdet);
//        btnLm.setText("五官");
//        btnLm.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                mMyHandler.sendEmptyMessage(6);
//            }
//        });
//        btnLm.setEnabled(false);
//
//
//        VisionBase.init(this, new ConnectionCallback() {
//            @Override
//            public void onServiceConnect() {
//                mHandler.sendEmptyMessage(1);
//            }
//
//            @Override
//            public void onServiceDisconnect() {
//                mHandler.sendEmptyMessage(2);
//            }
//        });
//        //        sceneDetector = new SceneDetector(MainActivity.this);
//
//        requestPermissions();
//    }
//    private class MyHandlerThread extends HandlerThread implements Handler.Callback {
//        public MyHandlerThread() {
//            super("MyHandler");
//            // TODO Auto-generated constructor stub
//        }
//
//        public MyHandlerThread(String name) {
//            super(name);
//            // TODO Auto-generated constructor stub
//        }
//
//        @Override
//        public boolean handleMessage(Message arg0) {
//            switch (arg0.what) {
//                case 6:
//                    Frame f6 = new Frame();
//                    f6.setBitmap(bmp);
//                    long s6 = System.currentTimeMillis();
//                    JSONObject lmf = lm.detectLandMark(f6, null);
//                    if (lmf == null) {
//                        return false;
//                    }
//                    result = lmf.toString();
//                    info = lm.convertResult(lmf);
//                    long end6= System.currentTimeMillis();
//                    Log.e(LOG_TAG, "landmark need time:" + (end6 - s6));
//                    Log.e(LOG_TAG, "info:" + info);
//                    mHandler.sendEmptyMessage(66);
//                    break;
//                default:
//                    break;
//            }
//            return false;
//        }
//
//    }
//    LandMarkInfo info = null;
//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    Log.d(LOG_TAG, "bind ok ");
//                    Toast.makeText(LandMarkActivity.this, "bind success", Toast.LENGTH_SHORT).show();
//                    lm = new LandMark(LandMarkActivity.this);
//                    break;
//                case 2:
//                    Toast.makeText(LandMarkActivity.this, "disconnect", Toast.LENGTH_SHORT).show();
//                    break;
//                case 66:
//                    ivCaptured.setImageBitmap(bmp);
//                    btnCamera.setEnabled(true);
//                    btnSelect.setEnabled(true);
//                    ssv.setArr(info.getXarray(), info.getYarray());
//                    break;
//            }
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if ((requestCode == REQUEST_IMAGE_CAPTURE || requestCode == REQUEST_IMAGE_SELECT) && resultCode == RESULT_OK) {
//            String imgPath;
//
//            if (requestCode == REQUEST_IMAGE_CAPTURE) {
//                //imgPath = fileUri.getPath();
//                imgPath = Environment.getExternalStorageDirectory() + fileUri.getPath();
//            } else {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = LandMarkActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgPath = cursor.getString(columnIndex);
//                cursor.close();
//            }
//            Log.d(LOG_TAG, "imgPath = " + imgPath);
//            bmp = BitmapFactory.decodeFile(imgPath);
//
//            btnLm.setEnabled(true);
//
//            Log.d(LOG_TAG, "bitmap = " + imgPath);
//        } else {
//            btnCamera.setEnabled(true);
//            btnSelect.setEnabled(true);
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    /**
//     * Create a file Uri for saving an image or video
//     */
//    private Uri getOutputMediaFileUri(int type) {
//        //return Uri.fromFile(getOutputMediaFile(type));
//        Log.d(LOG_TAG, "authority = " + getPackageName() + ".provider");
//        Log.d(LOG_TAG, "getApplicationContext = " + getApplicationContext());
//        return FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", getOutputMediaFile(type));
//
//    }
//
//    /**
//     * Create a File for saving an image or video
//     */
//    private static File getOutputMediaFile(int type) {
//        // To be safe, you should check that the SDCard is mounted
//        // using Environment.getExternalStorageState() before doing this.
//
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Caffe-Android-Demo");
//        // This location works best if you want the created images to be shared
//        // between applications and persist after your app has been uninstalled.
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
//
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile;
//        if (type == MEDIA_TYPE_IMAGE) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                    "IMG_" + timeStamp + ".jpg");
//        } else {
//            return null;
//        }
//        Log.d(LOG_TAG, "mediaFile " + mediaFile);
//        return mediaFile;
//    }
//
//
//    private void requestPermissions() {
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                int permission = ActivityCompat.checkSelfPermission(this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                if (permission != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0x0010);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    public void onDestroy() {
//        lm.release();
//        VisionBase.destroy();
//        mMyHandlerThread.quit();
//        super.onDestroy();
//    }
//    public class SemSegView extends View {
//        private float xArr[] = null; //像素为单位
//        private float yArr[] = null;
//        Paint p = new Paint();
//
//        public SemSegView(Context context) {
//            super(context);
//            p.setStyle(Paint.Style.FILL_AND_STROKE);
//            p.setStrokeWidth(10);
//            p.setColor(Color.BLACK);
//        }
//        public SemSegView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//            p.setStyle(Paint.Style.FILL_AND_STROKE);
//            p.setStrokeWidth(10);
//            p.setColor(Color.BLACK);
//        }
//        public void setArr(float[] xa, float[] ya) {
//            xArr = new float[xa.length];
//            yArr = new float[ya.length];
//            System.arraycopy(xa, 0, xArr,0,xa.length);
//            System.arraycopy(ya, 0, yArr,0,ya.length);
////        invalidate();
//            postInvalidateDelayed(1000);
//        }
//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
////        canvas.drawPoint(250, 250, p);
//            if (xArr == null || yArr == null) {
//                return;
//            }
//
//
//            canvas.drawPoint(10, 10, p);
//            int len = xArr.length;
//            for (int i = 0; i< len ; i++) {
//                canvas.drawPoint(xArr[i], yArr[i], p);
//            }
//
//        }
//    }