package com.bluemoonl.iphonecaculator;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class CaculatorActivity extends AppCompatActivity {

    TextView TextView_result;


    double resultNumber = 0;
    double inputNumber = 0;
    double currentNumber = 0;
    String operator = "=";
    String lastoperator = "=";
    String changeNumber;
    boolean isFirstInput = true;
    boolean isOperatorClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        TextView_result = findViewById(R.id.TextView_result);

        TextView_result.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isFirstInput) {
                            String getResultText = TextView_result.getText().toString();
                            if (getResultText.length() > 1) {
                                String subString = getResultText.substring(0, getResultText.length() - 1);
                                TextView_result.setText(subString);
                            } else {
                                TextView_result.setText("0");
                                isFirstInput = true;
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void ClearButtonClick(View v) {

        TextView_result.setText("0");
        resultNumber = 0;
        inputNumber = 0;
        lastoperator = operator;
        operator = "=";
        isFirstInput = true;
        isOperatorClick = false;
    }

    public void PlusMinusButtonClick(View v) {

        currentNumber = Double.parseDouble(TextView_result.getText().toString());
        currentNumber = calculator(currentNumber, -1, "×");

        TextView_result.setText(String.valueOf(currentNumber));
    }

    public void PercentButtonClick(View v) {

        currentNumber = Double.parseDouble(TextView_result.getText().toString());
        currentNumber = calculator(currentNumber, 0.01, "×");

        TextView_result.setText(String.valueOf(currentNumber));
    }

    public void PointButtonClick(View v) {
        if(isFirstInput) {
            if(operator.equals("=")) {
                operator = v.getTag().toString();
                resultNumber = Double.parseDouble(TextView_result.getText().toString());
            }
            TextView_result.setText("0" + v.getTag().toString());
            isFirstInput = false;
        }
        else {
            if(TextView_result.getText().toString().contains(".")) {
            }
            else {
                TextView_result.append(v.getTag().toString());
            }
        }
    }

    public void numButtonClick(View v) {

        String getButtonText = v.getTag().toString();
        changeNumber = TextView_result.getText().toString();
//            TextView_result.setText(formatDF(Double.parseDouble(changeNumber)));

        if (changeNumber.length() < 9) {
            NumPush(v);
        } else {
            if (isOperatorClick) {
                isFirstInput = true;
                NumPush(v);
            } else {
                TextView_result.setText(changeNumber);
            }
        }
    }

    public void operatorButtonClick(View v){

        isOperatorClick = true;

        if (isFirstInput) {
            operator = v.getTag().toString();
        } else {
            inputNumber = Double.parseDouble(TextView_result.getText().toString());
            lastoperator = operator;

            resultNumber = calculator(resultNumber, inputNumber, operator);

//            TextView_result.setText(String.valueOf(resultNumber));
            isFirstInput = true;
            operator = v.getTag().toString();
        }
    }

    public void equalsButtonClick(View v) {


        if (operator.equals("÷") && currentNumber == 0) {
            TextView_result.setText("오류");
        } else if (isFirstInput) {
            currentNumber = Double.parseDouble(TextView_result.getText().toString());
            if (isOperatorClick) {
                resultNumber = calculator(resultNumber, inputNumber, lastoperator);
                TextView_result.setText(String.valueOf(resultNumber));
            }
        } else if (!isOperatorClick && operator.equals("=")) {
            TextView_result.setText("오류");
            isFirstInput = true;
        } else {
            inputNumber = Double.parseDouble(TextView_result.getText().toString());
            resultNumber = calculator(resultNumber, inputNumber, operator);
            lastoperator = operator;

            TextView_result.setText(String.valueOf(resultNumber));
            isFirstInput = true;
            operator = v.getTag().toString();
        }
    }

    public double calculator(double resultNumber, double inputnumber, String operator) {
        switch (operator) {
            case "=":
                resultNumber = inputnumber;
                break;
            case "+":
                resultNumber = resultNumber + inputnumber;
                break;
            case "−":
                resultNumber = resultNumber - inputnumber;
                break;
            case "×":
                resultNumber = resultNumber * inputnumber;
                break;
            case "÷":
                resultNumber = resultNumber / inputnumber;
                break;
        }
        return resultNumber;
    }

    public void NumPush(View v) {
        String getButtonText = v.getTag().toString();
        isOperatorClick = false;

        if (isFirstInput && TextView_result.getText().toString() != "0") {
            TextView_result.setText(getButtonText);
            isFirstInput = false;
            if (operator.equals("=")) {
                isOperatorClick = false;
            }
        } else {
            if (TextView_result.getText().toString().equals("0")) {
                TextView_result.setText("0");
                isFirstInput = true;
            }
            else {
                TextView_result.append(getButtonText);
            }
        }
    }
}
