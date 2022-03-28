package com.han56.utils;

import com.han56.entity.tcDataSource.TcOrderBookBean;
import com.han56.entity.tcDataSource.vo.TcOrderBookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/25 下午8:41
 */
@Component
@Slf4j
public class Utils {

    /*
    * 封装 asks 列表
    * */
    public synchronized  List<List<String>> setAsksList(TcOrderBookVO tcOrderBookVO){
        List<List<String>> res = new ArrayList<>();
        List<String> askOne = new ArrayList<>();
        askOne.add(tcOrderBookVO.getAskOne()); askOne.add(tcOrderBookVO.getAskOneVolumn());
        List<String> askTwo = new ArrayList<>();
        askTwo.add(tcOrderBookVO.getAskTwo()); askTwo.add(tcOrderBookVO.getAskTwoVolumn());
        List<String> askThree = new ArrayList<>();
        askThree.add(tcOrderBookVO.getAskThree()); askThree.add(tcOrderBookVO.getAskThreeVolumn());
        List<String> askFour = new ArrayList<>();
        askFour.add(tcOrderBookVO.getAskFour()); askFour.add(tcOrderBookVO.getAskFourVolumn());
        List<String> askFive = new ArrayList<>();
        askFive.add(tcOrderBookVO.getAskFive()); askFive.add(tcOrderBookVO.getAskFiveVolumn());
        res.add(askOne);res.add(askTwo);res.add(askThree);res.add(askFour);res.add(askFive);
        return  res;
    }


    /*
    * 封装 bids 列表
    * */
    public synchronized  List<List<String>> setBidsList(TcOrderBookVO tcOrderBookVO){
        List<List<String>> res = new ArrayList<>();
        List<String> bidOne = new ArrayList<>();
        bidOne.add(tcOrderBookVO.getBidOne()); bidOne.add(tcOrderBookVO.getBidOneVolumn());
        List<String> bidTwo = new ArrayList<>();
        bidTwo.add(tcOrderBookVO.getBidTwo()); bidTwo.add(tcOrderBookVO.getBidTwoVolumn());
        List<String> bidThree = new ArrayList<>();
        bidThree.add(tcOrderBookVO.getBidThree()); bidThree.add(tcOrderBookVO.getBidThreeVolumn());
        List<String> bidFour = new ArrayList<>();
        bidFour.add(tcOrderBookVO.getBidFour()); bidFour.add(tcOrderBookVO.getBidFourVolumn());
        List<String> bidFive = new ArrayList<>();
        bidFive.add(tcOrderBookVO.getBidFive()); bidFive.add(tcOrderBookVO.getBidFiveVolumn());
        res.add(bidOne);res.add(bidTwo);res.add(bidThree);res.add(bidFour);res.add(bidFive);
        return  res;
    }

    /*
    * 根据请求时间判断是否处于交易时间区域
    * */
    public Boolean dateIsValid(){
        //初始化返回值变量 交易中：true 停市中：false
        Boolean flag = true;

        //判断日期是否合法
        Date nowDate = new Date();
        Calendar nowCalendarDate = Calendar.getInstance();
        nowCalendarDate.setTime(nowDate);
        if(nowCalendarDate.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||nowCalendarDate.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            log.warn("当前为周末，交易所停市状态");
            return false;
        }

        //判断当前时间是否在交易状态中
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date nowTime = null;
        Date beginTime1 = null;
        Date beginTime2 = null;
        Date endTime1 = null;
        Date endTime2 = null;
        try{
            nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            beginTime1 = simpleDateFormat.parse("09:30");
            beginTime2 = simpleDateFormat.parse("13:30");
            endTime1 = simpleDateFormat.parse("11:00");
            endTime2 = simpleDateFormat.parse("15:00");
            //System.out.println("时间格式："+nowTime);
        }catch(Exception e){
            e.printStackTrace();
        }


        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(nowTime);

        Calendar beginTimeCalendar1 = Calendar.getInstance();
        beginTimeCalendar1.setTime(beginTime1);
        Calendar beginTimeCalendar2 = Calendar.getInstance();
        beginTimeCalendar2.setTime(beginTime2);

        Calendar endTimeCalendar1 = Calendar.getInstance();
        endTimeCalendar1.setTime(endTime1);
        Calendar endTimeCalendar2 = Calendar.getInstance();
        endTimeCalendar2.setTime(endTime2);

        if(nowCalendar.before(beginTimeCalendar1)||nowCalendar.after(endTimeCalendar2)){
            log.warn("当前时间处在9:30之前,15:00之后，交易所处于停市状态");
            return false;
        }else if(nowCalendar.before(beginTime2)&&nowCalendar.after(endTime1)){
            log.warn("当前时间处在13:30之前,11:00之后，交易所处于停市状态");
            return false;
        }
        return true;
    }

    /*
    * 将腾讯摆盘API获取到数据塞进VO层中，并返回JSON字符串
    * */
    public synchronized TcOrderBookVO setTcOrderBookData(String resDatas){
        //取每行数据中双引号之间的值
        //System.out.println("初始字符串长度"+str.length());
        if (resDatas.length()<400){
            log.warn("腾讯API摆盘返回数据长度过小，注意！");
            //返回错误JSON
        }
        String substring = resDatas.substring(resDatas.indexOf("\"") + 1, resDatas.lastIndexOf("\""));
        String[] splits = substring.split("~");
        //System.out.println("解析完成后splits长度:"+splits.length);
        /*
         * 进一步封装摆盘数据VO层
         * */
        return Utils.orderBookVO(splits);
    }

    /*
     * 封装摆盘数据VO层
     * */
    public synchronized static TcOrderBookVO orderBookVO(String[] strs){
        TcOrderBookVO tcOrderBookVO = new TcOrderBookVO();
        tcOrderBookVO.setTimestamp(System.currentTimeMillis());

        tcOrderBookVO.setBidOne(strs[9]);
        tcOrderBookVO.setBidOneVolumn(strs[10]);
        tcOrderBookVO.setAskOne(strs[19]);
        tcOrderBookVO.setAskOneVolumn(strs[20]);

        tcOrderBookVO.setBidTwo(strs[11]);
        tcOrderBookVO.setBidTwoVolumn(strs[12]);
        tcOrderBookVO.setAskTwo(strs[21]);
        tcOrderBookVO.setAskTwoVolumn(strs[22]);

        tcOrderBookVO.setBidThree(strs[13]);
        tcOrderBookVO.setBidThreeVolumn(strs[14]);
        tcOrderBookVO.setAskThree(strs[23]);
        tcOrderBookVO.setAskThreeVolumn(strs[24]);

        tcOrderBookVO.setBidFour(strs[15]);
        tcOrderBookVO.setBidFourVolumn(strs[16]);
        tcOrderBookVO.setAskFour(strs[25]);
        tcOrderBookVO.setAskFourVolumn(strs[26]);

        tcOrderBookVO.setBidFive(strs[17]);
        tcOrderBookVO.setBidFiveVolumn(strs[18]);
        tcOrderBookVO.setAskFive(strs[27]);
        tcOrderBookVO.setAskFiveVolumn(strs[28]);

        return tcOrderBookVO;
    }

}
