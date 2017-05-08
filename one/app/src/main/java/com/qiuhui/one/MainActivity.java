package com.qiuhui.one;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int max_num;
    int max_question;
    int num;
    int right_cnt = 0;
    int error_cnt = 0;
    int a,b,c,d;
    int f1,f2,f3;
    int mode;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String log;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button_go = (Button) findViewById(R.id.go);
        final EditText edit_max_num = (EditText) findViewById(R.id.max_num);
        final EditText edit_max_question = (EditText) findViewById(R.id.question_num);
        final Button button_ok = (Button) findViewById(R.id.OK);
        final EditText text_answer = (EditText) findViewById(R.id.answer);
        final EditText text_max_num = (EditText) findViewById(R.id.max_num);
        final EditText text_max_question = (EditText) findViewById(R.id.question_num);
        final TextView text_debug = (TextView) findViewById(R.id.debug);
        final TextView text_question = (TextView) findViewById(R.id.question);
        final TextView text_total = (TextView) findViewById(R.id.total);
        final TextView text_right = (TextView) findViewById(R.id.right);
        final TextView text_error = (TextView) findViewById(R.id.error);
        final TextView text_result = (TextView) findViewById(R.id.result);
        final Chronometer timer = (Chronometer) findViewById(R.id.timer);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        num = 0;
        f1 =1 ;

        data_list = new ArrayList<String>();
        data_list.add("a + b= ?");
        data_list.add("a + b + c = ?");
        data_list.add("a + (?) + b = c");
        data_list.add("a + b = c + (?)");


        data_list.add("a - b= ?");
        data_list.add("a - b - c = ?");
        data_list.add("a - (?) - b = c");
        data_list.add("a - b = c - (?)");

        data_list.add("a ? b= ?");
        data_list.add("a ? b ? c = ?");
        data_list.add("a ? (?) ? b = c");
        data_list.add("a ? b = c ? (?)");


        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);



        //  gen_question(num);
        text_answer.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
            public boolean onEditorAction(TextView v,int actionId,KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    main_task();
                    return true;
                }
                return false;
            }
        });

        button_go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edit_max_num.getText()) || TextUtils.isEmpty(edit_max_question.getText()) ){
                    Toast.makeText(MainActivity.this,"Please set paremeter!!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    max_num = Integer.parseInt(edit_max_num.getText().toString());
                    max_question = Integer.parseInt(edit_max_question.getText().toString());
                    if(max_question <= 100 ) {
                        num = 1;
                        right_cnt = 0;
                        error_cnt = 0;
                        text_debug.setText("");
                        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
                        timer.start();
                        gen_question(num);
                        text_total.setText(String.format("Total Num:%d", max_question));
                        text_right.setText(String.format("Rigth Num:%d", right_cnt));
                        text_error.setText(String.format("Error Num:%d", error_cnt));
                        text_result.setText("");
                    }else{
                        Toast.makeText(MainActivity.this,"Please set max_question <= 100!!!",Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    main_task();
                }
            }
        });
    }
    private void main_task()
    {
        EditText edit_max_question = (EditText) findViewById(R.id.question_num);
        EditText text_answer = (EditText) findViewById(R.id.answer);
        TextView text_debug = (TextView) findViewById(R.id.debug);
        TextView text_question = (TextView) findViewById(R.id.question);
        TextView text_result = (TextView) findViewById(R.id.result);
        TextView text_total = (TextView) findViewById(R.id.total);
        TextView text_right = (TextView) findViewById(R.id.right);
        TextView text_error = (TextView) findViewById(R.id.error);
        Chronometer timer = (Chronometer) findViewById(R.id.timer);
        String get_char;
        String log;
        if(num == 0){
            Toast.makeText(MainActivity.this,"Please set paremeter!!!",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(text_answer.getText())){
            Toast.makeText(MainActivity.this,"do this",Toast.LENGTH_SHORT).show();

        }
        else{
            get_char = text_answer.getText().toString();
            c = Integer.parseInt(get_char);
            if (c != a + b) {
                log = format_num(num,a,b,c);
                SpannableStringBuilder style = new SpannableStringBuilder(log);
                style.setSpan(new ForegroundColorSpan(Color.RED),0,log.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if(num <= max_question) {
                    text_debug.append(style);
                    error_cnt++;
                }
                else{
                    text_question.setText("");
                }

            } else {
                log = format_num(num,a,b,c);
                if(num <= max_question) {
                    text_debug.append(log);
                    right_cnt++;
                }
                else{
                    text_question.setText("");
                }

            }
            if(num >= max_question){
                num++;
                text_result.setText(String.format("      You scort: %d",100 * right_cnt / max_question));
                text_question.setText("");
                text_answer.setText("");
                timer.stop();
            }
            else {
                num++;
                gen_question(num);
            }
            text_total.setText(String.format("Total Num:%d", max_question));
            text_right.setText(String.format("Rigth Num:%d", right_cnt));
            text_error.setText(String.format("Error Num:%d", error_cnt));
        }

    }


    private String format_num(int num,int a,int b,int c)
    {
        String  log;

        if(num == 100) log = String.format("%d: ",num);
        else if(num <  10)  log = String.format("    %d: ",num);
        else log = String.format("  %d: ",num);

        if(a == 100) log += String.format("%d + ",a);
        else if(a <  10)  log += String.format("  %d + ",a);
        else log += String.format("  %d + " ,a);

        if(b == 100) log += String.format("%d = ",b);
        else if(b <  10)  log += String.format("  %d = ",b);
        else log += String.format("  %d =" ,b);

        if(c == 100) log += String.format("%d  ",c);
        else if(c <  10)  log += String.format("  %d  ",c);
        else log += String.format("  %d " ,c);
        if(num % 4 == 0) log += "\n";
        return log;
    }

    private void  gen_question(int i){
        String question_text;
        EditText text_answer = (EditText) findViewById(R.id.answer);
        TextView text_question = (TextView) findViewById(R.id.question);
        //if(mode == 1) f1= "+";
        Random random1 = new Random();
        do {
            a = random1.nextInt(max_num);
            b = random1.nextInt(max_num);

        } while (a + b > max_num || a < 0 || b < 0);
        question_text = String.format("%2d:  %d   %s   %d   =",num,a,f1==1?"+":"-",b);
        text_question.setText(question_text);
        text_answer.setText("");

    }

}
