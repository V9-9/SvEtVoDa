package com.example.svetvoda;

import android.content.SharedPreferences;
import android.widget.Toast;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputA, inputB, inputC, inputD;
    private TextView resultText;
    private Button calculateButton;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputA = findViewById(R.id.inputA);
        inputB = findViewById(R.id.inputB);
        inputC = findViewById(R.id.inputC);
        inputD = findViewById(R.id.inputD);
        resultText = findViewById(R.id.resultText);
        calculateButton = findViewById(R.id.calculateButton);

        restoreInputState();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double a = Double.parseDouble(inputA.getText().toString());
                double b = Double.parseDouble(inputB.getText().toString());
                double c = Double.parseDouble(inputC.getText().toString());
                double d = Double.parseDouble(inputD.getText().toString());

                String resultMessage = String.format("Илья, привет!\nВода=%.2f руб.;\nЭлектричество=%.2f руб.;\nИТОГО: %.2f руб.",
                        ((a - 307.93)+(b + c)), d, ((a - 307.93) + (b + c) + d));
                resultText.setText(resultMessage);

                saveInputState();
            }
        });
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputA.setText("");
                inputB.setText("");
                inputC.setText("");
                inputD.setText("");

                saveInputState();
            }
        });
    }

    private void saveInputState() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("inputA", inputA.getText().toString());
        editor.putString("inputB", inputB.getText().toString());
        editor.putString("inputC", inputC.getText().toString());
        editor.putString("inputD", inputD.getText().toString());
        editor.apply();
    }

    private void restoreInputState() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        inputA.setText(sharedPreferences.getString("inputA", ""));
        inputB.setText(sharedPreferences.getString("inputB", ""));
        inputC.setText(sharedPreferences.getString("inputC", ""));
        inputD.setText(sharedPreferences.getString("inputD", ""));
    }

    public void copyText(View view) {
        String textToCopy = resultText.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", textToCopy);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Текст скопирован", Toast.LENGTH_SHORT).show();
    }
}