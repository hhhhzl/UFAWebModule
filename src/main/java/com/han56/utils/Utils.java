package com.han56.utils;

import com.han56.entity.tcDataSource.TcOrderBookBean;
import com.han56.entity.tcDataSource.vo.TcOrderBookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

}
