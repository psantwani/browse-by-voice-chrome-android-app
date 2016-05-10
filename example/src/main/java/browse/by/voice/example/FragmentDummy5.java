package browse.by.voice.example;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import browse.by.voice.R;
import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.camera.CameraController;

public class FragmentDummy5 extends Fragment {

    private ScannerLiveView camera;
    private CameraController controller;
    private boolean flashStatus;
    private static final String url_update_product = "http://browsebyvoicewebapp.azurewebsites.net/updatedb.php";
    private static final String url_insert_product = "http://browsebyvoicewebapp.azurewebsites.net/insertandroid.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BARCODE = "barcode_value";
    private static final String TAG_ID = "id";
    private static final String TAG_ANDROID = "android";
    private static final String TAG_PC = "pc_id";
    JSONParser jsonParser = new JSONParser();
    private Integer PortAvailable;
    String ipAddressString;
    String barcodeData = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dummyactivity5,container,false);
        camera = (ScannerLiveView) v.findViewById(R.id.camview);
        camera.startScanner();
        //portscan();
        //wifiIpAddress(getActivity().getApplicationContext());
        //updateDatabase();

        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(getActivity(), "Scanner Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(getActivity(), "Scanner Stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(getActivity(), "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(final String data) {
                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                        barcodeData = data;
                        if (barcodeData != "") {
                            new insertDb().execute();
                        }


            }
        });
        return v;
    }

    protected void wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();


        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }
    }

    protected  void portscan(){
        ServerSocket s = null;
        try {
            s = new ServerSocket(0);
            PortAvailable = s.getLocalPort();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void updateDatabase(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair(TAG_BARCODE, "'VtBoQamM1ngf5vhy9Xy6X6wMcKIlIzBG'"));
        params.add(new BasicNameValuePair(TAG_ID, "221"));
        params.add(new BasicNameValuePair(TAG_ANDROID, "'"+ipAddressString+":"+PortAvailable+"'"));
        JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                "POST", params);
        Intent i = getActivity().getIntent();
        getActivity().setResult(100, i);
        getActivity().finish();
        /**
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully updated
                Intent i = getActivity().getIntent();
                // send result code 100 to notify about product update
                getActivity().setResult(100, i);
                getActivity().finish();
            } else {
                // failed to update product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
         **/
    }

    public class insertDb extends AsyncTask<Void,Void,Void>{
        protected Void doInBackground(Void... params) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    List<NameValuePair> insertParams = new ArrayList<NameValuePair>();
                    //params.add(new BasicNameValuePair(TAG_BARCODE, "'VtBoQamM1ngf5vhy9Xy6X6wMcKIlIzBG'"));
                    insertParams.add(new BasicNameValuePair(TAG_PC, "12"));
                    JSONObject json = jsonParser.makeHttpRequest(url_insert_product,
                            "POST", insertParams);
                    Intent i = getActivity().getIntent();
                    getActivity().setResult(100, i);
                    getActivity().finish();
                }
            });
            return null;
        }
    }
    protected void insertDatabase(String id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair(TAG_BARCODE, "'VtBoQamM1ngf5vhy9Xy6X6wMcKIlIzBG'"));
        params.add(new BasicNameValuePair(TAG_PC, id));
        JSONObject json = jsonParser.makeHttpRequest(url_insert_product,
                "POST", params);
        Intent i = getActivity().getIntent();
        getActivity().setResult(100, i);
        getActivity().finish();
        /**
         try {
         int success = json.getInt(TAG_SUCCESS);

         if (success == 1) {
         // successfully updated
         Intent i = getActivity().getIntent();
         // send result code 100 to notify about product update
         getActivity().setResult(100, i);
         getActivity().finish();
         } else {
         // failed to update product
         }
         } catch (JSONException e) {
         e.printStackTrace();
         }
         **/
    }

    @Override
    public void onResume()
    {
        super.onResume();
        camera.startScanner();
    }

    @Override
    public void onPause()
    {
        camera.stopScanner();
        super.onPause();
    }

}
