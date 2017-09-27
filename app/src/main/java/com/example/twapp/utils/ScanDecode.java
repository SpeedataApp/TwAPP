package com.example.twapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.honeywell.barcode.HSMDecodeComponent;
import com.honeywell.barcode.HSMDecodeResult;
import com.honeywell.barcode.HSMDecoder;
import com.honeywell.camera.CameraManager;
import com.honeywell.license.ActivationManager;
import com.honeywell.license.ActivationResult;
import com.honeywell.plugins.decode.DecodeResultListener;

import java.io.UnsupportedEncodingException;

import static com.honeywell.barcode.Symbology.QR;

/**
 * Created by lenovo-pc on 2017/8/4.
 */

public class ScanDecode implements DecodeResultListener {
    private HSMDecoder hsmDecoder;
    private Context context;
    private CameraManager cameraManager;
    private boolean IsUtf8 = false;
    HSMDecodeComponent hsmDecodeComponent;

    public ScanDecode(Context context) {
        this.context = context;
        initScan();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.geomobile.se4500barcode");
        context.registerReceiver(receiver, intentFilter);
    }

    public void initScan() {
        try {
            //activate the API with your license key   trial-speed-tjian-08072017     无效测试key trial-jingt-tjian-05152017
//            ActivationResult activationResult = ActivationManager.activate(context, "trial-jingt-tjian-05152017");
            ActivationResult activationResult = ActivationManager.activate(context, "trial-speed-tjian-08072017");
            Toast.makeText(context, "Activation Result: " + activationResult, Toast.LENGTH_LONG).show();
            //get the singleton instance of the decoder
            hsmDecoder = HSMDecoder.getInstance(context);
            hsmDecoder.enableSymbology(QR);
            hsmDecoder.enableAimer(false);
//            hsmDecoder.setAimerColor(Color.RED);
            hsmDecoder.enableSound(false);
            hsmDecoder.setOverlayText("");
            hsmDecoder.setOverlayTextColor(Color.RED);
            cameraManager = CameraManager.getInstance(context);
            hsmDecoder.addResultListener(this);
            hsmDecodeComponent = new HSMDecodeComponent(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void DestroyScan() {
        hsmDecoder.removeResultListener(this);
        HSMDecoder.disposeInstance();
    }

    /**
     * 返回解码结果
     *
     * @param hsmDecodeResults
     */
    @Override
    public void onHSMDecodeResult(HSMDecodeResult[] hsmDecodeResults) {
        displayBarcodeData(hsmDecodeResults);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geomobile.se4500barcode".equals(intent.getAction())) {
                hsmDecodeComponent.enableScanning(true);
            }

        }
    };
    private String isdecodeDate = null;

    /**
     * 显示数据
     *
     * @param barcodeData 解码数据
     */
    private void displayBarcodeData(HSMDecodeResult[] barcodeData) {
        if (barcodeData.length > 0) {
            HSMDecodeResult firstResult = barcodeData[0];
            String decodeDate = null;
            if (isUTF8(firstResult.getBarcodeDataBytes())) {
                try {
                    decodeDate = new String(firstResult.getBarcodeDataBytes(),
                            "utf8");
//                    EventBus.getDefault().post(new Myeventbus(decodeDate));
                    Toast.makeText(context, decodeDate, Toast.LENGTH_SHORT).show();
                    Log.i("Reginers", "displayBarcodeData: ");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //判断扫描的内容是否是UTF8的中文内容
    private boolean isUTF8(byte[] sx) {
        //Log.d(TAG, "begian to set codeset");
        for (int i = 0; i < sx.length; ) {
            if (sx[i] < 0) {
                if ((sx[i] >>> 5) == 0x7FFFFFE) {
                    if (((i + 1) < sx.length) && ((sx[i + 1] >>> 6) == 0x3FFFFFE)) {
                        i = i + 2;
                        IsUtf8 = true;
                    } else {
                        if (IsUtf8)
                            return true;
                        else
                            return false;
                    }
                } else if ((sx[i] >>> 4) == 0xFFFFFFE) {
                    if (((i + 2) < sx.length) && ((sx[i + 1] >>> 6) == 0x3FFFFFE) && ((sx[i + 2] >>> 6) == 0x3FFFFFE)) {
                        i = i + 3;
                        IsUtf8 = true;
                    } else {
                        if (IsUtf8)
                            return true;
                        else
                            return false;
                    }
                } else {
                    if (IsUtf8)
                        return true;
                    else
                        return false;
                }
            } else {
                i++;
            }
        }
        return true;
    }
}
