package my.com.tm.nis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 5/12/2017.
 */

public class Start extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);


        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new
                        Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

        });
   /* public void fade(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView3);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        image.startAnimation(animation1);
    }*/
}}