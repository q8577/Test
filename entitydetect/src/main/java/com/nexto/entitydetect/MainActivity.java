package com.nexto.entitydetect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huawei.hiai.nlu.model.ResponseResult;//接口返回的结果类
import com.huawei.hiai.nlu.sdk.NLUAPIService;//接口服务类
import com.huawei.hiai.nlu.sdk.NLUConstants;//接口常量类
import com.huawei.hiai.nlu.sdk.OnResultListener;//异步函数，执行成功的回调结果类

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    private static HashMap entityNameMap = new HashMap<>();

    static
    {
        entityNameMap.put("time", "时间");
        entityNameMap.put("location", "地点");
        entityNameMap.put("name", "人名");
        entityNameMap.put("phoneNum", "电话号码");
        entityNameMap.put("email", "邮箱");
        entityNameMap.put("url", "url");
        entityNameMap.put("movie", "电影");
        entityNameMap.put("tv", "电视剧");
        entityNameMap.put("varietyshow", "综艺");
        entityNameMap.put("anime", "动漫");
        entityNameMap.put("league", "联赛");
        entityNameMap.put("team", "球队");
        entityNameMap.put("music", "单曲");
        entityNameMap.put("musicAlbum", "专辑");
        entityNameMap.put("singer", "歌手");
        entityNameMap.put("trainNo", "火车车次");
        entityNameMap.put("flightNo", "航班号");
        entityNameMap.put("expressNo", "快递单号");
        entityNameMap.put("idNo", "证件号");
        entityNameMap.put("verificationCode", "验证码");
        entityNameMap.put("app", "手机应用");
        entityNameMap.put("carNo", "车牌号");
        entityNameMap.put("bankCardNo", "银行卡号");
        entityNameMap.put("book", "图书");
        entityNameMap.put("cate", "菜名");
        entityNameMap.put("famousBrand", "名牌");
        entityNameMap.put("stockName", "股票名");
        entityNameMap.put("stockCode", "股票代码");
        entityNameMap.put("fundName", "基金名");
        entityNameMap.put("fundCode", "基金代码");
    }
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        NLUAPIService.getInstance().init(MainActivity.this , new OnResultListener<Integer>(){

            @Override
            public void onResult(Integer result)
            {
                // 初始化成功回调，在服务出初始化成功调用该函数
            }
        },true);

    }

    public void pase(View view) {
        String requestJson = "{text:'我要看电影魔兽',module:'movie'}";
//        ResponseResult respResult = NLUAPIService.getInstance().getEntity(requestJson, NLUConstants.REQUEST_TYPE_LOCAL);
//        if (null != respResult)
//        {
            //获取接口返回结果，参考接口文档返回使用
//            String result = respResult.getJsonRes();
            String result = "{\"code\":0,\"message\":\"成功\",\"entity\":{\"time\":[{\"timestamp\":1456559721232,\"oriText\":\"明天\",\"section\":\"D\",\"charOffset\":0},{\"timestamp\":1456559721232,\"oriText\":\"晚上\",\"section\":\"E\",\"charOffset\":10}],\"movie\":[{\"name\":\"机器之血\",\"charOffset\":5}]}}";
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject entity=jsonObject.getJSONObject("entity");
                Iterator<String> entityNames = entity.keys();
                StringBuffer buffer = new StringBuffer();
                while (entityNames.hasNext()) {
                    String name = entityNames.next();
                    buffer.append(entityNameMap.get(name)+":");
                    JSONArray jsonArray = entity.getJSONArray(name);
                    for (int i=0;i<jsonArray.length();i++) {
                       buffer.append(jsonArray.getJSONObject(i).getString("oriText"));
                       if (i == jsonArray.length() - 1) {
                           buffer.append(".\n");
                       } else {
                           buffer.append(",");
                       }
                    }
                }
                result = buffer.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            textView.setText(result);
            Log.d("aaa", result);

//        }
//        Module ----可选参数 ，不填的时候所有实体都分析


    }
}
