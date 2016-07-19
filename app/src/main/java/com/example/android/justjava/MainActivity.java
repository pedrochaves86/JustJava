package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void increment(View view) {

        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 coffees!", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = ++quantity;
        displayQuantity(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than 1 coffee!", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = --quantity;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameText = (EditText) findViewById(R.id.name_text);
        String hasName = nameText.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "pedrochaves86@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, hasName));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCream, hasChocolate, hasName));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if (hasWhippedCream && hasChocolate) {
            basePrice += 3;
        } else if (hasChocolate) {
            basePrice += 2;
        } else if (hasWhippedCream) {
            basePrice += 1;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String hasName) {
        return getString(R.string.email_name, hasName) +
                "\n" + getString(R.string.add_whipped_cream, addWhippedCream) +
                "\n" + getString(R.string.add_chocolate, addChocolate) +
                "\n" + getString(R.string.email_quantity, quantity) +
                "\n" + getString(R.string.email_price, NumberFormat.getCurrencyInstance().format(price)) +
                "\n" + getString(R.string.thank_you);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.justjava/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.justjava/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}