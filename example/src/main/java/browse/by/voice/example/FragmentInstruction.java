package browse.by.voice.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.wunderlist.slidinglayer.SlidingLayer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import browse.by.voice.MainActivity;
import browse.by.voice.R;
import browse.by.voice.example.menu.SectionFragmentTitleActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.Sequence;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.framing.FrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class FragmentInstruction extends Fragment {

    private SlidingLayer mSlidingLayer;
    private Button ButtonOpen;
    private Button ButtonClose;
    private TextView ConnectionDetails;
    private TextView SessionPasswordCalc;

    AutobahnServerTest server;
    boolean listen = false;
    EditText etMessage;
    Button bSend;
    private String message1;
    private Socket ClientWebSocket;
    private WebSocket fragmentparamWebSocket;
    private Button ListenButton;
    private EditText PortInput;
    View fragmentView;
    private String SessionPassword;
    private Integer PortAvailable;
    private String OpenButtontextValue;
    private Typeface font;
    private Animation mEnterAnimation, mExitAnimation;
    private Integer touchcount;
    public static TourGuide mTutorialHandler;
    public static int OVERLAY_METHOD = 1;
    public static int OVERLAY_LISTENER_METHOD = 2;
    public static String CONTINUE_METHOD = "continue_method";
    private int mChosenContinueMethod;
    private  Boolean firstrun = false;
    private Boolean showTour = true;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Bundle bundle = this.getArguments();
        String instruction;
        String title;
        touchcount = 0;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = pxToDp(displaymetrics.heightPixels);
        int width = pxToDp(displaymetrics.widthPixels);

        fragmentView = inflater.inflate(R.layout.dummyactivity1, container, false);
        font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "font/Raleway-Regular.ttf");
        mSlidingLayer = (SlidingLayer) fragmentView.findViewById(R.id.slidingLayer1);
        mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
        ButtonOpen = (Button) fragmentView.findViewById(R.id.buttonOpen);
        (ButtonOpen).setTypeface(font);
        OpenButtontextValue = ((MainActivity)getActivity()).ConnectionButtonText("Open");
        if(OpenButtontextValue.equals("Disconnect")){ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);}
        else{ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);}
        ButtonOpen.setText(OpenButtontextValue);



        /**App tour **/
        /**Setup Animation**/
        Intent intent = getActivity().getIntent();
        mChosenContinueMethod = intent.getIntExtra(CONTINUE_METHOD, OVERLAY_METHOD);

        mEnterAnimation = new AlphaAnimation(0f, 1f);
        mEnterAnimation.setDuration(600);
        mEnterAnimation.setFillAfter(true);

        mExitAnimation = new AlphaAnimation(1f, 0f);
        mExitAnimation.setDuration(600);
        mExitAnimation.setFillAfter(true);

        if(firstrun && showTour) {
            Button connectioButton = (Button) fragmentView.findViewById(R.id.buttonOpen);

            TourGuide tourGuide1 = TourGuide.init(getActivity())
                    .setPointer(new Pointer())
                    .setToolTip(new ToolTip().setTitle("Welcome!").setDescription("This button will help you connect to your computer."))
                    .playLater(connectioButton);

            TourGuide tourGuide2 = TourGuide.init(getActivity())
                    .setPointer(new Pointer().setGravity(Gravity.TOP | Gravity.LEFT).setColor(Color.parseColor("red")))
                    .setToolTip(new ToolTip().setTitle("App menu").setDescription("Clicking on the left section of the bar will open menu."))
                    .setOverlay(new Overlay()
                                    .setEnterAnimation(mEnterAnimation)
                                    .setExitAnimation(mExitAnimation)
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mTutorialHandler.next();
                                        }
                                    })
                    )
                    .playLater(((MainActivity) getActivity()).drawer.getToolbar());


            Sequence sequence = new Sequence.SequenceBuilder()
                    .add(tourGuide1, tourGuide2)
                    .setDefaultOverlay(new Overlay()
                                    .setEnterAnimation(mEnterAnimation)
                                    .setExitAnimation(mExitAnimation)
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mTutorialHandler.next();
                                        }
                                    })
                    )
                    .setDefaultPointer(null)
                    .setContinueMethod(Sequence.ContinueMethod.OverlayListener)
                    .build();


            //TourGuide.init(getActivity()).playInSequence(sequence);
            mTutorialHandler = TourGuide.init(getActivity()).playInSequence(sequence);
            showTour = false;
        }
        /**Start again**/
        ButtonClose = (Button) fragmentView.findViewById(R.id.buttonClose);
        (ButtonClose).setTypeface(font);
        mSlidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        mSlidingLayer.setShadowSizeRes(R.dimen.shadow_size);
        mSlidingLayer.setChangeStateOnTap(false);
        etMessage = (EditText)fragmentView.findViewById(R.id.etMessage);
        bSend = (Button)fragmentView.findViewById(R.id.bSend);
        ListenButton = (Button)fragmentView.findViewById(R.id.button5);
        PortInput = (EditText)fragmentView.findViewById(R.id.edit2);


        if(((MainActivity)getActivity()).connected){ButtonOpen.setText("Disconnect");ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); this.listen = true;}
        else{ButtonOpen.setText("Connect");ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); this.listen = false;}
        ButtonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListenButton.performClick();
                if(mTutorialHandler != null){
                    mTutorialHandler.next();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTutorialHandler.cleanUp();
                        }
                    }, 8000);
                }
                if (!ButtonOpen.getText().equals("Disconnect")) {

                }

                if (ButtonOpen.getText().equals("Disconnect")) {
                    buttonClicked(v);
                }
            }
        });


        portscan();
        PortInput.setText(PortAvailable.toString());
        wifiIpAddress(getActivity().getApplicationContext());

        ConnectionDetails = (TextView) fragmentView.findViewById(R.id.swipeText);
        SessionPasswordCalc = (TextView) fragmentView.findViewById(R.id.swipeTextPassword);
        (ConnectionDetails).setTypeface(font);
        (SessionPasswordCalc).setTypeface(font);

        ConnectionDetails.setText(Html.fromHtml("Enter this session password in the Browse by voice chrome extension to complete connection"));
        SessionPasswordCalc.setText(SessionPassword);

        ButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });

        //Server Part

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        ListenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wlisten(fragmentView);
            }
        });

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage(fragmentView);
            }
        });

        return fragmentView;

    }


    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }


    public void wlisten(View paramView)
    {

        if (!this.listen)
        {
            ((EditText)paramView.findViewById(R.id.edit2)).setText(PortAvailable.toString());
            Object localObject = (EditText) paramView.findViewById(R.id.edit2);
            if (controlla_dati(((EditText)localObject).getText().toString()))
            {
                this.listen = true;
                final Button ListenButton = (Button)paramView.findViewById((R.id.button5));
                ListenButton.setText("Listen..");
                ButtonOpen.setText("Disconnect");
                ((MainActivity)getActivity()).ConnectionButtonText("Disconnect");
                ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                //Toast.makeText(getActivity(),"Connection to PC Requested",Toast.LENGTH_SHORT).show();
                paramView = (Button)paramView.findViewById(R.id.button5);
                //paramView.setText("Listen...");
                localObject = new Thread(new Task3(((EditText)localObject).getText().toString()));
                ((Thread)localObject).setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
                {
                    public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                listen = false;
                                ButtonOpen.setText("Connect");
                                OpenButtontextValue = ((MainActivity)getActivity()).ConnectionButtonText("Connect");
                                ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                //this.val$bt.setText("Listen");
                                ((EditText) getActivity().findViewById(R.id.edit2)).setText("");
                                Toast.makeText(getActivity(), "MAYBE THE PORT IS WRONG!! \n CHECK IT", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                ((Thread)localObject).start();
            }

            return;

        }

        this.listen = false;
        if(server != null) {
            if(ClientWebSocket != null) {
                message1 = "Aborting Browse By Voice";
                new WriteMessage().execute();
            }
            try {
                server.stop();
                server = null;
                ClientWebSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //((EditText)paramView.findViewById(R.id.edit2)).setText("");
        OpenButtontextValue = ((MainActivity)getActivity()).ConnectionButtonText("Connect");
        ButtonOpen.setText("Connect");
        ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        /**Toast.makeText(getActivity(),"Connection Closed by Phone.",Toast.LENGTH_SHORT).show();**/
    }

    public void MessageFromActivity(String text){
        if(text.equals("Chrome aborting Browse By Voice")){

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    listen = false;
                    if (server != null) {
                        try {
                            server.stop();
                            server = null;
                            ClientWebSocket = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ButtonOpen.setText("Connect");
                    ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    /**Toast.makeText(getActivity(),"Connection Closed by PC.",Toast.LENGTH_SHORT).show();**/
                }
            });
        }
        else if(text.equals("Chrome requesting master key dialog")){

        }
        else if (text.equals("first run")){
            firstrun = true;
        }
        else {
            message1 = text;
            new WriteMessage().execute();
        }
    }

    private class WriteMessage extends AsyncTask<Void,Void,Void> {
        protected Void doInBackground(Void... params) {
            ByteBuffer bb = ByteBuffer.wrap(message1.getBytes(Charset.forName("UTF-8")));
            if(fragmentparamWebSocket.isOpen()) {
                server.onMessage(fragmentparamWebSocket, bb);
            }
            //MainActivity.this.server.onMessage(MainActivity.this.paramWebSocket,bb);
            return null;
        }
    }


    public void toLong(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("ip address cannot be null or empty");
        }
        String[] octets = ipAddress.split(java.util.regex.Pattern.quote("."));
        if (octets.length != 4) {
            throw new IllegalArgumentException("invalid ip address");
        }
        long ip = 0;
        for (int i = 3; i >= 0; i--) {
            long octet = Long.parseLong(octets[3 - i]);
            if (octet > 255 || octet < 0) {
                throw new IllegalArgumentException("invalid ip address");
            }
            ip |= octet << (i * 8);
        }

        String d = Integer.toString(PortAvailable, 36);
        String e = Long.toString(ip,36);
        SessionPassword = e + "@" + d;
    }

    public  void SendMessage(View paramView){
        //Toast.makeText(getActivity(), message1, Toast.LENGTH_SHORT).show();
        message1 = etMessage.getText().toString();
        new WriteMessage().execute();
    }

    public boolean controlla_dati(String paramString)
    {
        if (paramString.length() == 0)
        {
            Toast.makeText(getActivity(), "HOST VALUE IS EMPTY", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, MainActivity.class));
            startActivity(new Intent(this.getActivity(),MainActivity.class));
            //startActivity(new Intent(getActivity(),getClass()));
            return false;
        }

        if (!((WifiManager)getActivity().getApplication().getSystemService(Context.WIFI_SERVICE)).isWifiEnabled())
        {
            Toast.makeText(getActivity(), "WIFI ISN'T ENABLED", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, MainActivity.class));
            //startActivity(new Intent(getActivity(),getClass()));
            startActivity(new Intent(this.getActivity(),MainActivity.class));
            return false;
        }
        return true;
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
        PortInput.setText(PortAvailable.toString());
    }

    protected void wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        if (ipAddressString != null) {
            toLong(ipAddressString);
        }
    }

    class Task3
            implements Runnable
    {
        String link;

        public Task3(String paramString)
        {
            this.link = paramString;
        }

        public void run()
        {
            Draft_17 localDraft_17 = new Draft_17();
            try
            {
                server = new AutobahnServerTest(Integer.valueOf(this.link).intValue(),localDraft_17,getActivity());
                new Thread(server).start();
                return;
            }
            catch (NumberFormatException localNumberFormatException)
            {
                for (;;)
                {
                    localNumberFormatException.printStackTrace();
                }
            }
            catch (UnknownHostException localUnknownHostException)
            {
                for (;;)
                {
                    localUnknownHostException.printStackTrace();
                }
            }
        }
    }


    public class AutobahnServerTest
            extends WebSocketServer
    {
        Context context;
        int counter = 0;

        public AutobahnServerTest(int paramInt, Draft paramDraft, Context paramContext)
                throws UnknownHostException
        {
            super(new InetSocketAddress(paramInt), Collections.singletonList(paramDraft));
            this.context = paramContext;
        }

        public AutobahnServerTest(InetSocketAddress paramInetSocketAddress, Draft paramDraft)
        {
            super(paramInetSocketAddress, Collections.singletonList(paramDraft));
        }

        public void add_text(final String paramString)
        {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    TextView localTextView = (TextView) fragmentView.findViewById(R.id.text1);
                    localTextView.setText(localTextView.getText().toString() + paramString + "\n");
                }
            });
        }

        public void connected()
        {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    OpenButtontextValue = ((MainActivity) getActivity()).ConnectionButtonText("Disconnect");
                    ButtonOpen.setText("Disconnect");
                    bSend.performClick();
                    //((TextView) fragmentView.findViewById(R.id.text1)).setVisibility(View.VISIBLE);
                }
            });
        }

        public void onClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean)
        {
            //toast("Connection Closed by PC. Phone is now Requesting.");
            ((MainActivity)getActivity()).SendMessageToFragment("Abort Mission. I repeat, Abort Mission.");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setCustomImage(R.drawable.bbv_128)
                            .setTitleText("You are disconnected!")
                            .setContentText("Click on 'Connect' button to re-establish connection with your PC.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    ButtonClose.performClick();
                                    sweetAlertDialog.hide();
                                }
                            })
                            .show();
                    listen = false;
                    ButtonOpen.setText("Connect");
                    ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                }
            });
        }

        public void onError(WebSocket paramWebSocket, Exception paramException)
        {
            //((MainActivity)getActivity()).SendMessageToFragment("Abort Mission. I repeat, Abort Mission.");
            paramException.printStackTrace();
        }

        public void onMessage(WebSocket paramWebSocket, String paramString)
        {
            if(paramString.equals("Chrome aborting Browse By Voice")){
                server.helpMethod(paramString);
            }
            if(paramString.equals("Chrome requesting master key dialog")){
                server.helpMethod(paramString);
            }
            if(paramString.equals("Chrome requesting master key dialog close")){
                server.helpMethod(paramString);
            }

            //toast("onMessage1 : " + paramString);
            //add_text(paramString);
        }

        public void onMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer)
        {
            //toast("onMessage2 : " + paramByteBuffer.toString());
            if(paramWebSocket.isOpen()) {
                paramWebSocket.send(paramByteBuffer);
            }
        }

        public void onOpen(WebSocket paramWebSocket, ClientHandshake paramClientHandshake)
        {
            this.counter += 1;
            /**toast("CONNECTION OPENED!");**/
            fragmentparamWebSocket = paramWebSocket;
            WebSocketImpl impl = (WebSocketImpl) paramWebSocket;
            ClientWebSocket = ( (SocketChannel) impl.key.channel()).socket();
            //toast(ClientWebSocket.getRemoteSocketAddress().toString());
            ((MainActivity)getActivity()).SendMessageToFragment("Piyush, come in.");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setCustomImage(R.drawable.bbv_128)
                            .setTitleText("You are connected!")
                            .setContentText("Go to 'My Voice' section and start browsing.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    ButtonClose.performClick();
                                    sweetAlertDialog.hide();
                                }
                            })
                            .show();
                    listen = true;
                    ButtonOpen.setText("Disconnect");
                    ButtonOpen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                }
            });
            connected();
        }

        public void onWebsocketMessageFragment(WebSocket paramWebSocket, Framedata paramFramedata)
        {
            //toast("Depracated Method");
            ((FrameBuilder)paramFramedata).setTransferemasked(false);
            paramWebSocket.sendFrame(paramFramedata);
        }

        public void toast(final String paramString)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(AutobahnServerTest.this.context, paramString,Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void overrideFonts(final Context context, final View v) {


                try {
                    if (v instanceof ViewGroup) {
                        ViewGroup vg = (ViewGroup) v;
                        for (int i = 0; i < vg.getChildCount(); i++) {
                            View child = vg.getChildAt(i);
                            overrideFonts(context, child);
                        }
                    } else if (v instanceof TextView) {
                        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Bold.ttf"));
                    } else if (v instanceof EditText) {
                        ((EditText) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Bold.ttf"));
                    } else if (v instanceof Button) {
                        ((Button) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Bold.ttf"));
                    }


                } catch (Exception e) {
                }
    }

    public void buttonClicked(View v) {
        if(ButtonOpen.getText().equals("OPENED")){
            //Toast.makeText(getActivity(),"Enter the above password in your PC",Toast.LENGTH_SHORT).show();
        }
        switch (v.getId()) {
            case R.id.buttonOpen:
                mSlidingLayer.openLayer(true);
                break;
            case R.id.buttonClose:
                mSlidingLayer.closeLayer(true);
                break;
        }
    }

}
