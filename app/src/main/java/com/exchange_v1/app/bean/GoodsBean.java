//package com.exchange_v1.app.bean;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.text.TextUtils;
//
//import com.exchange_v1.app.base.TApplication;
//import com.exchange_v1.app.utils.CountUtil;
//import com.exchange_v1.app.utils.StringUtil;
//import com.exchange_v1.app.utils.ToastUtil;
//import com.exchange_v1.app.utils.Util;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.Serializable;
//
///**
// * 商品bean
// *
// * @author gushiyong
// * @Description TODO
// * @date 2015年8月24日
// * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司
// */
//public class GoodsBean extends BaseBean {
//    /**
//     * 变量描述
//     */
//    private static final long serialVersionUID = 6814125185718162921L;
//
//    public static final String TYPE_OIL_LIST_CARD_LITRE = "litre"; //储油卡
//    public static final String TYPE_OIL_LIST_CARD_AMOUNT = "amount";    //金额卡
//
//    /**
//     * 商品ID
//     */
//    private String id;
//    /**
//     * 商品 期数
//     */
//    private String period;
//    /**
//     * 商品图片
//     */
//    private String goodsImg;
//
//    /**
//     * 显示名称
//     */
//    private String name;
//
//    /**
//     * 商品名称
//     */
//    private String goodsName;
//    /**
//     * 商品价格
//     */
//    private String goodsPrice;
//    /**
//     * 商品数量
//     */
//    private String goodsNumber;
//    /**
//     * 商品备注
//     */
//    private String goodsNote;
//    /**
//     * 商品介绍
//     */
//    private String goodsIntroduce;
//    /**
//     * 商品最大数量
//     */
//    private String goodsMax;
//    /**
//     * 油品
//     */
//    private String oil;
//    /**
//     * 升
//     */
//    private String rise;
//    /**
//     * 城市
//     */
//    private String city;
//    /**
//     * 标题
//     */
//    private String title;
//    /**
//     * 商品类型
//     */
//    private String cardType;
//    /**
//     * 总付期限
//     */
//    private String cashTime;
//    /**
//     * 有效期
//     */
//    private String validity;
//    /**
//     * 每月限领量
//     */
//    private String limitAmount;
//    /**
//     * 产品ID
//     */
//    private String productId;
//    /**
//     * 库存数量
//     */
//    private String stockNum;
//
//    /**
//     * 购物车时间
//     */
//    private String time;
//    /**
//     * 购物车ID
//     */
//    private String obj_ident;
//    /**
//     * 商品ID
//     */
//    private String goods_id;
//    /**
//     * 是否可以
//     */
//    private String is_true;
//    /**
//     * 是否选中
//     */
//    private boolean is_check;
//    /**
//     * 油品
//     */
//    private String spec_oil;
//    /**
//     * 单位
//     */
//    private String spec_type;
//    /**
//     * 商品属性
//     */
//    private String attr;
//    private String member_ident;
//    /**
//     * 用户所选金额
//     */
//    private String cash;
//    /**
//     * 用户所选冻结期
//     */
//    private String frozen;
//    /**
//     * 用户所选金额
//     */
//    private String cashperiods;
//    /**
//     * 卡价值
//     */
//    private String card_price;
//    /**
//     * 有效期至
//     */
//    private String expired;
//    /**
//     * 每期可领取
//     */
//    private String cashnum;
//    /**
//     * 首页商品排序
//     */
//    private String order;
//    /**
//     * 地区
//     */
//    private String regionName;
//    private String desc;
//    /**
//     * 卡卷编号
//     */
//    private String card_bn;
//    /**
//     * 卡券额度
//     */
//    private String card_amount_info;
//    /**
//     * 油品显示
//     */
//    private String card_coupons;
//    /**
//     * 现金券
//     */
//    private int coupon;
//
//    /**
//     * 卡类型
//     */
//    private String type;
//
//    /**
//     * 背景图
//     */
//    private String bgimg;
//    /**
//     * 产品名
//     */
//    private String pname;
//    //5.6.1 首页产品列表名称
//    private String pn_name;
//
//    public String getIs_add_rate() {
//        return is_add_rate;
//    }
//
//    public void setIs_add_rate(String is_add_rate) {
//        this.is_add_rate = is_add_rate;
//    }
//
//    private String is_add_rate; //是否可加息
//
//    /**
//     * 库存
//     */
//    public int store;
//    public String litre_or_amount;
//    public int term;
//    public String termText;
//    public String termUnit;
//    //首页六张卡片图片地址
//    public String kaimg;
//
//    public String getKaimg() {
//        return kaimg;
//    }
//
//    public void setKaimg(String kaimg) {
//        this.kaimg = kaimg;
//    }
//
//    public String getBuy_num_max() {
//        return buy_num_max;
//    }
//
//    public void setBuy_num_max(String buy_num_max) {
//        this.buy_num_max = buy_num_max;
//    }
//
//    public String buy_num_max;
//    public Additional additional = new Additional();
//
//    public class Additional implements Serializable {
//        public String profit_rate;
//        public String profit_rate_label;
//        public String profit_give;
//        public String profit_extrarate;
//        public String profit_extrarate_label;
//        public String profit_unit;
//        public String label;
//        public String type;    //type   为litre时为 储油卡      为amount为金额卡
//        //		public List<String> keyword = new ArrayList<>();
//        public String keyword;
//        public String keyword_type;
//        public String unit;
//
//
//        public String profit_extrarate_unit;
//    }
//
//    public String getTermText() {
//        return termText;
//    }
//
//    public void setTermText(String termText) {
//        this.termText = termText;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLitre_or_amount() {
//        return litre_or_amount;
//    }
//
//    public void setLitre_or_amount(String litre_or_amount) {
//        this.litre_or_amount = litre_or_amount;
//    }
//
//    public int getTerm() {
//        return term;
//    }
//
//    public void setTerm(int term) {
//        this.term = term;
//    }
//
//    public String getTermUnit() {
//        return termUnit;
//    }
//
//    public void setTermUnit(String termUnit) {
//        this.termUnit = termUnit;
//    }
//
//    public int getStore() {
//        return store;
//    }
//
//    public void setStore(int store) {
//        this.store = store;
//    }
//
//    public int getCoupon() {
//        return coupon;
//    }
//
//    public void setCoupon(int coupon) {
//        this.coupon = coupon;
//    }
//
//    public String getCard_coupons() {
//        return card_coupons;
//    }
//
//    public void setCard_coupons(String card_coupons) {
//        this.card_coupons = card_coupons;
//    }
//
//    public String getCard_bn() {
//        return card_bn;
//    }
//
//    public void setCard_bn(String card_bn) {
//        this.card_bn = card_bn;
//    }
//
//    public String getCard_amount_info() {
//        return card_amount_info;
//    }
//
//    public void setCard_amount_info(String card_amount_info) {
//        this.card_amount_info = card_amount_info;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    public String getOrder() {
//        return order;
//    }
//
//    public void setOrder(String order) {
//        this.order = order;
//    }
//
//    public String getCard_price() {
//        return card_price;
//    }
//
//    public void setCard_price(String card_price) {
//        this.card_price = card_price;
//    }
//
//    public String getExpired() {
//        return expired;
//    }
//
//    public void setExpired(String expired) {
//        this.expired = expired;
//    }
//
//    public String getCashnum() {
//        return cashnum;
//    }
//
//    public String getRegionName() {
//        return regionName;
//    }
//
//    public void setRegionName(String regionName) {
//        this.regionName = regionName;
//    }
//
//    public void setCashnum(String cashnum) {
//        this.cashnum = cashnum;
//    }
//
//    public static long getSerialversionuid() {
//        return serialVersionUID;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public String getCash() {
//        return cash;
//    }
//
//    public void setCash(String cash) {
//        this.cash = cash;
//    }
//
//    public String getFrozen() {
//        return frozen;
//    }
//
//    public void setFrozen(String frozen) {
//        this.frozen = frozen;
//    }
//
//    public String getCashperiods() {
//        return cashperiods;
//    }
//
//    public void setCashperiods(String cashperiods) {
//        this.cashperiods = cashperiods;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getObj_ident() {
//        return obj_ident;
//    }
//
//    public void setObj_ident(String obj_ident) {
//        this.obj_ident = obj_ident;
//    }
//
//    public String getGoods_id() {
//        return goods_id;
//    }
//
//    public String getMember_ident() {
//        return member_ident;
//    }
//
//    public void setMember_ident(String member_ident) {
//        this.member_ident = member_ident;
//    }
//
//    public void setGoods_id(String goods_id) {
//        this.goods_id = goods_id;
//    }
//
//    public String getIs_true() {
//        return is_true;
//    }
//
//    public void setIs_true(String is_true) {
//        this.is_true = is_true;
//    }
//
//    public boolean getIs_check() {
//        return is_check;
//    }
//
//    public void setIs_check(boolean is_check) {
//        this.is_check = is_check;
//    }
//
//    public String getSpec_oil() {
//        return spec_oil;
//    }
//
//    public void setSpec_oil(String spec_oil) {
//        this.spec_oil = spec_oil;
//    }
//
//    public String getSpec_type() {
//        return spec_type;
//    }
//
//    public void setSpec_type(String spec_type) {
//        this.spec_type = spec_type;
//    }
//
//    public String getAttr() {
//        return attr;
//    }
//
//    public void setAttr(String attr) {
//        this.attr = attr;
//    }
//
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Additional getAdditional() {
//        return additional;
//    }
//
//    public void setAdditional(Additional additional) {
//        this.additional = additional;
//    }
//
//    public String getPn_name() {
//        return pn_name;
//    }
//
//    public void setPn_name(String pn_name) {
//        this.pn_name = pn_name;
//    }
//
//    @Override
//    protected void init(JSONObject jSon) throws JSONException {
//        /** objlist里action_solution数据字段 */
//        String action_solution = jSon.optString("action_solution_new");
//        JSONObject action_obj = null;
//
//        if (!TextUtils.isEmpty(action_solution)) {
//            try {
//                action_obj = new JSONObject(action_solution);
//            } catch (Exception e) {
//            }
//        }
//
//
//        setId(jSon.optString("id"));
//        setOrder(jSon.optString("order"));
//        setGoodsImg(jSon.optString("img"));
//        String goodsname = jSon.optString("product_type_name");
//        if (TextUtils.isEmpty(goodsname)) {
//            setGoodsName(jSon.optString("name"));
//        } else {
//            setGoodsName(goodsname);
//        }
//        setGoodsNote(jSon.optString("intro"));
//        setGoodsIntroduce(jSon.optString("intro"));
//        setTitle(jSon.optString("title"));
//        setOil(jSon.optString("oil_name"));
//        String product_name = jSon.optString("product_name");
//        if (TextUtils.isEmpty(product_name)) {
//            setCardType(jSon.optString("card_type"));
//        } else {
//            setCardType(product_name);
//        }
//        setName(jSon.optString("name"));
//        setCard_bn(jSon.optString("card_bn"));
//        setObj_ident(jSon.optString("obj_ident"));
//        setSpec_oil(jSon.optString("spec_oil"));
//        setSpec_type(jSon.optString("spec_type"));
//        String price = jSon.optString("price");
//        if (TextUtils.isEmpty(price)) {
//            price = jSon.optString("amount");
//        }
//        setCard_amount_info(jSon.optString("card_amount_info"));
//
//        card_coupons = jSon.optString("card_coupons");
//        String cash_time = jSon.optString("cash_time");
//        if (TextUtils.isEmpty(cash_time)) {
//            cash_time = jSon.optString("cash_deadline");
//        }
//        String validity = jSon.optString("validity");
//        if (TextUtils.isEmpty(validity)) {
//            validity = jSon.optString("expiry_month");
//        }
//        String limit_amount = jSon.optString("limit_amount");
//        if (TextUtils.isEmpty(limit_amount)) {
//            limit_amount = jSon.optString("cash_limit_init");
//        }
//        setGoodsPrice(price);
//        setCashTime(cash_time);
//        setValidity(validity);
//        setLimitAmount(limit_amount);
//        setAttr(jSon.optString("attr"));
//        setGoods_id(jSon.optString("goods_id"));
//        setTime(jSon.optString("time"));
//        setProductId(jSon.optString("product_id"));
//        setTime(jSon.optString("time"));
//        setGoodsNumber(jSon.optString("quantity"));
//        setGoodsMax(jSon.optString("goods_max"));
//        setIs_true(jSon.optString("is_true"));
//        setMember_ident(jSon.optString("member_ident"));
//        setCashperiods(jSon.optString("cashperiods"));
//        setRegionName(jSon.optString("region_name"));
//        setExpired(jSon.optString("expired"));
//        setCashnum(jSon.optString("cashnum"));
//        setDesc(jSon.optString("desc"));
//        setCoupon(jSon.optInt("coupon"));
//        setType(jSon.optString("type"));
//        setIs_add_rate(jSon.optString("is_add_rate"));
//        setBgimg(jSon.optString("bgimg"));
//        setPname(jSon.optString("pname"));
//        setPn_name(jSon.optString("pn_name"));
//        setStore(jSon.optInt("store"));
//        setTerm(jSon.optInt("term"));
//        setTermUnit(jSon.optString("termUnit"));
//        setKaimg(jSon.optString("kaimg"));
//        setBuy_num_max(jSon.optString("buy_num_max"));
//        setTermText(jSon.optString("termText"));
//        setLitre_or_amount(jSon.optString("litre_or_amount"));
//        setPeriod(jSon.optString("period"));//期数
//
//        String add = jSon.optString("additional");
//        if (!TextUtils.isEmpty(add)) {
//            JSONObject jData = new JSONObject(add);
//            additional = new Additional();
//            additional.profit_rate = jData.optString("profit_rate");
//            additional.profit_rate_label = jData.optString("profit_rate_label");
//            additional.label = jData.optString("label");
//            additional.profit_extrarate_label = jData.optString("profit_extrarate_label");
//            additional.type = jData.optString("type");
//            additional.unit = jData.optString("unit");
//            additional.profit_give = jData.optString("profit_give");
//            additional.profit_extrarate = jData.optString("profit_extrarate");
//            additional.profit_unit = jData.optString("profit_unit");
//            additional.keyword = jData.optString("keyword");
//            additional.keyword_type = jData.optString("keyword_type");
//            additional.profit_extrarate_unit = jData.optString("profit_extrarate_unit");
//
//        }
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getPeriod() {
//        return period;
//    }
//
//    public void setPeriod(String period) {
//        this.period = period;
//    }
//
//    public String getGoodsImg() {
//        return goodsImg;
//    }
//
//    public String getProductId() {
//        return productId;
//    }
//
//    public void setProductId(String productId) {
//        this.productId = productId;
//    }
//
//    public String getStockNum() {
//        return stockNum;
//    }
//
//    public void setStockNum(String stockNum) {
//        this.stockNum = stockNum;
//    }
//
//    public void setGoodsImg(String goodsImg) {
//        this.goodsImg = goodsImg;
//    }
//
//    public String getGoodsName() {
//        return goodsName;
//    }
//
//    public void setGoodsName(String goodsName) {
//        this.goodsName = goodsName;
//    }
//
//    public String getGoodsPrice() {
//        return goodsPrice;
//    }
//
//    public void setGoodsPrice(String goodsPrice) {
//        this.goodsPrice = goodsPrice;
//    }
//
//    public String getGoodsNumber() {
//        return goodsNumber;
//    }
//
//    public void setGoodsNumber(String goodsNumber) {
//        this.goodsNumber = goodsNumber;
//    }
//
//    public String getGoodsNote() {
//        return goodsNote;
//    }
//
//    public void setGoodsNote(String goodsNote) {
//        this.goodsNote = goodsNote;
//    }
//
//    public String getGoodsMax() {
//        return goodsMax;
//    }
//
//    public void setGoodsMax(String goodsMax) {
//        this.goodsMax = goodsMax;
//    }
//
//    public String getGoodsIntroduce() {
//        return goodsIntroduce;
//    }
//
//    public void setGoodsIntroduce(String goodsIntroduce) {
//        this.goodsIntroduce = goodsIntroduce;
//    }
//
//    public String getOil() {
//        return oil;
//    }
//
//    public void setOil(String oil) {
//        this.oil = oil;
//    }
//
//    public String getRise() {
//        return rise;
//    }
//
//    public void setRise(String rise) {
//        this.rise = rise;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getCardType() {
//        return cardType;
//    }
//
//    public void setCardType(String cardType) {
//        this.cardType = cardType;
//    }
//
//    public String getCashTime() {
//        return cashTime;
//    }
//
//    public void setCashTime(String cashTime) {
//        this.cashTime = cashTime;
//    }
//
//    public String getValidity() {
//        return validity;
//    }
//
//    public void setValidity(String validity) {
//        this.validity = validity;
//    }
//
//    public String getLimitAmount() {
//        return limitAmount;
//    }
//
//    public void setLimitAmount(String limitAmount) {
//        this.limitAmount = limitAmount;
//    }
//
//    public String getBgimg() {
//        return bgimg;
//    }
//
//    public void setBgimg(String bgimg) {
//        this.bgimg = bgimg;
//    }
//
//    public String getPname() {
//        return pname;
//    }
//
//    public void setPname(String pname) {
//        this.pname = pname;
//    }
//
//    public static final int CARD_TYPE_REGULAR = 0x88;//定期  1,2,3年卡
//    public static final int CARD_TYPE_CURRENT = 0x89;//活期  天天加油卡
//    public static final int CARD_TYPE_NORMAL = 0x100;//普通
//    public static final int CARD_TYPE_REFUEL = 0x101;//全国加油 卡充值
//    public static final int CARD_TYPE_GUANGHUI_REFUEL = 0x102;//光汇加油
//    public static final int CARD_TYPE_EXPER = 0x103; //大众储油体验卡
//    public static final int CARD_TYPE_TREM = 0x105; //大众储油体验卡(新20170606)
//    public static final int CARD_TYPE_NATION = 0x106; //全国加油卡(新20170606)
//    public static final int CARD_LITRE_MARKUP = 0x107; //储油涨 3.3.0
//    public static final int CARD_LITRE_TERM_EXPER = 0x108; //储油新手体验卡  3.3.0
//    public static final int CARD_LITRE_MONTH = 0x109; //储油月月升  3.3.0
//    public static final int CARD_LITRE_COPY = 0x110; //汇油宝  3.6.0
//    public static final int CARD_LITRE_NAMOL = 0x111; //大众油品消费卡
//    public static final int CARD_LITRE_NATION = 0x112; //521全国加油卡
//    public static final int CARD_LITRE_EXPER = 0x113; //大众储油卡
//    public static final int CARD_LITRE_TREM = 0x114; //大众储油体验卡-新手卡
//    public static final int CARD_LITRE_WEBANK = 0x115; //微众加油卡
//    public static final int CARD_LITRE_CLOUD = 0x116; //云油加油卡
//    public static final int CARD_LITRE_PRESERVATION = 0x117; //光汇保值卡
//    public static final int CARD_FIXED_AMOUNT_EXPER = 0x118; //新汇油宝类型新手体验卡(2018V2)
//    public static final int CARD_FIXED_AMOUNT = 0x119; //新汇油宝(2018V2)
//    public static final int AMOUNT_AUTO_2019V1 = 0x120; //光汇直充卡
//    public static final int AMOUNT_AUTO_2019V2 = 0x121; //光汇分期卡
//
//    /**
//     * 是否储蓄卡
//     *
//     * @return
//     */
//    public static int getCardType(String cardType) {
//        if ("fixed_amount_current".equals(cardType)) {
//            //活期
//            return CARD_TYPE_CURRENT;
//        } else if ("fixed_amount_term_1".equals(cardType) || "fixed_amount_term_2".equals(cardType) || "fixed_amount_term_3".equals(cardType)) {
//            //定期
//            return CARD_TYPE_REGULAR;
//        } else if ("amount_auto_refuel".equals(cardType)) {    //全国加油
//            return CARD_TYPE_REFUEL;
//        } else if ("amount_refuel".equals(cardType)) {            //光汇加油
//            return CARD_TYPE_GUANGHUI_REFUEL;
//        } else if ("fixed_litre_exper".equals(cardType)) {        //大众储油体验卡
//            return CARD_TYPE_EXPER;
//        } else if ("fixed_litre_term".equals(cardType)) {        //大众储油体验卡(新20170606)
//            return CARD_TYPE_TREM;
//        } else if ("amount_refuel_nation".equals(cardType)) {        //全国加油卡(新20170606)
//            return CARD_TYPE_NATION;
//        } else if ("fixed_litre_markup".equals(cardType)) {        //储油涨 3.3.0
//            return CARD_LITRE_MARKUP;
//        } else if ("fixed_litre_term_exper".equals(cardType)) {        //储油新手体验卡  3.3.0
//            return CARD_LITRE_TERM_EXPER;
//        } else if ("fixed_litre_month".equals(cardType)) {        //储油月月升  3.3.0
//            return CARD_LITRE_MONTH;
//        } else if ("fixed_amount_copy".equals(cardType)) {        //汇油宝  3.6.0
//            return CARD_LITRE_COPY;
//        } else if ("fixed_amount".equals(cardType)) {        //大众油品消费卡
//            return CARD_LITRE_NAMOL;
//        } else if ("amount_refuel_nation_v1".equals(cardType)) {        //521全国加油卡
//            return CARD_LITRE_NATION;
//        } else if ("fixed_litre_up_v1".equals(cardType)) {        //521大众储油卡
//            return CARD_LITRE_EXPER;
//        } else if ("fixed_litre_exper_v1".equals(cardType)) {        //521大众储油体验卡
//            return CARD_LITRE_TREM;
//        } else if ("amount_refuel_webank".equals(cardType)) {        //560微众加油卡
//            return CARD_LITRE_WEBANK;
//        } else if ("amount_refuel_cloud".equals(cardType)) {        //云油加油卡
//            return CARD_LITRE_CLOUD;
//        } else if ("fixed_litre_down_v1".equals(cardType)) {        //光汇保值卡
//            return CARD_LITRE_PRESERVATION;
//        } else if ("fixed_amount_exper_v2".equals(cardType)) {        //新汇油宝类型新手体验卡(2018V2)
//            return CARD_FIXED_AMOUNT_EXPER;
//        } else if ("fixed_amount_v2".equals(cardType)) {
//            return CARD_FIXED_AMOUNT;
//        } else if ("amount_auto_2019v1".equals(cardType)) {
//            return AMOUNT_AUTO_2019V1;
//        } else if ("amount_auto_2019v2".equals(cardType)) {
//            return AMOUNT_AUTO_2019V2;
//        } else {
//            //普通
//            return CARD_TYPE_NORMAL;
//        }
//    }
//
//    public static void goToProductDetail(Context context, RedirectInfoBean redirectInfoBean) {
//        if (redirectInfoBean == null) {
//            return;
//        }
//        goToProductDetail(context, redirectInfoBean.getProductId(), redirectInfoBean.getProductName(), redirectInfoBean.getProductType(), redirectInfoBean.getPeriod());
//    }
//
//    /**
//     * 跳转商品详情页
//     *
//     * @param mContext
//     * @param productId     产品id
//     * @param productName   产品名称
//     * @param productType   产品类型 type_bn
//     * @param productPeriod 产品期数
//     */
//    public static void goToProductDetail(Context mContext, String productId, String productName, String productType, String productPeriod) {
//        goToProductDetail(mContext, productId, productName, productType, productPeriod, false);
//    }
//
//    /**
//     * 跳转商品详情页
//     *
//     * @param mContext
//     * @param productId     产品id
//     * @param productName   产品名称
//     * @param productType   产品类型 type_bn
//     * @param productPeriod 产品期数
//     */
//    public static void goToProductDetail(Context mContext, String productId, String productName, String productType, String productPeriod, boolean isJPush) {
//        if (StringUtil.isEmpty(productType)) {
//            return;
//        }
//        GoodsBean bean = new GoodsBean();
//        bean.setId(productId);
//        bean.setName(productName);
//        bean.setGoodsName(productName);
//        bean.setType(productType);
//        bean.setPeriod(productPeriod);//增加期数
//        goToProductDetail(mContext, bean, isJPush);
//    }
//
//    /**
//     * 跳转商品详情页
//     *
//     * @param mContext
//     * @param goodsBean
//     */
//    public static void goToProductDetail(Context mContext, GoodsBean goodsBean) {
//        goToProductDetail(mContext, goodsBean, false);
//    }
//
//    public static void goToProductDetail(Context mContext, GoodsBean goodsBean, boolean isJPush) {
//        if (goodsBean == null) {
//            return;
//        }
//        String productType = goodsBean.getType();
//        String productId = goodsBean.getId();
//        String productName = goodsBean.getName();
//        if (StringUtil.isEmpty(productType)) {
//            return;
//        }
//        if (GoodsBean.CARD_TYPE_REFUEL == GoodsBean.getCardType(productType)) {//全国加油 卡冲值
//            FuelCardRechargeActivity.openActivity((Activity) mContext);
//        } else {
//            Bundle bundle = new Bundle();
//            if (Util.isEmpty(productId)) {
//                ToastUtil.showToast(mContext, mContext.getString(R.string.toast_not_goods));
//            } else {
//                CountUtil.sendDataForStatistics(mContext, CountIdUtil.PXIANGQING);
//                bundle.putSerializable(FieldConfig.intent_bean, goodsBean);
//                bundle.putString(mContext.getString(R.string.intent_key_type), TApplication.oilMode == 0 ? "cloud" : "public");
//                if (GoodsBean.CARD_TYPE_TREM == GoodsBean.getCardType(productType)) {//大众储油体验卡（新）
//                    ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                } else {
//                    if (GoodsBean.CARD_TYPE_EXPER == GoodsBean.getCardType(productType)) {//大众储油体验卡
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_TYPE_NATION == GoodsBean.getCardType(productType)) {//全国加油卡新
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_TYPE_TREM == GoodsBean.getCardType(productType)) {//储油卡新
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_LITRE_MARKUP == GoodsBean.getCardType(productType)) {//储油涨 3.3.0
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_LITRE_TERM_EXPER == GoodsBean.getCardType(productType)) {//储油新手体验卡  3.3.0
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_LITRE_MONTH == GoodsBean.getCardType(productType)) {//储油月月升  3.3.0
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_LITRE_COPY == GoodsBean.getCardType(productType)) {//汇油宝  3.6.0
//                        //                        CollectOrderDetailActivity.openActivity(mContext, goodsBean);
//                        //友盟、百度统计埋点
//                        CountUtil.sendDataForStatistics(mContext, CountIdUtil.Pro_ + productType + "_" + goodsBean.getPeriod());
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_LITRE_TREM == GoodsBean.getCardType(productType)//新手体验卡
//                            || GoodsBean.CARD_LITRE_EXPER == GoodsBean.getCardType(productType)//光汇储油卡
//                            || GoodsBean.CARD_LITRE_PRESERVATION == GoodsBean.getCardType(productType)) {//光汇保值卡
//                        CountUtil.sendDataForStatistics(mContext, CountIdUtil.Pro_ + productType + "_" + goodsBean.getPeriod());
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_LITRE_NATION == GoodsBean.getCardType(productType)) {//光汇加油卡
//                        //友盟、百度统计埋点
//                        CountUtil.sendDataForStatistics(mContext, CountIdUtil.Pro_ + productType + "_" + goodsBean.getPeriod());
//                        ProductDetailWeBankOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    } else if (GoodsBean.CARD_LITRE_WEBANK == GoodsBean.getCardType(productType)//云油加油卡（升数）
//                            || GoodsBean.CARD_LITRE_CLOUD == GoodsBean.getCardType(productType)) {//云油加油卡（金额）
//                        //友盟、百度统计埋点
//                        CountUtil.sendDataForStatistics(mContext, CountIdUtil.Pro_ + productType + "_" + goodsBean.getPeriod());
//                        ProductDetailWeBankOilCardActivity.openActivity(mContext, goodsBean);
//                    } else if (GoodsBean.AMOUNT_AUTO_2019V1 == GoodsBean.getCardType(productType)
//                            || GoodsBean.AMOUNT_AUTO_2019V2 == GoodsBean.getCardType(productType)) {
//                        CountUtil.sendDataForStatistics(mContext, CountIdUtil.Pro_ + productType + "_" + goodsBean.getPeriod());
//                        BwoilCardBuyConfirmActivity.openActivity(mContext, goodsBean,isJPush);
//                    } else {
//                        ProductDetailBrightOilCardActivity.openActivity(mContext, goodsBean, isJPush);
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 我的订单，再次购买
//     * 包含了 521 三种新产品和微众卡
//     */
//    public static boolean checkOrderCardType(String typeBn) {
//        if (GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_GUANGHUI_REFUEL//光汇加油
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_REFUEL//全国加油
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_EXPER//大众储油体验卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_NATION//3.1.0 全国加油卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_COPY//3.6.0 汇油宝
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_NAMOL//大众油品消费卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_NORMAL
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_NATION
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_EXPER
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_TREM//普通
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_CLOUD//5.6.6云油卡判断
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_PRESERVATION//5.7.0光汇保值卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_WEBANK
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_FIXED_AMOUNT//新汇油宝
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_FIXED_AMOUNT_EXPER//新汇油宝新手卡类型
//                || GoodsBean.getCardType(typeBn) == GoodsBean.AMOUNT_AUTO_2019V1//直充卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.AMOUNT_AUTO_2019V2//分期卡
//                ) {//微众卡
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 进入  我的加油卡详情页  的油卡卡类型
//     * 不包含521三种 新油卡
//     * 5.7.3 增加新汇油宝类型
//     */
//    public static boolean checkCardType(String typeBn) {
//        if (GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_GUANGHUI_REFUEL//光汇加油
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_REFUEL//全国加油
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_EXPER//大众储油体验卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_TYPE_NATION//3.1.0 全国加油卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_COPY//3.6.0 汇油宝
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_NAMOL//大众油品消费卡
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_TREM //普通
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_FIXED_AMOUNT //新汇油宝
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_FIXED_AMOUNT_EXPER) {//新手体验卡
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 5.2.1版本
//     * 判断是否为三种新产品的油卡卡类型,5.6.0新增微众加油卡
//     * 5.6.6增加云油加油卡
//     */
//    public static boolean check521CardType(String typeBn) {
//        if (GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_NATION
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_EXPER
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_TREM
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_WEBANK
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_CLOUD
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_PRESERVATION//5.7.0光汇保值卡
//                ) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否 在领取时,需要传升数的卡类型
//     */
//    public static boolean checkOilCardIsCashPamars(String typeBn) {
//        if (GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_NATION
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_EXPER
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_TREM
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_WEBANK
//                || GoodsBean.getCardType(typeBn) == GoodsBean.CARD_LITRE_PRESERVATION//5.7.0光汇保值卡
//                ) {
//            return true;
//        }
//        return false;
//    }
//
//}
