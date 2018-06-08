package com.nexto.myapplicationt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.hiai.vision.visionkit.common.Frame;//加载Frame类
import com.huawei.hiai.vision.face.FaceLandMarkDetector;//加载五官检测方法类
import com.huawei.hiai.vision.visionkit.face.FaceLandmark;//加载返回结果类
import com.huawei.hiai.vision.common.VisionBase;//加载连接服务的静态类
import com.huawei.hiai.vision.common.ConnectionCallback;//加载连接服务的回调函数

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            textView.setText(json);
            Log.d("aaaa", json);
//绘制脸部识别点
            bitmap=bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);//宽度
            canvas.drawPoints(points,paint);
            canvas.save();
            ImageView iv =findViewById(R.id.iv);
            iv.setImageBitmap(bitmap);

        }
    };
    private String json;
    private TextView textView;
    private ImageView iv;
    private Bitmap bitmap;
    private JSONObject resultObj;
    private float[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.d("aaaa", "initview完成");


        VisionBase.init(MainActivity.this, new ConnectionCallback() {
            @Override
            public void onServiceConnect() {
            }

            @Override
            public void onServiceDisconnect() {
            }
        });
    }


    public void click(final View view) throws JSONException {
        Log.d("aaaa", "点击按钮");


        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.d("aaaa", "开启子线程");


                try {
//                    InputStream inputStream = getAssets().open("a.jpg");
//                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //获取Bitmap图像(ARGB888格式)
                Frame frame = new Frame();//构造Frame对象
                frame.setBitmap(bitmap);
                FaceLandMarkDetector faceLMDetector = new FaceLandMarkDetector(MainActivity.this);
                //进行五官特征检测；
                resultObj = faceLMDetector.detectLandMark(frame, null);
//                List<FaceLandmark> landMarks = faceLMDetector.convertResult(resultObj);//得到java类型结果  --出错
                //获取第一个点的坐标
//                PointF p = landMarks.get(0).getPositionF();

                json = resultObj.toString();
                Log.d("aaaa", json);


                JSONArray landmarks = null;
                try {
                    resultObj = new JSONObject("{\"landmark\":[{\"positionF\":{\"x\":91.918335,\"y\":311.54224},\"type\":-1},{\"positionF\":{\"x\":95.64966,\"y\":356.20996},\"type\":-1},{\"positionF\":{\"x\":103.22046,\"y\":400.76953},\"type\":-1},{\"positionF\":{\"x\":114.684814,\"y\":443.38232},\"type\":-1},{\"positionF\":{\"x\":130.25903,\"y\":483.3994},\"type\":-1},{\"positionF\":{\"x\":151.24097,\"y\":520.3882},\"type\":-1},{\"positionF\":{\"x\":176.87354,\"y\":553.0508},\"type\":-1},{\"positionF\":{\"x\":208.6709,\"y\":579.2241},\"type\":-1},{\"positionF\":{\"x\":250.52661,\"y\":587.44385},\"type\":-1},{\"positionF\":{\"x\":293.89648,\"y\":578.3589},\"type\":-1},{\"positionF\":{\"x\":329.3711,\"y\":553.0508},\"type\":-1},{\"positionF\":{\"x\":359.438,\"y\":520.8208},\"type\":-1},{\"positionF\":{\"x\":384.09717,\"y\":483.83203},\"type\":-1},{\"positionF\":{\"x\":401.18555,\"y\":442.08447},\"type\":-1},{\"positionF\":{\"x\":411.35205,\"y\":396.1189},\"type\":-1},{\"positionF\":{\"x\":417.625,\"y\":348.7473},\"type\":-1},{\"positionF\":{\"x\":420.65332,\"y\":300.83496},\"type\":-1},{\"positionF\":{\"x\":114.684814,\"y\":283.63843},\"type\":-1},{\"positionF\":{\"x\":131.88135,\"y\":263.1432},\"type\":-1},{\"positionF\":{\"x\":158.70361,\"y\":255.84277},\"type\":-1},{\"positionF\":{\"x\":187.79712,\"y\":257.14062},\"type\":-1},{\"positionF\":{\"x\":215.70093,\"y\":264.71143},\"type\":-1},{\"positionF\":{\"x\":259.71973,\"y\":261.14233},\"type\":-1},{\"positionF\":{\"x\":289.5703,\"y\":250.8136},\"type\":-1},{\"positionF\":{\"x\":321.8003,\"y\":247.2445},\"type\":-1},{\"positionF\":{\"x\":352.94873,\"y\":253.24707},\"type\":-1},{\"positionF\":{\"x\":375.66113,\"y\":272.76892},\"type\":-1},{\"positionF\":{\"x\":238.62964,\"y\":281.69165},\"type\":-1},{\"positionF\":{\"x\":238.19702,\"y\":305.05298},\"type\":-1},{\"positionF\":{\"x\":237.22363,\"y\":328.198},\"type\":-1},{\"positionF\":{\"x\":236.46655,\"y\":352.85718},\"type\":-1},{\"positionF\":{\"x\":211.48291,\"y\":383.89746},\"type\":-1},{\"positionF\":{\"x\":225.65112,\"y\":388.3318},\"type\":-1},{\"positionF\":{\"x\":240.57642,\"y\":390.60303},\"type\":-1},{\"positionF\":{\"x\":255.82617,\"y\":386.92578},\"type\":-1},{\"positionF\":{\"x\":270.75146,\"y\":381.62622},\"type\":-1},{\"positionF\":{\"x\":149.7268,\"y\":296.40063},\"type\":-1},{\"positionF\":{\"x\":165.08472,\"y\":282.82727},\"type\":-1},{\"positionF\":{\"x\":187.79712,\"y\":280.77234},\"type\":-1},{\"positionF\":{\"x\":206.94043,\"y\":293.26416},\"type\":-1},{\"positionF\":{\"x\":188.66235,\"y\":298.99634},\"type\":-1},{\"positionF\":{\"x\":166.16626,\"y\":301.9165},\"type\":-1},{\"positionF\":{\"x\":278.53857,\"y\":288.938},\"type\":-1},{\"positionF\":{\"x\":297.57373,\"y\":274.4994},\"type\":-1},{\"positionF\":{\"x\":321.15137,\"y\":275.14832},\"type\":-1},{\"positionF\":{\"x\":338.67236,\"y\":287.09937},\"type\":-1},{\"positionF\":{\"x\":321.36768,\"y\":294.02124},\"type\":-1},{\"positionF\":{\"x\":298.00635,\"y\":293.04785},\"type\":-1},{\"positionF\":{\"x\":201.3164,\"y\":467.8252},\"type\":-1},{\"positionF\":{\"x\":211.80737,\"y\":439.70508},\"type\":-1},{\"positionF\":{\"x\":228.89575,\"y\":425.64502},\"type\":-1},{\"positionF\":{\"x\":241.76611,\"y\":428.88965},\"type\":-1},{\"positionF\":{\"x\":255.17725,\"y\":424.34717},\"type\":-1},{\"positionF\":{\"x\":276.15918,\"y\":436.89307},\"type\":-1},{\"positionF\":{\"x\":293.03125,\"y\":462.8501},\"type\":-1},{\"positionF\":{\"x\":277.88965,\"y\":481.45264},\"type\":-1},{\"positionF\":{\"x\":259.2871,\"y\":490.9702},\"type\":-1},{\"positionF\":{\"x\":245.01074,\"y\":492.917},\"type\":-1},{\"positionF\":{\"x\":230.84253,\"y\":492.48438},\"type\":-1},{\"positionF\":{\"x\":213.97046,\"y\":484.48096},\"type\":-1},{\"positionF\":{\"x\":210.40137,\"y\":464.79688},\"type\":-1},{\"positionF\":{\"x\":229.86914,\"y\":444.03125},\"type\":-1},{\"positionF\":{\"x\":242.7395,\"y\":443.59863},\"type\":-1},{\"positionF\":{\"x\":255.82617,\"y\":442.5171},\"type\":-1},{\"positionF\":{\"x\":283.51367,\"y\":460.4707},\"type\":-1},{\"positionF\":{\"x\":257.34033,\"y\":465.87842},\"type\":-1},{\"positionF\":{\"x\":243.82104,\"y\":468.0415},\"type\":-1},{\"positionF\":{\"x\":230.62622,\"y\":466.95996},\"type\":-1}]}");

                    landmarks = resultObj.getJSONArray("landmark");
                    landmarks.getJSONObject(0).getJSONObject("positionF").get("x");
                    points = new float[68*2];
                    for (int i=0;i<68;i++) {
                        Log.d("aaaa", "bbbbbbb");

                        points[i*2] = (float) landmarks.getJSONObject(i).getJSONObject("positionF").get("x");
                        Log.d("aaaa", "ccccccc");

                        points[i*2+1] = (float) landmarks.getJSONObject(i).getJSONObject("positionF").get("y");
                    }
                    Log.d("aaaa", "ddddddddd");

                } catch (JSONException e) {
                e.printStackTrace();
            }

                Message obtain = Message.obtain();
//                handler.sendMessage(obtain);


            }
        }.start();

    }

    private void initView() {
        textView = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);
    }

/*

{
  "resultCode": 0,
  "landmark": "[{ "positionF":{ "x":135.95386, "y":472.45093}, "type":-1},{ "positionF":{ "x":141.7107, "y":539.7739}, "type":-1}]"
 */

    //    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nanana).copy(Bitmap.Config.ARGB_8888, true);



}
