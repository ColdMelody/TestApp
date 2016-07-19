package com.demos.testapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.demos.testapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class QRCodeActivity extends AppCompatActivity {

    private ImageView mIvQrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Button mBtnGetQrcode = (Button) findViewById(R.id.btn_get_qrcode);
        mIvQrcode= (ImageView) findViewById(R.id.iv_qecode);
        mBtnGetQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate();
            }
        });
    }
    private void generate(){
        Bitmap qrBitmap=generateBitmap();
        Bitmap logoBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Bitmap bitmap=addLogo(qrBitmap,logoBitmap);
        mIvQrcode.setImageBitmap(bitmap);
    }

    /**
     *
     * @return Bitmap
     */
    private Bitmap generateBitmap(){
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        Map<EncodeHintType,String> map=new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET,"utf-8");
        try {
            BitMatrix encode=qrCodeWriter.encode("感冒了嗓子齁的难受，喝水也没点用", BarcodeFormat.QR_CODE, 800, 800,map);
            int[] pixels=new int[800 * 800];
            for (int i = 0; i< 800; i++){
                for (int j = 0; j< 800; j++){
                    if (encode.get(i,j)){
                        pixels[i* 800 +j]=0x00000000;
                    }else {
                        pixels[i* 800 +j]=0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels,0, 800, 800, 800, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param qrBitmap 二维码
     * @param logoBitmap 二维码中心的logo
     * @return Bitmap
     */
    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        //缩小至五分之一
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }

}
