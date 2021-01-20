package com.rotatoryknobexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zwarriorteam.rotatoryknob.RotatoryKnobView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView rotatoryText;
    RotatoryKnobView rotatoryKnobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rotatoryText = findViewById(R.id.rotatoryText);
        rotatoryKnobView = findViewById(R.id.rotatoryKnobView);
        rotatoryKnobView.setOnRotatoryKnobViewListener(new RotatoryKnobView.RotatoryKnobViewListener() {
            @Override
            public void onStateChange(boolean state) {
                String state_str = state + "";
                rotatoryText.setText(state_str);
            }

            @Override
            public void onRotateChange(int rotorValue) {
                String str = rotorValue + "\n" + rotatoryKnobView.getMinRotorValue() + "\n" + rotatoryKnobView.getMaxRotorValue();
                rotatoryText.setText(str);
            }

            @Override
            public void onTap(boolean isTap) {
                Log.e(TAG, isTap + " <---");
                rotatoryText.setText(isTap?"Tap":"UnTap");
            }
        });
    }
}