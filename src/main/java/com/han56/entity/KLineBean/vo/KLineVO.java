package com.han56.entity.KLineBean.vo;

import com.han56.entity.KLineBean.KLineResponseBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/22 下午3:07
 */
public class KLineVO implements Serializable {

    private String code;

    private String msg;

    private List<KLineResponseBean> data;

    public KLineVO(String code, String msg, List<KLineResponseBean> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<KLineResponseBean> getData() {
        return data;
    }

    public void setData(List<KLineResponseBean> data) {
        this.data = data;
    }
}
