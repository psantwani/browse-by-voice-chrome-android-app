package browse.by.voice.example;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gc.materialdesign.views.ButtonRectangle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import browse.by.voice.MainActivity;
import browse.by.voice.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragmentDummy extends Fragment implements RecognitionListener {

    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private Button OffButton;
    private TextView returnedText;
    private Button speechOnButton;
    private SpeechRecognizer speech = null;
    private String lastCommand = "";
    private String LOG_TAG = "VoiceRecognitionActivity";
    private Intent recognizerIntent;
    private int OriginalVolume;
    private int MaxVolume;
    private boolean onButtonDisable = false;
    private boolean offButtonDisable = true;
    private View fragmentInstructionView;
    private TextView MessageToBePassed;
    private Button FireAction;
    private Button showPrompt;
    private AlertDialog globalAlertDialog;
    private Typeface font;
    private Boolean PreviousConnectedValue = ((MainActivity)getActivity()).connected;
    @Override
    public View onCreateView(final LayoutInflater inflater, final @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dummyactivity, container, false);
        fragmentInstructionView = inflater.inflate(R.layout.dummyactivity1, container, false);
        MessageToBePassed = (TextView)fragmentInstructionView.findViewById(R.id.etMessage);
        FireAction = (Button)fragmentInstructionView.findViewById(R.id.bSend);
        font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "font/Raleway-Regular.ttf");
        //Invisible View
        AudioManager manager = (AudioManager)getActivity().getApplication().getSystemService((Context.AUDIO_SERVICE));
        OriginalVolume  = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        MaxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        toggleButton = (ToggleButton) v.findViewById(R.id.toggleButton1);
        returnedText = (TextView) v.findViewById(R.id.textView1);
        returnedText.setTypeface(font);
        speechOnButton = (Button) v.findViewById(R.id.speechOn);
        //Prompt dialog
        showPrompt = (Button)v.findViewById(R.id.showprompt);
        showPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View promptsView = inflater.inflate(R.layout.prompts, container, false);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);


                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        //returnedText.setText(userInput.getText());
                                        //((MainActivity)getActivity()).SendMessageToFragment("Android prompt Confirm:" + userInput.getText().toString());
                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run() {
                                                returnedText.setText(userInput.getText());
                                                if(userInput.getText().toString() == ""){((MainActivity)getActivity()).SendMessageToFragment("Android prompt Confirm:BrowseByVoiceNoPassword");}
                                                else{
                                                ((MainActivity)getActivity()).SendMessageToFragment("Android prompt Confirm:" + userInput.getText().toString());}
                                            }
                                        });

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run() {
                                                returnedText.setText(userInput.getText());
                                                ((MainActivity) getActivity()).SendMessageToFragment("android prompt cancel");
                                            }
                                        });
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                globalAlertDialog = alertDialog;
                // show it
                alertDialog.show();
            }
        });

        //Visible view
        speechOnButton = (Button) v.findViewById(R.id.speechOn);
        OffButton = (Button) v.findViewById(R.id.button2);
        (speechOnButton).setTypeface(font);
        (OffButton).setTypeface(font);
        StartListening();

        speechOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onButtonDisable) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    if(calendar.get(Calendar.HOUR_OF_DAY) >= 0 && calendar.get(Calendar.HOUR_OF_DAY) <4){returnedText.setText("Good Evening. Say something :)");}
                    if(calendar.get(Calendar.HOUR_OF_DAY) >= 4 && calendar.get(Calendar.HOUR_OF_DAY) <12){returnedText.setText("Good Morning. Say something :)");}
                    else if(calendar.get(Calendar.HOUR_OF_DAY) >= 12 && calendar.get(Calendar.HOUR_OF_DAY) < 18){returnedText.setText("Good Afternoon. Say something :)");}
                    else if(calendar.get(Calendar.HOUR_OF_DAY) >= 18 && calendar.get(Calendar.HOUR_OF_DAY) < 25){returnedText.setText("Good Evening. Say something :)");}
                    toggleButton.performClick();
                    onButtonDisable = true;
                    offButtonDisable = false;
                }
            }
        });

        OffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!offButtonDisable) {
                    returnedText.setText("Speech listening stopped!");
                    StopListening(v);
                }
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    if (speech == null) {
                        StartListening();
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                }
            }
        });


        return v;
    }



    public void MessageFromActivity(String text){
        if(text.equals("Chrome requesting master key dialog")){
            if (onButtonDisable) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        showPrompt.performClick();
                    }
                });
            }
        }
        if(text.equals("Chrome requesting master key dialog close")){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    globalAlertDialog.hide();
                }
            });
        }

    }


    public void StartListening() {
        progressBar.setVisibility(View.INVISIBLE);
        returnedText.setVisibility(View.VISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(getActivity());
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getActivity().getApplication().getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    public void StopListening(View v){
        if(speech != null){
            speech.destroy();
            toggleButton.setChecked(false);
            progressBar.setVisibility(View.INVISIBLE);
            //returnedText.setVisibility(View.INVISIBLE);
            speech = null;
            offButtonDisable = true;
            onButtonDisable = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            //AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            //manager.setStreamVolume(AudioManager.STREAM_MUSIC, MaxVolume , OriginalVolume);
            Log.i(LOG_TAG, "destroy");
        }

    }


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }


    @Override
    public void onResults(Bundle results) {
        if(((MainActivity)getActivity()).connected != PreviousConnectedValue){
            if(((MainActivity)getActivity()).connected == false){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setCustomImage(R.drawable.bbv_128)
                                .setTitleText("You are disconnected!")
                                .setContentText("Click on 'Connect' button to re-establish connection with your PC.")
                                .show();
                    }
                });
            }
        }
        else{
            PreviousConnectedValue = ((MainActivity)getActivity()).connected;
        }
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        text = matches.get(0);
        //for (String result : matches)
        //text += result + "\n";
        lastCommand = text;
        returnedText.setText(text);
        MessageToBePassed.setText(text);
        ((MainActivity)getActivity()).SendMessageToFragment(text);
        FireAction.performClick();
        //etMessage.setText(text);
        //bSend.performClick();
        toggleButton.setChecked(true);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        toggleButton.setChecked(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onError(int errorCode) {
        if (speech != null) {
            speech.destroy();
            toggleButton.setChecked(false);
        }
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        if(!errorMessage.equals("No match")){
            returnedText.setText(errorMessage);
        }
        else {
            returnedText.setText("Last Command : " + lastCommand);
        }
        //Toast.makeText(MainActivity.this,speech.toString(),Toast.LENGTH_SHORT).show();
        speech = SpeechRecognizer.createSpeechRecognizer(getActivity());
        speech.setRecognitionListener(this);
        toggleButton.setChecked(true);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "Voice Recognition Service got busy. Please click the off button and again switch on the service.";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}
