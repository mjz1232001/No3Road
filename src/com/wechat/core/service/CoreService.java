package com.wechat.core.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wechat.main.MenuManager;
import com.wechat.message.resp.TextMessage;
import com.wechat.util.MessageUtil;

/**
 * 核心服务类
 * 
 */
public class CoreService {
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
			System.out.println("fromUserName: " + fromUserName + " toUserName: " + toUserName + " msgType: " + msgType);
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
				if(reqContent.equals("11")) {
					// 第三方用户唯一凭证
					String appId = "wx2ad6018bb1f16c02";
					// 第三方用户唯一凭证密钥
					String appSecret = "059feae011f01dcff6e15f6b457fb138";

					// 调用接口获取access_token
					com.wechat.pojo.AccessToken at = com.wechat.util.WeixinUtil.getAccessToken(appId, appSecret);

					if (null != at) {
						// 调用接口创建菜单
						long result = com.wechat.util.WeixinUtil.createMenu(MenuManager.getMenu(), at.getToken());

						// 判断菜单创建结果
						if (0 == result)
							System.out.println("菜单创建成功！");
						else
							System.out.println("菜单创建失败，错误码：" + result);
					}
				}
				respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
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
					respContent = "谢谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
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
