package com.wechat.main;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wechat.pojo.AccessToken;
import com.wechat.pojo.Button;
import com.wechat.pojo.CommonButton;
import com.wechat.pojo.ViewButton;
import com.wechat.pojo.ComplexButton;
import com.wechat.pojo.Menu;
import com.wechat.util.WeixinUtil;

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
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		if (true) {
			// 调用接口创建菜单
			long result = WeixinUtil.createMenu(getMenu(), at.getToken());

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
		ViewButton mainBtn1 = new ViewButton();
		mainBtn1.setName("社区");
		mainBtn1.setType("view");
		mainBtn1.setUrl("http://m.wsq.qq.com/143767443");
		
		CommonButton btn21 = new CommonButton();  
        btn21.setName("移民");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("签证");  
        btn22.setType("click");  
        btn22.setKey("22");  
        
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("个性化服务");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22});
		
		ViewButton mainBtn3 = new ViewButton();
		mainBtn3.setName("微官网");
		mainBtn3.setType("view");
		mainBtn3.setUrl("http://5222.m.weimob.com/weisite/home?pid=5222&bid=10400&wechatid=fromUsername");

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3});

		return menu;
	}
}