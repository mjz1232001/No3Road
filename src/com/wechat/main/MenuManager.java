package com.wechat.main;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wechat.pojo.Button;
import com.wechat.pojo.ViewButton;
import com.wechat.pojo.ComplexButton;
import com.wechat.pojo.Menu;
import com.wechat.pojo.ViewButton;

/**
 * 菜单管理器类
 * 
 */
public class MenuManager {
//	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) throws JsonProcessingException {
		// 第三方用户唯一凭证
		String appId = "wx2ad6018bb1f16c02";
		// 第三方用户唯一凭证密钥
		String appSecret = "059feae011f01dcff6e15f6b457fb138";

		// 调用接口获取access_token
//		com.wechat.pojo.AccessToken at = com.wechat.util.WeixinUtil.getAccessToken(appId, appSecret);
		String token = "j-JnzNYNEctZvwzdGrGGv5NsJ-Blg6p7dDoCY5bmyfN18sf8DXpKWjh_a8zl6QklmDfFcw_hK1GYHaT0z5-ZWC5HfICAGd6FicZ688dzX0A";
		if (true) {
			// 调用接口创建菜单
			long result = com.wechat.util.WeixinUtil.createMenu(getMenu(), token);

			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public static Menu getMenu() {
		ViewButton btn11 = new ViewButton();
		btn11.setName("全部车型");
		btn11.setType("view");
		btn11.setUrl("http://www.3453.m.weimob.com/Webcar/SeriesModelList?pid=3453&wechatid=fromUsername&title=全部车型&wxref=mp.weixin.qq.com");

		ViewButton btn12 = new ViewButton();
		btn12.setName("BMW");
		btn12.setType("view");
		btn12.setUrl("http://www.3453.m.weimob.com/car/Webbrand/SeriesModelList/type/3/pid/3453/sid/56/mid/82/wechatid/fromUsername/title/全部车型");

		ViewButton btn13 = new ViewButton();
		btn13.setName("BMW2");
		btn13.setType("view");
		btn13.setUrl("http://www.3453.m.weimob.com/weisite/list?pid=3453&bid=7443&wechatid=fromUsername&ltid=4949&wxref=mp.weixin.qq.com");

		ViewButton btn14 = new ViewButton();
		btn14.setName("最新资讯");
		btn14.setType("view");
		btn14.setUrl("http://www.3453.m.weimob.com/weisite/list?pid=3453&bid=7443&wechatid=fromUsername&ltid=24768&wxref=mp.weixin.qq.com");
		
		ViewButton btn15 = new ViewButton();
		btn15.setName("微相册");
		btn15.setType("view");
		btn15.setUrl("http://www.3453.m.weimob.com/Webalbums/Albumslist?pid=3453&bid=7443&wxref=mp.weixin.qq.com");

		ViewButton btn21 = new ViewButton();
		btn21.setName("预约保养");
		btn21.setType("view");
		btn21.setUrl("http://www.3453.m.weimob.com/Webcar/CarReserveBook?pid=3453&wechatid=fromUsername&title=预约保养&wxref=mp.weixin.qq.com");

		ViewButton btn22 = new ViewButton();
		btn22.setName("销售管理");
		btn22.setType("view");
		btn22.setUrl("http://www.3453.m.weimob.com/Webcar/SellList?pid=3453&wechatid=fromUsername&title=联系销售&wxref=mp.weixin.qq.com");

		ViewButton btn23 = new ViewButton();
		btn23.setName("优惠活动");
		btn23.setType("view");
		btn23.setUrl("http://www.3453.m.weimob.com/weisite/list?pid=3453&bid=7443&wechatid=fromUsername&ltid=24768&wxref=mp.weixin.qq.com");


		ViewButton mainBtn3 = new ViewButton();
		mainBtn3.setName("进入微官网");
		mainBtn3.setType("view");
		mainBtn3.setUrl("http://www.3453.m.weimob.com/weisite/home?pid=3453&bid=7443&wechatid=fromUsername");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("签证");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("移民");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3});

		return menu;
	}
}