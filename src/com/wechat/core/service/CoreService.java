package com.wechat.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wechat.main.MenuManager;
import com.wechat.message.resp.Article;
import com.wechat.message.resp.NewsMessage;
import com.wechat.message.resp.TextMessage;
import com.wechat.util.MessageUtil;

/**
 * 核心服务类
 * 
 */
public class CoreService {
	
	public static String generateMessage(Map<String, String> requestMap, String type) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle(type);
		article1.setDescription("本论坛解决" + type + "问题");
		article1.setPicUrl("http://img2.tigtag.com/images/content/usvisa.jpg");
		article1.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MzYzNzI0MA==&mid=202926829&idx=2&sn=f5e2a40695447e02e091003d1ad07e28#rd");

		articleList.add(article1);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		String respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}

	public static String generateNewsMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle("签证");
		article1.setDescription("本论坛解决签证问题");
		article1.setPicUrl("http://img2.tigtag.com/images/content/usvisa.jpg");
		article1.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MzYzNzI0MA==&mid=202926829&idx=2&sn=f5e2a40695447e02e091003d1ad07e28#rd");

		Article article2 = new Article();
		article2.setTitle("申请");
		article2.setDescription("申请名校好帮手");
		article2.setPicUrl("www.7676u.org/images/banners.gif");
		article2.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MzYzNzI0MA==&mid=202926829&idx=3&sn=66b35e083fa9329044576099753b8e00#rd");

		Article article3 = new Article();
		article3.setTitle("移民");
		article3.setDescription("移民助您解决所有问题");
		article3.setPicUrl("http://gzdaily.dayoo.com/res/2/20121106/1352131422584_1.jpg");
		article3.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MzYzNzI0MA==&mid=202926829&idx=3&sn=66b35e083fa9329044576099753b8e00#rd");

		articleList.add(article2);
		articleList.add(article1);
		articleList.add(article3);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		String respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			System.out.println("requestMap: " + requestMap);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			System.out.println("fromUserName: " + fromUserName
					+ " toUserName: " + toUserName + " msgType: " + msgType);
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String reqContent = requestMap.get("Content");
				if (reqContent.equals("？")) {
					respContent = "回复数字1 -> 最新PNP消息 \n\n 回复数字2 -> 最新CEC消息 \n\n 回复数字3 -> 最新快速通道消息 \n\n 回复数字4 -> 基于位置的个性化服务 \n\n";
				} else if (reqContent.equals("1")) {
					return generateMessage(requestMap, "PNP");
				} else if (reqContent.equals("2")) {
					respContent = "CEC目前需有工作证明";
				} else if (reqContent.equals("3")) {
					return generateMessage(requestMap, "快速通道");
				} else if (reqContent.equals("4")) {
					respContent = "我们提供个性化的签证服务， 请发送您的地址信息，我们维护数据库来帮您个性化服务";
				} else {
					respContent = "您发送的是文本消息！";
				} 
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您周围有3家移民律师资讯处，分别在222华尔士街和232圣劳伦斯街";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					return generateNewsMessage(requestMap);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
					String eventKey = requestMap.get("EventKey");  
					  
                    if (eventKey.equals("21")) {  
                        respContent = "我们的产品可以根据用户的信息来提供个性化的移民服务";  
                    } else if (eventKey.equals("22")) {  
                    	return generateNewsMessage(requestMap);  
                    } 
				}
			}
			System.out.println("respContent: " + respContent);
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			System.out.println("after text to xml");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}
