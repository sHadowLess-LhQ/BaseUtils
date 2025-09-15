package cn.com.shadowless.baseutils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * 微信工具类
 *
 * @author sHadowLess
 */
public enum WeChatUtils {

    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 微信API接口
     */
    private IWXAPI iwxapi;

    /**
     * 获取实例
     *
     * @param context 上下文对象
     * @param appId   应用ID
     * @return 实例对象
     */
    public WeChatUtils getInstance(Context context, String appId) {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context, appId, false);
            iwxapi.registerApp(appId);
        }
        return this;
    }

    /**
     * Has we chat boolean.
     *
     * @return the boolean
     */
    public boolean canUseWeChat() {
        return iwxapi.isWXAppInstalled() && iwxapi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    /**
     * Gets api.
     *
     * @return the api
     */
    public IWXAPI getApi() {
        return iwxapi;
    }

    /**
     * 调起微信支付
     *
     * @param appId     the app id
     * @param partnerId the partner id
     * @param prepayId  the prepay id
     * @param nonceStr  the nonce str
     * @param timeStamp the time stamp
     * @param sign      the sign
     */
    public void openWeChatPay(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String sign) {
        //支付请求类
        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = partnerId;
        request.prepayId = prepayId;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;
        request.sign = sign;
        //调起微信支付
        iwxapi.sendReq(request);
    }

    /**
     * Open we chat share string.
     *
     * @param text  the text
     * @param scene the scene
     */
    public void openWeChatShareString(String text, int scene) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = scene;
        iwxapi.sendReq(req);
    }

    /**
     * Open we chat share img.
     *
     * @param bitmap the bitmap
     * @param scene  the scene
     */
    public void openWeChatShareImg(Bitmap bitmap, int scene) {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.thumbData = compressBitmapBytes2TargetSize(bitmapToByteArray(bitmap), 32 * 1024);
        bitmap.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = scene;
        iwxapi.sendReq(req);
    }

    /**
     * Open we chat share img.
     *
     * @param bitmap the bitmap
     * @param scene  the scene
     */
    public void openWeChatShareImg(byte[] bitmap, int scene) {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.thumbData = bitmap;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = scene;
        iwxapi.sendReq(req);
    }

    /**
     * Open we chat share video.
     *
     * @param url         the url
     * @param isLow       the is low
     * @param title       the title
     * @param description the description
     * @param bitmap      the bitmap
     * @param scene       the scene
     */
    public void openWeChatShareVideo(String url, boolean isLow, String title, String description, Bitmap bitmap, int scene) {
        WXVideoObject video = new WXVideoObject();
        if (isLow) {
            video.videoLowBandUrl = url;
        } else {
            video.videoUrl = url;
        }
        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = description;
        msg.thumbData = compressBitmapBytes2TargetSize(bitmapToByteArray(bitmap), 32 * 1024);
        bitmap.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = scene;
        iwxapi.sendReq(req);
    }

    /**
     * Open we chat share web.
     *
     * @param url         the url
     * @param title       the title
     * @param description the description
     * @param bitmap      the bitmap
     * @param scene       the scene
     */
    public void openWeChatShareWeb(String url, String title, String description, Bitmap bitmap, int scene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = compressBitmapBytes2TargetSize(bitmapToByteArray(bitmap), 32 * 1024);
        bitmap.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = scene;
        iwxapi.sendReq(req);
    }

    /**
     * Open we chat share mini program.
     *
     * @param url         the url
     * @param type        the type
     * @param programId   the program id
     * @param path        the path
     * @param title       the title
     * @param description the description
     * @param bitmap      the bitmap
     */
    public void openWeChatShareMiniProgram(String url, int type, String programId, String path, String title, String description, Bitmap bitmap) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        //兼容低版本的网页链接
        miniProgramObj.webpageUrl = url;
        //正式版:0，测试版:1，体验版:2
        miniProgramObj.miniprogramType = type;
        //小程序原始id
        miniProgramObj.userName = programId;
        //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        miniProgramObj.path = path;
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        //小程序消息title
        msg.title = title;
        // 小程序消息desc
        msg.description = description;
        // 小程序消息封面图片，小于128k
        msg.thumbData = compressBitmapBytes2TargetSize(bitmapToByteArray(bitmap), 128 * 1024);
        bitmap.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);
    }

    /**
     * Bitmap to byte array byte [ ].
     *
     * @param bitmap the bitmap
     * @return the byte [ ]
     */
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * 将Bitmap的字节流压缩为目标大小
     *
     * @param src        the src
     * @param targetSize 单位B
     * @return byte [ ]
     */
    private byte[] compressBitmapBytes2TargetSize(byte[] src, int targetSize) {
        // 将字节数据转换成临时bitmap对象，为压缩做准备
        Bitmap bmp = BitmapFactory.decodeByteArray(src, 0, src.length);
        byte[] result = getBytesFromCompressBitmap(bmp, targetSize);
        // 回收不用的Bitmap
        if (!bmp.isRecycled()) {
            bmp.recycle();
        }
        return result;
    }

    /**
     * 压缩bitmap的字节数据，quality每次减少5
     *
     * @param bitmap     the bitmap
     * @param targetSize the target size
     * @return byte [ ]
     */
    private byte[] getBytesFromCompressBitmap(Bitmap bitmap, int targetSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 默认quality为100，quality取值范围[0, 100]
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        byte[] bytes = baos.toByteArray();
        while (bytes.length > targetSize && quality >= 5) {
            quality -= 5;
            if (quality < 0) {
                quality = 0;
            }
            // 重置，不然会累加
            baos.reset();
            // 将数据写入ByteArrayOutputStream对象中
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            // 将流转换成字节数组
            bytes = baos.toByteArray();
        }
        // 关闭流
        try {
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
