package com.m.rabbit.ashop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import android.R.integer;

/**
 * 推荐model
 * @author zengxiaotao
 */
public class Goods implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8058796514341602342L;
	/**
     * 资源id
     */
    public int res;

    /**
     * 名字
     */
    public Goods() {
    }

    public Goods(int res, String name) {
        super();
        this.res = res;
        this.vName = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    
    public String gid;// "f864b698725647e8b5353aa56de622a5",
    public String gName;// "电饭锅",
    public String gContent;//低耗能电饭锅
    public double originalPrice;//380,
    public double currentPrice;// 280,
    public double discount;//0.74,
    public String type;//"00000001",
    public String typeName;//"妈咪宝贝",
    public String bargainers;// 109,
    public String cDate;//2014-06-01 12:32:33",
    public String sDate;//2014-06-18 00:00:00",
    public String createDate;
    public int salesCount;
    public int goodsCount;//100,
    public String imgName;//电饭锅",
    public String imgContent;//"精品",
    public String vName;// "猫",
    public String vPath;//"http://imocho.xicp.net/video/goods/mao.mov"

	public String gNo;
	public String attribute;//商品属性
	public String vContent;
	
	//trolley
    public double dj;//	商品单价
    public double spzj;//商品总价
    public String shopId;// "8b33b4ba8b0b44acad81e71bf9e3ac35",
	public String lxbh;//商品属性编号
	public String lxbhName;//订单中的商品属性
	public int ddsl;
	
	public String wlbh;//	物流编号	String	
	public String wlgsmcbh;//	物流公司名称编号	String	
	public String wlgsmc;//	物流公司名称	String	
//	public ArrayList<GoodsAttr> attributeList;
	
	
	public double sxf;//	手续费	double	
	public double kdf;//	快递费	double	
	
	/** 是否限购（0不限购，1限购） */
	public int sfxg;
	/**
	 * 限购数量
	 */
	public int xgsl;
	/** 是否允许开发票（0允许，1不允许） */
	public int sfkfp;
	/** 规格类型（0单规格，1多规格） */
	public int gglx;

	public String qrcodeImg;
	
	public ArrayList<GoodsImg> goodsImgList;
	public ArrayList<GoodsDetailImg> goodsDetailImgList;
	public ArrayList<GoodsVideo> videoList ;
	public ArrayList<GoodsAttrNew> ggList ;
	public GoodsAttrNew checkAttr ;
	
	@Override
	public String toString() {
		return "Goods [res=" + res + ", pid=" + gid
				+ ", pName=" + gName + ", pContent=" + gContent
				+ ", originalPrice=" + originalPrice + ", currentPrice="
				+ currentPrice + ", discount=" + discount + ", type=" + type
				+ ", bargainers=" + bargainers + ", cDate=" + cDate
				+ ", sDate=" + sDate + ", goodsCount=" + goodsCount
				+ ", imgName=" + imgName + "]";
	}
	
	public boolean isChecked;
    
	public String getImgFirst(){
		if (goodsImgList != null && goodsImgList.size() > 0 ) {
			return goodsImgList.get(0).smallImg;
		}
		return null;
	}
	
	public String getVideoFirst(){
		if (videoList != null && videoList.size() > 0 ) {
			return videoList.get(0).vPath;
		}
		return null;
	}
	
	public String getDetailImgFirst(){
		if (goodsDetailImgList != null && goodsDetailImgList.size() > 0 ) {
			return goodsDetailImgList.get(0).bigImg;
		}
		return null;
	}
	
	/**
	 * 查询购物车和订单使用
	 * @return
	 */
	public String getCheckAttrFirst(){
		if (ggList != null && ggList.size() > 0 ) {
			if (ggList.get(0) != null) {
				return ggList.get(0).ggmc;
			}
		}
		return null;
	}
	
	/**
	 * 查询是否限购
	 * @return
	 */
	public boolean getSfxg(){
		return sfxg == 1;
	}
	
	/**
	 * 是否允许开发票
	 */
	public boolean getSfkfp(){
		return sfkfp == 0;
	}

	public int getCollectCount() {
		return new Random().nextInt(100)+1;
	}
	
	public String getQrCodeImg(){
		return qrcodeImg;
	}
	
	@Override
	public boolean equals(Object o) {
		return gid != null && gid.equals(((Goods)o).gid);
	}
}
