package com.mindcod3r.wenera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mindcod3r.wenera.Components.Button.ButtonDefaultOutline;
import com.mindcod3r.wenera.Components.Button.ButtonPositive;
import com.mindcod3r.wenera.Components.EditTextIcon;
import com.mindcod3r.wenera.Components.Button.ButtonDefaultOutline;
import com.mindcod3r.wenera.Components.Button.ButtonPositive;
import com.mindcod3r.wenera.Components.EditTextIcon;

public class MainActivity extends AppCompatActivity {

    EditTextIcon loginForm, passwordForm;
    ButtonDefaultOutline buyButton;
    ButtonPositive loginButton;

    private void login() {
        finish();
        startActivity(new Intent(this, MenuActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.context = this;
        loginForm = (EditTextIcon) findViewById(R.id.loginForm);
        passwordForm = (EditTextIcon) findViewById(R.id.passwordForm);
        loginButton = (ButtonPositive) findViewById(R.id.loginButton);
        buyButton = (ButtonDefaultOutline) findViewById(R.id.buyButton);

        loginButton.setOnClickListener((v) -> {
            login();
        });
    }
}