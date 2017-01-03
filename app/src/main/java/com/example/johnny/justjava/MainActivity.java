package com.example.johnny.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view){
        if (quantity>1) {
            quantity--;
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        boolean hasChocolate= chocolateCheckBox.isChecked();
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        createOrderSummary(price, hasWhippedCream, hasChocolate, name);
    }


    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int basePrice=5;
        if (hasWhippedCream){
            basePrice++;
        }
        if(hasChocolate){
            basePrice+=2;
        }
        return quantity*basePrice;
    }

    /**
     * creates summary of order
     *
     * @param price, total price of order
     * @param hasWhippedCream if it has whipped cream
     * @return text summary
     */
    private void createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String priceMessage= "Name: " + name;
        priceMessage += "\nAdd whipped cream? "+ hasWhippedCream;
        priceMessage += "\nAdd chocolate? "+ hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage=priceMessage+"\n Thank You!";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java oder for "+ name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Nothing",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
