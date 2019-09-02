package com.exchange_v1.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.utils.DisplayUtil;
import com.exchange_v1.app.utils.TVUtils;
import com.exchange_v1.app.utils.ViewUtils;


/**
 * 标题栏控件.
 * 
 *
 */
public class TitleView extends RelativeLayout {

	/** 上下文环境 */
	private Context context;
	/** 父视图 */
	private View view_Parent;

	/** 左边按钮 */
	private View view_LeftBtn;

	/** 右边按钮 */
	private View view_RightBtn;
	/** 右边按钮 (右边开始算，第二个按钮) */
	private View view_RightBtn_New;

	/** 左边按钮图标 */
	private ImageView img_Left;
	private ImageView img_Left2;
	/** 右边按钮图标 */
	private ImageView img_Right;
	/** 右边按钮图标(右边开始算，第二个按钮) */
	private ImageView img_right_new;
	/** 中间图片 */
	private ImageView img_title;

	/** 左边按钮文本 */
	private TextView txt_Left;
	/** 右边按钮文本 */
	private TextView txt_Right;
	/** 标题 */
	private TextView txt_Title;
	/** 标题2 */
	private TextView txt_Title2;

	/**
	 * 标题右面的
	 * 上面是图标，下面是文本
	 */
	private LinearLayout view_rightTvAndIvLL;
	/**
	 * 标题右面的
	 * 上面是图标
	 */
	private ImageView view_rightIv;
	/**
	 * 标题右面的
	 * 下面是文本
	 */
	private TextView view_rightTv;
	private RelativeLayout mView_rl;
    private View mLine_bottom;

    public TitleView(Context context) {
		super(context);
		init(context);
	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	/**
	 * 初始化.
	 * 
	 * @version 1.0
	 * @createTime 2013-10-23,上午10:57:14
	 * @updateTime 2013-10-23,上午10:57:14
	 * @createAuthor CodeApe
	 * @updateAuthor wjx
	 * @updateInfo (调整了view_title的xml布局文件的背景，高度，按钮位置等)
	 * 
	 */
	@SuppressLint("InflateParams")
	private void init(Context context) {
		if (context==null) {
			return;
		}
		this.context = context;

		view_Parent = LayoutInflater.from(this.context).inflate(
				R.layout.view_title, null);

		view_LeftBtn = view_Parent.findViewById(R.id.view_left);
		view_RightBtn = view_Parent.findViewById(R.id.view_right);
		view_RightBtn_New = view_Parent.findViewById(R.id.view_right_new);
		mView_rl = (RelativeLayout) view_Parent.findViewById(R.id.rl);
        mLine_bottom = (View) view_Parent.findViewById(R.id.line_bottom);

		img_Left = (ImageView) view_Parent.findViewById(R.id.img_left);
		img_Left2 = (ImageView) view_Parent.findViewById(R.id.img_left2);
		img_Right = (ImageView) view_Parent.findViewById(R.id.img_right);
		img_right_new = (ImageView) view_Parent
				.findViewById(R.id.img_right_new);
		img_title = (ImageView) view_Parent.findViewById(R.id.img_title);

		txt_Left = (TextView) view_Parent.findViewById(R.id.txt_left);
		txt_Right = (TextView) view_Parent.findViewById(R.id.txt_right);
		txt_Title = (TextView) view_Parent.findViewById(R.id.txt_title);
		txt_Title2 = (TextView) view_Parent.findViewById(R.id.txt_title2);
		view_Parent.setBackgroundColor(context.getResources().getColor(
				R.color.white));

		view_rightTvAndIvLL = ViewUtils.getView(view_Parent,R.id.view_rightTvAndIvLL);
		view_rightTv = ViewUtils.getView(view_Parent,R.id.view_rightTv);
		view_rightIv = ViewUtils.getView(view_Parent,R.id.view_rightIv);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				(int)context.getResources().getDimension(R.dimen.titleBarHeight));
		addView(view_Parent, params);

	}

	public void showView_rightTvAndIvLL(int imgRes, String text, View.OnClickListener onClickListener){
		ViewUtils.setVisible(view_rightIv);
		ViewUtils.setVisible(view_rightTv);
		ViewUtils.setVisible(view_rightTvAndIvLL);

		TVUtils.setText(view_rightTv,text);

		view_rightIv.setImageResource(imgRes);

		if (onClickListener!=null) {
			view_rightTvAndIvLL.setOnClickListener(onClickListener);
		}

	}

	/**
	 * 设置标题
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:15:06
	 * @updateTime 2014年3月30日,下午6:15:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 *            文本资源Id
	 */
	public void setTitle(int resId) {
		txt_Title.setVisibility(View.VISIBLE);
		txt_Title.setText(context.getString(resId));
	}
	public void setBackground(int color) {
		mView_rl.setBackgroundColor(color);
	}
    public void setLinebility(boolean isFlag) {
        if (isFlag){
            mLine_bottom.setVisibility(VISIBLE);
        }else {
            mLine_bottom.setVisibility(GONE);
        }
    }
	public void setTitle(int stringId, int colorId) {
		txt_Title.setVisibility(View.VISIBLE);
		txt_Title.setText(context.getString(stringId));
		txt_Title.setTextColor(colorId);
	}

	public void setTitleColor(int colorId) {
		txt_Title.setTextColor(colorId);
	}

	/**
	 * 获取标题
	 * @return
     */
	public TextView getTitleTV(){
		return  txt_Title;
	}

	/**
	 * 设置标题
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:16:23
	 * @updateTime 2014年3月30日,下午6:16:23
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param title
	 *            标题文本内容
	 */
	public void setTitle(CharSequence title) {
		txt_Title.setVisibility(View.VISIBLE);
		txt_Title.setText(title);
	}

	/**
	 * 设置标题监听事件
	 * 
	 * @version 1.0
	 * @createTime 2014年8月28日,上午10:08:15
	 * @updateTime 2014年8月28日,上午10:08:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 */
	public void setOnTitleListener(OnClickListener listener) {
		Drawable drawable = getResources().getDrawable(
				R.mipmap.icon_white_arrow_down);
		txt_Title.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,
				null);
		txt_Title.setCompoundDrawablePadding(DisplayUtil.dip2px(context, 5));
		txt_Title.setOnClickListener(listener);
	}

	/**
	 * 设置标题点击事件的图标
	 * 
	 * @version 1.0
	 * @createTime 2014年10月15日,下午3:55:22
	 * @updateTime 2014年10月15日,下午3:55:22
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 */
	public void setOnTitleRightImage(int resId) {
		Drawable drawable = getResources().getDrawable(resId);
		txt_Title.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,
				null);
		txt_Title.setCompoundDrawablePadding(DisplayUtil.dip2px(context, 5));
	}

	/**
	 * 设置左边图片按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:18:07
	 * @updateTime 2014年3月30日,下午6:18:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 *            图片资源id
	 */
	public void setLeftImgBtn(int resId, OnClickListener listener) {
		img_Left.setVisibility(View.VISIBLE);
		img_Left.setImageResource(resId);
		view_LeftBtn.setOnClickListener(listener);
	}

	/**
	 * 设置左边2图片按钮
	 *
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:18:07
	 * @updateTime 2014年3月30日,下午6:18:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param resId
	 *            图片资源id
	 */
	public void setLeftImgBtn2(int resId, OnClickListener listener) {
		img_Left2.setVisibility(View.VISIBLE);
		img_Left2.setImageResource(resId);
		img_Left2.setOnClickListener(listener);
	}

	public void setLeftImgBtn2Gone() {
		ViewUtils.setGone(img_Left2);
	}

	/**
	 * 设置左边按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:25:51
	 * @updateTime 2014年3月30日,下午6:25:51
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *            按钮文本内容
	 * @param listener
	 *            按钮监听事件
	 */
	public void setLeftBtn(String text, OnClickListener listener) {
		txt_Left.setVisibility(View.VISIBLE);
		txt_Left.setText(text);
		view_LeftBtn.setOnClickListener(listener);
	}

	/**
	 * 设置左边带箭头按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:26:32
	 * @updateTime 2014年3月30日,下午6:26:32
	 * @createAuthor CodeApe
	 * @updateAuthor wjx
	 * @updateInfo (添加了img_Left.setVisibility(View.VISIBLE))
	 * 
	 * @param text
	 *            按妞文本内容
	 * @param listener
	 *            按钮监听事件
	 */
	public void setLeftArrowBtn(String text, OnClickListener listener) {
		img_Left.setVisibility(View.VISIBLE);
		txt_Left.setVisibility(View.VISIBLE);
		txt_Left.setText(text);
		view_LeftBtn.setOnClickListener(listener);
	}

	/**
	 * 设置右边图片按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:18:07
	 * @updateTime 2014年3月30日,下午6:18:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 *            图片资源id
	 */
	public void setRightImgBtn(int resId, OnClickListener listener) {
		img_Right.setVisibility(View.VISIBLE);
		img_Right.setImageResource(resId);
		view_RightBtn.setOnClickListener(listener);
	}

	/**
	 * 设置右边图片按钮
	 * 
	 * @version 1.0
	 * @createTime 2015-4-24,下午12:52:07
	 * @updateTime 2015-4-24,下午12:52:07
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 *            图片资源id
	 */
	public void setRightImgBtn(int resId) {
		img_Right.setVisibility(View.VISIBLE);
		img_Right.setImageResource(resId);
	}

	/**
	 * 设置右边图片按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:18:07
	 * @updateTime 2014年3月30日,下午6:18:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 *            图片资源id
	 */
	public void setRightImgBtn(int resId, OnLongClickListener listener) {
		img_Right.setVisibility(View.VISIBLE);
		img_Right.setImageResource(resId);
		view_RightBtn.setOnLongClickListener(listener);
	}

	/**
	 * 设置右边按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:25:51
	 * @updateTime 2014年3月30日,下午6:25:51
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *            按钮文本内容
	 * @param listener
	 *            按钮监听事件
	 */
	public void setRightBtn(String text, OnClickListener listener) {
		txt_Right.setVisibility(View.VISIBLE);
		txt_Right.setText(text);
		view_RightBtn.setOnClickListener(listener);
	}

	public void setRightBtn(String text, int colorId, OnClickListener listener) {
		txt_Right.setVisibility(View.VISIBLE);
		txt_Right.setText(text);
		txt_Right.setTextColor(colorId);
		view_RightBtn.setOnClickListener(listener);
	}

	/**
	 * 设置右边点击事件
	 * 
	 * @version 1.0
	 * @createTime 2015年7月1日,下午3:45:53
	 * @updateTime 2015年7月1日,下午3:45:53
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 */
	public void setRightBtn(OnClickListener listener) {
		view_RightBtn.setOnClickListener(listener);
	}

	/**
	 * 设置右边按钮(带图片)
	 * 
	 * @version 1.0
	 * @createTime 2015年7月1日,下午3:45:53
	 * @updateTime 2015年7月1日,下午3:45:53
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *            按钮文本内容
	 *            按钮监听事件
	 * @param drawable
	 *            图片资源
	 * @param direction
	 *            方向(默认左边) 1==left，2==top, 3==right, 4==bottom
	 */
	public void setRightBtn(String text, Drawable drawable, int direction) {
		txt_Right.setVisibility(View.VISIBLE);

		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		if (direction == 2) {
			txt_Right.setCompoundDrawables(null, drawable, null, null); // 设置左图标
		} else if (direction == 3) {
			txt_Right.setCompoundDrawables(null, null, drawable, null); // 设置左图标
		} else if (direction == 4) {
			txt_Right.setCompoundDrawables(null, null, null, drawable); // 设置左图标
		} else {
			txt_Right.setCompoundDrawables(drawable, null, null, null); // 设置左图标
		}
		txt_Right.setGravity(Gravity.CENTER_VERTICAL);
		txt_Right.setText(text);
	}

	/**
	 * 是否禁用右边试图
	 * 
	 * @version 1.0
	 * @createTime 2014年10月14日,上午9:44:45
	 * @updateTime 2014年10月14日,上午9:44:45
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param enabled
	 */
	public void setRightEnabled(boolean enabled) {
		txt_Right.setEnabled(enabled);
	}

	/**
	 * 设置右边的背景
	 * 
	 * @version 1.0
	 * @createTime 2014年10月14日,上午9:45:11
	 * @updateTime 2014年10月14日,上午9:45:11
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void setRightTextColor(int resid) {
		txt_Right.setTextColor(resid);
	}

	/**
	 * 设置右边带箭头按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:26:32
	 * @updateTime 2014年3月30日,下午6:26:32
	 * @createAuthor CodeApe
	 * @updateAuthor wjx
	 * @updateInfo (添加了img_Right.setVisibility(View.VISIBLE))
	 * 
	 * @param text
	 *            按妞文本内容
	 * @param listener
	 *            按钮监听事件
	 */
	public void setRightArrowBtn(String text, OnClickListener listener) {
		img_Right.setVisibility(View.VISIBLE);
		txt_Right.setVisibility(View.VISIBLE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.leftMargin = -DisplayUtil.dip2px(context, 15);
		img_Right.setLayoutParams(params);
		txt_Right.setText(text);
		view_RightBtn.setOnClickListener(listener);
	}

	public ImageView getImg_Right() {
		return img_Right;
	}

	/**
	 * 设置返回按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:29:14
	 * @updateTime 2014年3月30日,下午6:29:14
	 * @createAuthor CodeApe
	 * @updateAuthor wjx
	 * @updateInfo (更换了动画)
	 * 
	 */
	public void setBackBtn() {
		addBackBtn(null);
	}

	public void addBackBtn(final OnClickListener onBackClickListener) {
		img_Left.setVisibility(View.VISIBLE);
		view_LeftBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showInput(false);
				((Activity) context).finish();
				((Activity) context).overridePendingTransition(
						R.anim.exit_enter, R.anim.exit_exit);
				if (onBackClickListener!=null) {
					onBackClickListener.onClick(v);
				}
			}
		});
	}

	/**
	 * 设置右边按钮文本内容
	 * 
	 * @version 1.0
	 * @createTime 2014年5月19日,下午5:13:09
	 * @updateTime 2014年5月19日,下午5:13:09
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *            按钮文本内容
	 */
	public void setRightBtnText(String text) {
		view_RightBtn.setVisibility(View.VISIBLE);
		txt_Right.setText(text);
	}

	/**
	 * 设置返回按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:36:59
	 * @updateTime 2014年3月30日,下午6:36:59
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 *            返回按钮监听事件
	 */
	public void setBackBtn(OnClickListener listener) {
		img_Left.setVisibility(View.VISIBLE);
		view_LeftBtn.setOnClickListener(listener);

	}

	/**
	 * 设置返回按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:39:49
	 * @updateTime 2014年3月30日,下午6:39:49
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *            返回按钮文本
	 */
	public void setBackBtn(String text) {
		txt_Left.setVisibility(View.VISIBLE);
		txt_Left.setText(text);
		view_LeftBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showInput(false);
				((Activity) context).finish();
				((Activity) context).overridePendingTransition(
						R.anim.exit_enter, R.anim.exit_enter);
			}
		});
	}

	/**
	 * 设置返回按钮
	 * 
	 * @version 1.0
	 * @createTime 2014年3月30日,下午6:37:51
	 * @updateTime 2014年3月30日,下午6:37:51
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *            返回按钮文本
	 * @param listener
	 *            返回按钮事件
	 */
	public void setBackBtn(String text, OnClickListener listener) {
		img_Left.setVisibility(View.VISIBLE);
		txt_Left.setVisibility(View.VISIBLE);
		txt_Left.setText(text);
		view_LeftBtn.setOnClickListener(listener);
		((Activity) context).overridePendingTransition(R.anim.enter_exit,
				R.anim.enter_enter);
	}

	/**
	 * 设置右边按钮是否可有
	 * 
	 * @version 1.0
	 * @createTime 2014年4月9日,下午4:54:10
	 * @updateTime 2014年4月9日,下午4:54:10
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param enable
	 *            true 可用 false 不可用
	 */
	public void setRightBtnEnable(boolean enable) {

		view_RightBtn.setEnabled(enable);

	}

	/**
	 * 设置右边按钮显示状态
	 * 
	 * @version 1.0
	 * @createTime 2014年5月20日,下午12:06:43
	 * @updateTime 2014年5月20日,下午12:06:43
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param visibility
	 *            View.GONE, View.VISIBLE
	 */
	public void setRightBtnVisibility(int visibility) {
		view_RightBtn.setVisibility(visibility);
	}

	/**
	 * 是否关闭键盘
	 * 
	 * @version 1.0
	 * @createTime 2014年4月9日,下午4:55:52
	 * @updateTime 2014年4月9日,下午4:55:52
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param show true 显示， false 关闭键盘
	 */
	public void showInput(boolean show) {
		try {
			if (show) {
				InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInputFromInputMethod(((Activity) context)
						.getCurrentFocus().getApplicationWindowToken(), 0);
			} else {
				InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(((Activity) context)
						.getCurrentFocus().getApplicationWindowToken(), 0);
			}
		} catch (NullPointerException e1) {

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 显示副标题
	 * 
	 * @version 1.0
	 * @createTime 2014年4月9日,下午5:03:30
	 * @updateTime 2014年4月9日,下午5:03:30
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param title
	 *            标题内容
	 */
	public void setSecondTitle(String title) {
		txt_Title2.setVisibility(View.VISIBLE);
		txt_Title2.setText(title);
	}

	/**
	 * 设置字体颜色
	 * 
	 * @version 1.0
	 * @createTime 2014-11-26,下午4:55:55
	 * @updateTime 2014-11-26,下午4:55:55
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param color
	 */
	public void setTxtColor(int color) {
		view_Parent.setBackgroundColor(color);

		txt_Right.setTextColor(color);
		txt_Title.setTextColor(color);
	}

	/**
	 * 设置背景颜色
	 * 
	 * @version 1.0
	 * @createTime 2014-11-26,下午4:55:55
	 * @updateTime 2014-11-26,下午4:55:55
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param color
	 */
	public void setBgColor(int color) {
		view_Parent.setBackgroundColor(color);

	}

	/**
	 * 设置右边按钮图标(右边开始算，第二个按钮)
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015-7-10,下午1:13:40
	 * @updateTime 2015-7-10,下午1:13:40
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 *            图片资源id
	 * @param listener
	 *            按钮点击事件
	 */
	public void setRightImgNewBtn(int resId, OnClickListener listener) {
		img_right_new.setVisibility(View.VISIBLE);
		img_right_new.setImageResource(resId);
		view_RightBtn_New.setOnClickListener(listener);
	}

	/**
	 * 
	 * 获取左边按钮
	 * 
	 * @version 1.0
	 * @createTime 2015年9月14日,上午10:30:26
	 * @updateTime 2015年9月14日,上午10:30:26
	 * @createAuthor gushiyong
	 * @updateAuthor gushiyong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public TextView getLeftBtnView() {
		return txt_Left;
	}

	/**
	 * 
	 * 获取右边按钮
	 * 
	 * @version 1.0
	 * @createTime 2015年9月14日,上午10:31:14
	 * @updateTime 2015年9月14日,上午10:31:14
	 * @createAuthor gushiyong
	 * @updateAuthor gushiyong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public TextView getRightBtnView() {
		return txt_Right;
	}

	/** 设置中间图片资源 */
	public void setCenterImageRes(int res) {
		img_title.setVisibility(View.VISIBLE);
		img_title.setImageResource(res);
	}

	public void setCenterImageListener(OnClickListener listener) {
		img_title.setOnClickListener(listener);
	}

	@Override
	public boolean isInEditMode() {
		return true;
	}
}
