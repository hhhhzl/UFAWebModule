package com.han56.service.impl;

import com.alibaba.fastjson.JSON;
import com.han56.service.CommonKLineData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

/**
*@description 功能描述
*@author han56
*@create 2022/3/17 下午8:51
 *
*/
@Service
public class CommonKLineDataImpl implements CommonKLineData {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getDayKLine(String startDate,String endTime,String stockCode) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        String baseUrl = "https://web.ifzq.gtimg.cn/appstock/app/fqkline/get?param=";
        StringBuilder stringBuffer = new StringBuilder();
        //组装请求url
        stringBuffer.append(baseUrl).append(stockCode).append(",day,").append(startDate).append(",").append(endTime).append(",600,qfq");
        logger.info("组装后的url连接："+ stringBuffer);
        //try catch 包围请求部分
        try {
            Request request = new Request.Builder()
                    .url(stringBuffer.toString())
                    .method("GET",null)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()&&response.body()!=null){
                logger.info("请求日线成功");
                return response.body().string();
            }
        }catch (Exception e){
            logger.error("请求日线操作失败，请查看日志报错信息记录筛查问题："+e.getMessage());
        }
        //请求失败，返回请求失败状态码
        return errorHandle();
    }

    @Override
    public String getWeekKLine(String startDate,String endDate,String stockCode) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        String baseUrl = "https://web.ifzq.gtimg.cn/appstock/app/fqkline/get?param=";
        StringBuilder stringBuffer = new StringBuilder();
        //组装请求url
        stringBuffer.append(baseUrl).append(stockCode).append(",week,").append(startDate).append(",").append(endDate).append(",600,qfq");
        logger.info("组装后的url连接："+ stringBuffer);
        //try catch 包围请求部分
        try {
            Request request = new Request.Builder()
                    .url(stringBuffer.toString())
                    .method("GET",null)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()&&response.body()!=null){
                logger.info("请求周线成功");
                return response.body().string();
            }
        }catch (Exception e){
            logger.error("请求周线操作失败，请查看日志报错信息记录筛查问题："+e.getMessage());
        }
        //请求失败，返回请求失败状态码
        return errorHandle();
    }



    @Override
    public String getMonthKLine(String startDate,String endDate,String stockCode) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        String baseUrl = "https://web.ifzq.gtimg.cn/appstock/app/fqkline/get?param=";
        StringBuilder stringBuffer = new StringBuilder();
        //组装请求url
        stringBuffer.append(baseUrl).append(stockCode).append(",month,").append(startDate).append(",").append(endDate).append(",600,qfq");
        logger.info("组装后的url连接："+ stringBuffer);
        //try catch 包围请求部分
        try {
            Request request = new Request.Builder()
                    .url(stringBuffer.toString())
                    .method("GET",null)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()&&response.body()!=null){
                logger.info("请求月线成功");
                return response.body().string();
            }
        }catch (Exception e){
            logger.error("请求月线操作失败，请查看日志报错信息记录筛查问题："+e.getMessage());
        }
        //请求失败，返回请求失败状态码
        return errorHandle();
    }

    @Override
    public String getQuatKLine(String QuatDate) {
        return null;
    }

    @Override
    public String getYearKLine(String yearDate) {
        return null;
    }


    /*
    * 错误代码返回处理器
    * */
    private String errorHandle() {
        HashMap<String,String> errorMap = new HashMap<>();
        errorMap.put("code","0");
        errorMap.put("msg","请求失败");
        errorMap.put("success","false");
        errorMap.put("data",null);
        return JSON.toJSONString(errorMap);
    }
}
