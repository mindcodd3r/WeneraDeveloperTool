package com.mindcod3r.wenera;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mindcod3r.wenera.Components.BottomBar.BottomBar;
import com.mindcod3r.wenera.Components.GridMenu.GridMenu;
import com.mindcod3r.wenera.Components.GridMenu.MenuItem;

public class MenuActivity extends AppCompatActivity {
    LinearLayout mainPage;
    GridMenu gridMenu;
    ValueAnimator animator;
    Handler handler, handler2;

    private void showMenuPage() {
        mainPage.addView(gridMenu);
    }

    ValueAnimator profileAnim;
    private void showProfilePage() {
        LayoutInflater inflater = this.getLayoutInflater();
        View page = inflater.inflate(R.layout.profile_page, null);

        profileAnim = ValueAnimator.ofInt(150, 25);
        profileAnim.addUpdateListener((anim) -> {
            View view = page.findViewById(R.id.line);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            lp.leftMargin = Utils.dp(this, (int) anim.getAnimatedValue());
            lp.rightMargin = Utils.dp(this, (int) anim.getAnimatedValue());
            view.setLayoutParams(lp);
        });
        profileAnim.setDuration(400);
        profileAnim.start();

        mainPage.addView(page, -1, -1);
    }
    private void showPage(int idx) {
        handler.removeCallbacksAndMessages(null);
        handler2.removeCallbacksAndMessages(null);

        animator = ValueAnimator.ofFloat(mainPage.getAlpha(), 0f);
        animator.setDuration(250);
        animator.addUpdateListener((anim) -> {
            mainPage.setAlpha((float)anim.getAnimatedValue());
        });
        animator.start();
        handler.postDelayed(() -> {
            mainPage.removeAllViews();
            switch (idx) {
                case 0:
                    showMenuPage();
                    break;
                case 1:
                    showProfilePage();
                    break;
            }

            animator = ValueAnimator.ofFloat(mainPage.getAlpha(), 1.0f);
            animator.setDuration(250);
            animator.addUpdateListener((anim) -> {
                mainPage.setAlpha((float)anim.getAnimatedValue());
            });
            animator.start();
        }, 250);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        handler  = new Handler();
        handler2 = new Handler();

        mainPage = (LinearLayout) findViewById(R.id.mainPage);

        gridMenu = new GridMenu(getApplicationContext(), 3, 2, 200, 10);
        gridMenu.getCell(0).addView(new MenuItem(getApplicationContext(), R.drawable.snowplow, "ImGui builder", "Version 1.0"), -1, -1);
        gridMenu.getCell(1).addView(new MenuItem(getApplicationContext(), R.drawable.apps, "Java builder", "soon").disable(), -1, -1);
        gridMenu.getCell(2).addView(new MenuItem(getApplicationContext(), R.drawable.user_robot, "ImGui menu Ai", "soon").disable(), -1, -1);

        gridMenu.getCell(0).setOnClickListener((v) -> {
            startActivity(new Intent(this, ImguiEditor.class));
        });

        ((TextView) findViewById(R.id.pageTitle)).setText("Tools");
        ((BottomBar) findViewById(R.id.bottomBar)).setCallback((idx, hint) -> {
            ((TextView) findViewById(R.id.pageTitle)).setText(hint);
            showPage(idx);
        });

        showPage(0);
    }
}