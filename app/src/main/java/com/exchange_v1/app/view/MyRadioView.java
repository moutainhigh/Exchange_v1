package com.exchange_v1.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.app.R;


/**
 * 首页底部按钮,看情况也可以使用RadioButton
 * 
 */
public class MyRadioView extends RelativeLayout {
	private TextView text;
	private ImageView img;
	private int img0Res;
	private int img1Res;
	public boolean isCheck = false;
	private View redCircle;//红点点

	public MyRadioView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public MyRadioView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		View contentView = View.inflate(context, R.layout.view_my_radio, null);
		text = (TextView) contentView.findViewById(R.id.myRadio_txt);
		img = (ImageView) contentView.findViewById(R.id.myRadio_img);
		redCircle = contentView.findViewById(R.id.select_point);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRadioView);
		text.setText(getText(a, R.styleable.MyRadioView_text));
		text.setTextColor(getResources().getColor(R.color.msg_font_gray));
		text.setEnabled(false);
		img1Res = a.getResourceId(R.styleable.MyRadioView_img1, R.mipmap.trans);
		img0Res = a.getResourceId(R.styleable.MyRadioView_img0, R.mipmap.trans);
		img.setImageResource(img0Res);
		addView(contentView);
		a.recycle();
	}

	/**
	 * 是否显示红点
	 * @param isShow
     */
	public void showRedCircle(boolean isShow){
		if(isShow){
			redCircle.setVisibility(VISIBLE);
		}else
		{
			redCircle.setVisibility(INVISIBLE);
		}

	}

	private String getText(TypedArray a, int index) {
		String txt = a.getString(index);
		if (txt == null) {
			return "";
		}
		return txt;
	}

	/**
	 * 设置状态
	 * 
	 * @updateTime 2015-6-27,下午10:42:37
	 * @updateAuthor qw
	 * @param check
	 * @return
	 */
	public void setCheck(boolean check) {
		if (isCheck == check) {
			return;
		}
		isCheck = check;
		text.setEnabled(check);
		img.setImageResource(isCheck ? img1Res : img0Res);
		if (check) {
			text.setTextColor(getResources().getColor(R.color.common_assist_blue_color));
		} else {
			text.setTextColor(getResources().getColor(R.color.msg_font_gray));
		}

//		text.setTextColor(getResources().getColor(R.color.red_e040));
	}

	/**
	 * 
	 * 设置文本
	 * 
	 * @version 1.0
	 * @createTime 2015年9月10日,下午5:48:57
	 * @updateTime 2015年9月10日,下午5:48:57
	 * @createAuthor gushiyong
	 * @updateAuthor gushiyong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param value
	 */
	public void setText(String value) {
		text.setText(value);
	}

	/** 设置资源 */
	public void setImgRes(int img0Res, int img1Res) {
		this.img0Res = img0Res;
		this.img1Res = img1Res;
		img.setImageResource(isCheck ? img1Res : img0Res);
	}
}