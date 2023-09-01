package com.mindcod3r.wenera;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mindcod3r.wenera.Components.Button.ButtonPositive;
import com.mindcod3r.wenera.Components.PickView.PickViewDialog;
import com.mindcod3r.wenera.ImguiComponents.ImguiMenu;
import com.mindcod3r.wenera.ImguiComponents.ImguiRect;
import com.mindcod3r.wenera.ImguiComponents.ImguiShape;
import com.mindcod3r.wenera.ImguiComponents.ImguiText;
import com.mindcod3r.wenera.ImguiComponents.ImguiViewer;
import com.mindcod3r.wenera.Components.PickView.PickView;

import java.util.ArrayList;

public class ImguiEditor extends AppCompatActivity {

    public static ImguiEditor instance;
    public static Context globalContext;
    public PickView pickShape;
    private LinearLayout argumentsLayout;

    ImageView nightModeButton, newShapeButton;
    public static ImguiViewer viewer;
    public static ImguiShape currentShape;
    public static int shapeNumber = 0;
    boolean nightMode = true;

    int currentNightColor;
    ValueAnimator animator;
    ButtonPositive confirm;
    public static ImguiMenu mainMenu;

    public ImguiRect newShapeRect() {
        int size = Utils.dpm(getApplicationContext(), 100);
        ImguiRect newRect = new ImguiRect(size, size, mainMenu.getWidth()/2-size/2, mainMenu.getHeight()/2-size/2);
        viewer.shapes.add(newRect);
        updateShapesList();
        viewer.updateScreen();
        return newRect;
    }

    public ImguiText newShapeText() {
        ImguiText newText = new ImguiText(0, 0);
        viewer.shapes.add(newText);
        updateShapesList();
        viewer.updateScreen();
        return newText;
    }

    public static int getIdNumber() {
        shapeNumber++;
        return shapeNumber;
    }

    String[] shapeNames = new String[] {"Rect", "Text", "Child"};
    public void showCreateShapeDialog() {
        new PickViewDialog(this, shapeNames, 3, 1).setCallback(
            new PickViewDialog.Callback() {
                @Override
                public void onItemPick(int idx, String str) {
                    switch (idx) {
                        case 0:
                            newShapeRect();
                            break;
                        case 1:
                            newShapeText();
                            break;
                    }
                }

                @Override
                public void onCancelled() {}
            }
        );
    }

    private void setNight(boolean isNight) {
        nightMode = isNight;
        animator = ValueAnimator.ofObject(new ArgbEvaluator(), currentNightColor, this.getColor(isNight ? R.color.light_gray : R.color.white));
        animator.setDuration(300);
        animator.addUpdateListener((anim) -> {
            currentNightColor = (int) anim.getAnimatedValue();
            ((View) findViewById(R.id.bg)).setBackgroundColor(currentNightColor);
        });
        animator.start();
    }

    public void updateShapesList() {
        String[] shapesId = new String[viewer.shapes.size()];
        for (int i = 0; i < viewer.shapes.size(); i++) {
            shapesId[i] = viewer.shapes.get(i).getId();
        }
        pickShape.setValues(shapesId, true);
    }

    public void updateShapesList(ImguiShape set) {
        String[] shapesId = new String[viewer.shapes.size()];
        for (int i = 0; i < viewer.shapes.size(); i++) {
            shapesId[i] = viewer.shapes.get(i).getId();
        }
        pickShape.setValues(shapesId);
        int idx;
        for (idx = 0; idx < shapesId.length; idx++) {
            if (shapesId[idx].equals(set.getId())) {
                break;
            }
        }
        pickShape.callback.onPick(idx, shapesId[idx]);
    }

    public void setShape(ImguiShape shape) {
        pickShape.setText(shape.getId());

        argumentsLayout.removeAllViews();
        currentShape = shape;
        argumentsLayout.addView(currentShape.getArguments(), -1, -1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgui_editor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        this.instance = this;

        nightModeButton = (ImageView) findViewById(R.id.nightmode);
        currentNightColor = this.getColor(R.color.light_gray);

        nightModeButton.setOnClickListener((view -> {
            setNight(!nightMode);
        }));

        globalContext = this;

        pickShape = (PickView) findViewById(R.id.pickable);
        pickShape.setCallback((idx, name) -> {
            setShape(viewer.shapes.get(idx));
        });
        pickShape.columns = 15;

        argumentsLayout = (LinearLayout) findViewById(R.id.argumentsLayout);

        viewer = (ImguiViewer) findViewById(R.id.imguiviewer);
        mainMenu = new ImguiMenu(0, 0, 0, 0);
        viewer.shapes.add(mainMenu);
        updateShapesList();

        viewer.setBackgroundResource(R.drawable.dashstroke);

        viewer.updateScreen();

        confirm = (ButtonPositive) findViewById(R.id.confirm);
        confirm.setOnClickListener((v) -> {
            currentShape.onConfirm();
            viewer.updateScreen();
        });

        newShapeButton = (ImageView) findViewById(R.id.newshape);
        newShapeButton.setOnClickListener(v -> showCreateShapeDialog());

    }
}