package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText operand1EditText;
    TextView resultTextView;

    Spinner spinnerSourceUnit;
    Spinner spinnerDestinationUnit;
    Spinner spinnerUnitType;

    HashMap<String, Double> lengthConv;
    HashMap<String, Double> weightConv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lengthConv = new HashMap<>();

        lengthConv.put("Inches", 2.54);
        lengthConv.put("Feet", 30.48);
        lengthConv.put("Yards", 91.44);
        lengthConv.put("Miles", 160934.00);
        lengthConv.put("Centimeters", 1.00);
        lengthConv.put("Meters", 100.00);
        lengthConv.put("Kilometers", 100000.00);

        weightConv = new HashMap<>();

        weightConv.put("Grams", 1.00);
        weightConv.put("Kilos", 1000.00);
        weightConv.put("Ounces", 28.3495);
        weightConv.put("Pounds", 453.592);
        weightConv.put("Tons", 907185000.00);

        operand1EditText = findViewById(R.id.operand1EditText);
        resultTextView = findViewById(R.id.resultTextView);
        spinnerUnitType = findViewById(R.id.spinnerUnitType);
        spinnerSourceUnit = findViewById(R.id.spinnerSourceUnit);
        spinnerDestinationUnit = findViewById(R.id.spinnerDestinationUnit);

        spinnerUnitType.setOnItemSelectedListener(this);

        String[] typeArray = getResources().getStringArray(R.array.typeArray);
        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnitType.setAdapter(typeAdapter);

    }

    public void convertClick(View view) {

        String type = spinnerUnitType.getSelectedItem().toString();
        String source = spinnerSourceUnit.getSelectedItem().toString();
        String destination = spinnerDestinationUnit.getSelectedItem().toString();
        double input = Double.parseDouble(operand1EditText.getText().toString());
        double result = 0;



        if (type.equals("Length")){
            double cm = input * lengthConv.get(source);
            result = cm / lengthConv.get(destination);

        } else if (type.equals("Weight")){
            double gram = input * weightConv.get(source);
            result = gram / weightConv.get(destination);
        } else {
            if (source.equals("Celcius")){
                if (destination.equals("Fahrenheit")){
                    result = (input * 1.8) + 32;
                } else if (destination.equals("Kelvin")){
                    result = input + 273.15;
                } else {
                    result = input;
                }
            } else if(source.equals("Fahrenheit")){
                if (destination.equals("Celcius")){
                    result = (input - 32) / 1.8;
                } else if (destination.equals("Kelvin")){
                    result = (input - 32) * 5/9 + 273.15;
                } else {
                    result = input;
                }
            } else {
                if (destination.equals("Celcius")){
                    result = input - 273.15;
                } else if (destination.equals("Fahrenheit")){
                    result = (input - 273.15) * 9/5 + 32;
                } else {
                    result = input;
                }
            }
        }

        resultTextView.setText(Double.toString(result));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinnerUnitType){
            String valueFromSpinner = adapterView.getItemAtPosition(i).toString();

            String[] unitArray;

            if (valueFromSpinner.equals("Length")){
                unitArray = getResources().getStringArray(R.array.lengthArray);
            } else if (valueFromSpinner.equals("Weight")){
                unitArray = getResources().getStringArray(R.array.weightArray);
            } else {
                unitArray = getResources().getStringArray(R.array.tempArray);
            }

            ArrayAdapter unitAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, unitArray);
            unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSourceUnit.setAdapter(unitAdapter);
            spinnerDestinationUnit.setAdapter(unitAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}