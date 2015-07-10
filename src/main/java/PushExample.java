import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 推送变的如此简单
 */
public class PushExample {
    protected static final Logger LOG = LoggerFactory.getLogger(PushExample.class);


    // demo App defined in resources/jpush-api.conf ，设置一个key就可以了，这些信息是自己申请的，去极光推送官网申请
	private static final String appKey ="你的appkey";
	private static final String masterSecret = "你的masterSecret";

    /**
     * Title是可以随便定义的
     */
	public static final String TITLE = "这个应该是标题";
    public static final String ALERT = "稍微详细的内容";
    public static final String MSG_CONTENT = "这里是详细的内容，很长很长很长很长很长很长很长很长很长很长很长很长很长很长";
    //什么是REGISTRATION_ID，怎么获取这个，只要在我们的后台数据库中设计进入这个数据就可以确定了，很棒
    public static final String REGISTRATION_ID = "0900e8d85ef";
    //可能是某一类人群，例如员工管理中各种类型的员工
    public static final String TAG = "tag_api";

	public static void main(String[] args) {
	    testSendPush();
	}
	
	
	public static void testSendPush() {
	    // HttpProxy proxy = new HttpProxy("localhost", 3128);
	    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        
        // For push, all you need do is to build PushPayload object.
        //就仅仅是push就行了，下面是对所有的设备都要进行连接
        PushPayload payload = buildPushObject_all_all_alert();
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
	}

    /**
     * 向所有的设备推送，所有设备
     * @return
     */
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.alertAll(ALERT);
	}

    /**
     * 向所有的某些alias的设备进行推送，注意参数是可以有多个的，Android设备
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert() {
        String [] alias = new String[]{"alias1","alias2"};
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(ALERT))
                .build();
    }

    /**
     * 向所有的具有某些tag的设备进行推送，Android设备
     * @return
     */
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }

    /**
     * 还可以推送相应个某些键值对过去，很方便，Android和iOS设备
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                		.setAlert("alert content")
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle("Android Title").build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.addExtra("extra_key", "extra_value").build())
                		.build())
                .build();
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
    
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
    
    
}

