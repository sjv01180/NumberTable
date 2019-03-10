package com.example.numbertable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.InputMismatchException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buildTable(View view) {
        float tableFont = getResources().getDimension(R.dimen.display_font);
        int rightPadding = (int) (getResources().getDimension(R.dimen.padding) / getResources().getDisplayMetrics().density);
        int rightPaddingTable = (int) (getResources().getDimension(R.dimen.padding_body) / getResources().getDisplayMetrics().density);

        //get min and max values from EditText
        EditText max = findViewById(R.id.input_max);
        EditText min = findViewById(R.id.input_min);

        //constructs checkbox values via ID
        CheckBox check_square = findViewById(R.id.n_squared);
        CheckBox check_cubed = findViewById(R.id.n_cubed);
        CheckBox check_root = findViewById(R.id.n_root);
        CheckBox check_fact = findViewById(R.id.n_factorial);

        //checks min and max values if they are properly set
        int maxI, minI;
        try {
            maxI = Integer.parseInt(max.getText().toString());
            minI = Integer.parseInt(min.getText().toString());

        } catch (Exception e) {
            maxI = 0;
            minI = 0;
        }

        //prints toast if min is greater than max
        if(minI > maxI) {
            Toast.makeText(this, "Min should not be greater than Max. Try again!", Toast.LENGTH_SHORT).show();
            return;
        }

        //build table
        TableLayout table = findViewById(R.id.table);
        table.removeAllViews();

        //build header row
        TableRow header = new TableRow(this);

        TextView nHeader = setTextView(getString(R.string.label_n), tableFont, rightPadding);
        TextView squareHead = setTextView(getString(R.string.label_nsquared), tableFont, rightPadding);
        TextView cubeHead = setTextView(getString(R.string.label_ncubed), tableFont, rightPadding);
        TextView rootHead = setTextView(getString(R.string.label_nroot), tableFont, rightPadding);
        TextView factHead = setTextView(getString(R.string.label_nfactorial), tableFont, rightPadding);

        header.addView(nHeader);
        checkCheckbox(header, check_square, squareHead);
        checkCheckbox(header, check_cubed, cubeHead);
        checkCheckbox(header, check_root, rootHead);
        checkCheckbox(header, check_fact, factHead);

        table.addView(header);

        //builds body row
        for(int i = minI; i <= maxI; i++) {
            int iSquared = i * i;
            int iCubed = iSquared * i;
            int iFact = factorial(i);

            TableRow row = new TableRow(this);
            TextView n = setTextView(String.format(Locale.US, "%d", i), tableFont, rightPaddingTable);
            TextView newSquared = setTextView(String.format(Locale.US, "%d", iSquared), tableFont, rightPaddingTable);
            TextView newCubed = setTextView(String.format(Locale.US, "%d", iCubed), tableFont, rightPaddingTable);
            TextView newRoot = setTextView(String.format(Locale.US, "%f", Math.sqrt((double) i)), tableFont, rightPaddingTable);
            TextView newFrac = setTextView(String.format(Locale.US, "%d", iFact), tableFont, rightPaddingTable);

            row.addView(n);
            checkCheckbox(row, check_fact, newSquared);
            checkCheckbox(row, check_fact, newCubed);
            checkCheckbox(row, check_fact, newRoot);
            checkCheckbox(row, check_fact, newFrac);
            table.addView(row);
        }
        //profit!!!
    }

    /**
     * Modifies an inserted TextValue based on text, text size and right padding
     * @param text the text that will be inserted into TextView
     * @param font the floating textSize value for TextView
     * @param paddingRight the padding value for the right side of TextView
     * @return a fully fledged TextView for a table row to use
     */
    private TextView setTextView(String text, float font, int paddingRight) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(TypedValue.COMPLEX_UNIT_PX, font);
        t.setPadding(0, 0, paddingRight, 0);
        return t;
    }

    /**
     * Adds text views to table row depending on checkbox values
     * @param r TableRow that will be manipulated
     * @param c the checkbox that will be examined for manipulation
     * @param t the text view that will be added onto r
     */
    private void checkCheckbox(TableRow r, CheckBox c, TextView t) {
        if(c.isChecked()) { r.addView(t); }
    }

    /**
     * Finds the factorial of a given number
     * @param number the number behind the factorial symbol
     * @return factorial of number
     */
    private int factorial(int number) {
        int n = 1;
        for(int i = 1; i <= number; i++) {
            n = n * i;
        }
        return n;
    }
}
