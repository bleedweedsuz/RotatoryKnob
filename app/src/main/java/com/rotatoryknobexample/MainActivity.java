package com.rotatoryknobexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zwarriorteam.rotatoryknob.RotatoryKnobView;

public class MainActivity extends AppCompatActivity {
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
                //..
            }

            @Override
            public void onRotateChange(int rotorValue) {
                String str = rotorValue + "\n" + rotatoryKnobView.getMinRotorValue() + "\n" + rotatoryKnobView.getMaxRotorValue();
                rotatoryText.setText(str);
            }
        });
    }
}