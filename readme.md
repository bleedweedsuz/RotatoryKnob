### Rotatory Knob
Rotatory knob is a simple "Rotary Knob" system, with simple infinite rotating interface. Most of the time this type of UI is used in Volume Knob, Increment or Decrement Interface etc.

![Tests Screenshot](https://github.com/bleedweedsuz/RotatoryKnob/blob/master/Screenshot/screenshot.jpg)

#### How to use?
##### Gradle
```
//1. Add it in your root build.gradle at the end of repositories
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

//2. Add the dependency
dependencies {
    implementation 'com.github.bleedweedsuz:RotatoryKnob:0.03'
}
```

##### Design XML
```
<com.zwarriorteam.rotatoryknob.RotatoryKnobView
    android:layout_width="100dp"
    android:layout_height="100dp"

    app:RotatoryKnob_TapEnable="true"
    app:RotatoryKnob_KnobTapSize="40dp"

    app:RotatoryKnob_ImageBack="@drawable/knob_back"
    app:RotatoryKnob_ImageActive="@drawable/knob_active"
    app:RotatoryKnob_ImageInactive="@drawable/knob_inactive"

    app:RotatoryKnobTap_ImageActive="@drawable/tap_active"
    app:RotatoryKnobTap_ImageInActive="@drawable/tap_inactive"

    app:RotatoryKnob_MaxRotorValue="100"
    app:RotatoryKnob_MinRotorValue="0"
    app:RotatoryKnob_RotorStep="1"
    app:RotatoryKnob_RotorValue="30" />
```

##### JAVA
```
....
String TAG = "Rotatory Knob";
RotatoryKnobView rotatoryKnobView = findViewById(R.id.rotatoryKnobView);
rotatoryKnobView.setOnRotatoryKnobViewListener(new RotatoryKnobView.RotatoryKnobViewListener() {
    @Override
    public void onStateChange(boolean state) {
        Log.d(TAG, state + " :Knob State");
    }

    @Override
    public void onRotateChange(int rotorValue) {
        Log.d(TAG, rotorValue + " :Knob Rotor Value");
        Log.d(TAG, rotatoryKnobView.getKnobState() + " :Knob");
        Log.d(TAG, rotatoryKnobView.getLastKnobDirection() + " :Knob");
        Log.d(TAG, rotatoryKnobView.getMinRotorValue() + " :Knob");
        Log.d(TAG, rotatoryKnobView.getMaxRotorValue() + " :Knob");
        Log.d(TAG, rotatoryKnobView.getRotorValue() + " :Knob");
        Log.d(TAG, rotatoryKnobView.getRotorAngle() + " :Knob");
        Log.d(TAG, rotatoryKnobView.getRotorStep() + " :Knob");
    }

    @Override
    public void onTap(boolean isTap) {
        Log.e(TAG, isTap + " Knob Tap");
        rotatoryText.setText(isTap?"Tap":"UnTap");
    }
});
....
```

### Example For Rotatory Knob
[Example For Rotatory Knob](https://github.com/bleedweedsuz/RotatoryKnobExample)

### Upcoming Features
- [ ] Non infinite rotary knob using range value[a-b].
- [ ] Add click sound for per rotor value increment or decrement.
- [x] Tap system in rotary knob, using circle radius system.