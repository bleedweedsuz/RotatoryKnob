package com.zwarriorteam.rotatoryknob;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RotatoryKnobView extends RelativeLayout implements GestureDetector.OnGestureListener {
    private static final String TAG = "RotatoryKnobView";

    private GestureDetector gestureDetector;
    private RotatoryKnobViewListener onRotatoryKnobViewListener;
    ImageView rkImageViewBack = null, rkImageViewRotor = null;
    private float lastTouchAngle = -1f, rotorAngle = 0;
    private boolean knobState = false;
    private int knobBackRes, knobRotorRes_Active, knobRotorRes_InActive;
    private int rotorValue = 0, rotorStep = 1, minRotorValue = 0, maxRotorValue = 100;

    public interface RotatoryKnobViewListener{
        void onStateChange(boolean state);
        void onRotateChange(int rotorValue);
    }

    public RotatoryKnobView(Context context) {
        super(context);
        this.init(context);
    }

    public RotatoryKnobView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.readAttributes(context, attrs);
        this.init(context);
    }

    private void init(Context context){
        gestureDetector = new GestureDetector(context, this);
        this.initImageView();
        this.setRotorValue(rotorValue);
    }

    private void readAttributes(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotatoryKnobView);
        knobBackRes = typedArray.getResourceId(R.styleable.RotatoryKnobView_RotatoryKnob_ImageBack, R.drawable.light_knob_back);
        knobRotorRes_Active  = typedArray.getResourceId(R.styleable.RotatoryKnobView_RotatoryKnob_ImageActive, R.drawable.light_knob_rotor);
        knobRotorRes_InActive  = typedArray.getResourceId(R.styleable.RotatoryKnobView_RotatoryKnob_ImageInactive, R.drawable.light_knob_rotor_inactive);

        rotorStep = typedArray.getInteger(R.styleable.RotatoryKnobView_RotatoryKnob_RotorStep, R.integer.RotatoryKnob_Step);
        minRotorValue = typedArray.getInteger(R.styleable.RotatoryKnobView_RotatoryKnob_MinRotorValue, R.integer.RotatoryKnob_Min_Value);
        maxRotorValue = typedArray.getInteger(R.styleable.RotatoryKnobView_RotatoryKnob_MaxRotorValue, R.integer.RotatoryKnob_Max_Value);
        rotorValue = typedArray.getInteger(R.styleable.RotatoryKnobView_RotatoryKnob_RotorValue, R.integer.RotatoryKnob_Value_Default);
        //rotorLock = typedArray.getBoolean(R.styleable.RotatoryKnobView_RotatoryKnob_RotorLock, getResources().getBoolean(R.bool.RotorKnob_RotorLock));

        typedArray.recycle();
    }

    private void initImageView(){
        if(rkImageViewBack == null) {
            rkImageViewBack = new ImageView(getContext());
            rkImageViewBack.setImageResource(knobBackRes);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(rkImageViewBack, layoutParams);
        }

        if(rkImageViewRotor == null){
            rkImageViewRotor = new ImageView(getContext());
            rkImageViewRotor.setImageResource(knobRotorRes_InActive);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(rkImageViewRotor, layoutParams);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            lastTouchAngle = -1; //Reset Last Touch Angle
            knobState = true;
            changeKnobStateView();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            knobState = false;
            changeKnobStateView();
        }
        if (gestureDetector.onTouchEvent(event)) return true;
        else return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //..
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float mouseX = e2.getX(), mouseY = e2.getY();
        changeRotation(mouseX, mouseY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //..
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private double cartesianToPolar(float x1, float y1, float x2, float y2){
        double dy = (y2 - y1), dx = (x2 - x1);
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        if(angle < 0 && angle >= -180){ return -angle; } //Custom angle adjustment for knob
        else{ return (180 + (180 - angle)); } //Custom angle adjustment for knob to make angle to 360 degree
    }

    private void setRotorAngle(float angle){
        if(rkImageViewRotor !=null){
            rkImageViewRotor.setRotation(-angle);
            if(onRotatoryKnobViewListener != null){
                onRotatoryKnobViewListener.onRotateChange(this.rotorValue);
            }
        }
    }

    private void changeRotation(float mouseX, float mouseY){
        int centerX = getWidth() / 2, centerY = getHeight() / 2;
        float currentTouchAngle = (float)cartesianToPolar(centerX, centerY, mouseX, mouseY);
        if(currentTouchAngle != lastTouchAngle && lastTouchAngle != -1){
            if(currentTouchAngle > lastTouchAngle){
                //Counter Clock Wise (CCW) Rotation
                float newRotorAngleCalc = currentTouchAngle - lastTouchAngle;
                rotorAngle += newRotorAngleCalc;
                if(rotorValue > minRotorValue) rotorValue -= rotorStep;
            }
            else{
                //Clock Wise (CW) Rotation
                float newRotorAngleCalc = lastTouchAngle - currentTouchAngle;
                rotorAngle -= newRotorAngleCalc;
                if(rotorValue < maxRotorValue) rotorValue += rotorStep;
            }
            setRotorAngle(rotorAngle);
            if (onRotatoryKnobViewListener != null) this.onRotatoryKnobViewListener.onRotateChange(rotorValue);
        }
        lastTouchAngle = currentTouchAngle;
    }

    private void changeKnobStateView(){
        if(rkImageViewRotor != null){
            if(knobState){ rkImageViewRotor.setImageResource(knobRotorRes_Active); }
            else{ rkImageViewRotor.setImageResource(knobRotorRes_InActive); }
            if(onRotatoryKnobViewListener != null){ this.onRotatoryKnobViewListener.onStateChange(knobState); }
        }
    }

    public void setOnRotatoryKnobViewListener(RotatoryKnobViewListener onRotatoryKnobViewListener) {
        this.onRotatoryKnobViewListener = onRotatoryKnobViewListener;
    }

    public void setRotorValue(int value){
        try {
            int calculatedAngle = value / this.getRotorStep();
            this.setRotorAngle(calculatedAngle);
            this.rotorAngle = calculatedAngle;
            if(onRotatoryKnobViewListener != null) onRotatoryKnobViewListener.onRotateChange(rotorValue);
        }
        catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
    }

    public float getRotorAngle(){
        return rotorAngle;
    }

    public boolean getKnobState(){
        return knobState;
    }

    public boolean isKnobState() {
        return knobState;
    }

    public int getRotorValue() {
        return rotorValue;
    }

    public int getRotorStep() {
        return rotorStep;
    }

    public int getMinRotorValue() {
        return minRotorValue;
    }

    public int getMaxRotorValue() {
        return maxRotorValue;
    }
}