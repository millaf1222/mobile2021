package umn.ac.id.week04_31459;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TutorialActivity extends AppCompatActivity {
    private Button btnHalaman1, btnHalaman2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        btnHalaman1 = findViewById(R.id.main_button_change_1);
        btnHalaman2 = findViewById(R.id.main_button_change_2);

        btnHalaman1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHalaman1 = new Intent(TutorialActivity.this, SecondActivity.class);
                startActivity(intentHalaman1);
            }
        });

        btnHalaman2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHalaman2 = new Intent(TutorialActivity.this, ThirdActivity.class);
                startActivity(intentHalaman2);
            }
        });
    }
}