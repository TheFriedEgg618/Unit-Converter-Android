package com.example.uc;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Spinner converterSpinner, unit1Spinner, unit2Spinner;
    private EditText inputField1, inputField2;
    private TextView formulaText;
    private String[][] unitArrays;
    private int preSelect1 = 0;
    private int preSelect2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        converterSpinner = findViewById(R.id.converter_spinner);
        unit1Spinner = findViewById(R.id.unit1_spinner);
        unit2Spinner = findViewById(R.id.unit2_spinner);
        inputField1 = findViewById(R.id.input1);
        inputField2 = findViewById(R.id.input2);
        formulaText = findViewById(R.id.formula);

        unitArrays = new String[][]{
                getResources().getStringArray(R.array.mass_units),
                getResources().getStringArray(R.array.length_units),
                getResources().getStringArray(R.array.temp_units),
                getResources().getStringArray(R.array.time_units)
        };

        converterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinners(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        unit1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (unit1Spinner.getSelectedItemPosition() == unit2Spinner.getSelectedItemPosition()) {
                    unit1Spinner.setSelection(unit2Spinner.getSelectedItemPosition());
                    unit2Spinner.setSelection(preSelect1);
                    preSelect1 = position;
                }
                convert(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        unit2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (unit1Spinner.getSelectedItemPosition() == unit2Spinner.getSelectedItemPosition()) {
                    unit2Spinner.setSelection(unit1Spinner.getSelectedItemPosition());
                    unit1Spinner.setSelection(preSelect2);
                    preSelect2=position;
                }
                convert(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        inputField1.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
           }

           @Override
           public void afterTextChanged(Editable s) {
               if (inputField1.hasFocus()) {
                   convert(true);
               }
           }
       });

        inputField2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputField2.hasFocus()) {
                    convert(false);
                }
            }
        });
    }


    private void updateUnitSpinners(int index) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitArrays[index]);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit1Spinner.setAdapter(adapter);
        unit2Spinner.setAdapter(adapter);
        unit2Spinner.setSelection(1);
        preSelect1 = 0;
        preSelect2 = 1;
        inputField1.setText("0");
        convert(true);
    }

    private void convert(boolean isInput1) {
        EditText input, output;
        Spinner from, to;
        if (isInput1) {
            input = inputField1;
            output = inputField2;
            from = unit1Spinner;
            to = unit2Spinner;
        } else {
            input = inputField2;
            output = inputField1;
            from = unit2Spinner;
            to = unit1Spinner;
        }
        String inputText = input.getText().toString();
        if (inputText.isEmpty()){
            output.setText("");
            return;
        }
        double outputNum = MathConverter.convert(String.valueOf(from.getSelectedItem()), String.valueOf(to.getSelectedItem()), Double.parseDouble(inputText));
        String formula = MathConverter.getFormula(String.valueOf(from.getSelectedItem()),String.valueOf(to.getSelectedItem()));
        formula = formula.replace("x", String.valueOf(inputField1.getText()));
        output.setText(String.valueOf(outputNum));
        formulaText.setText(formula);
    }

}