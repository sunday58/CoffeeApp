package com.example.andriod.coffeeapp;


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

    int numberOfCoffees = 2;



    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        // you cant order above 100
        if (numberOfCoffees == 100){
            // show an error message as toast
            Toast.makeText(this, "you cant order above 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        numberOfCoffees = numberOfCoffees + 1;
        display (numberOfCoffees);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        // you cant order below 1 cup
        if (numberOfCoffees == 1){
            //show an error as toast
            Toast.makeText(this, "you cant order below 1 cup", Toast.LENGTH_SHORT).show();
            return;
        }

        numberOfCoffees = numberOfCoffees - 1;
        display (numberOfCoffees);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // used for whipped cream
        CheckBox whippedCream = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();

        // used for chocolate
       CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
       boolean hasChocolate = chocolate.isChecked();

       // used to add customers name
        EditText name = findViewById(R.id.edit_text);
        String nameOfPerson = name.getText().toString();

        // updates price and name base on entered text and selections
        int price =   calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, nameOfPerson );

        // sends the coffee demand to an email only
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + nameOfPerson);
        intent.putExtra(Intent.EXTRA_TEXT, "Location: \n" +  priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }



    }


    /**
     * calculates the price of the order
     * @return total price
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        // price of 1 cup of coffee
        int basePrice = 5;

        //add $1 if the customer wants whipped cream
        if (hasWhippedCream){
            basePrice = basePrice + 1;
        }

        //add $2 if the customer wants chocolate
        if (hasChocolate){
         basePrice = basePrice + 2;
        }


        return numberOfCoffees * basePrice;
    }

    /**
     * @param addWhippedCream for whippedcream
     * @param hasChocolate add chocolate
     * @param nameOfPerson add customers name
     * @return summary for coffee order
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean hasChocolate, String nameOfPerson){
        String word = "name: " + nameOfPerson + "\nAdd Whipped cream? " + addWhippedCream ;
        word += "\nAdd Chocolate? " + hasChocolate;
        word += "\nquantity: " + numberOfCoffees;
         word += "\nTotal: " + "$" + price + "\nThank You!";
        return word;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number ) {
        TextView quantityTextView = (TextView)
                findViewById(R.id.quantity_text_view);
        quantityTextView.setText( String.valueOf(number));

    }




}
