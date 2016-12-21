package ir.moslem_deris.apps.zarinpal_android_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ir.moslem_deris.apps.zarinpal.PaymentBuilder;
import ir.moslem_deris.apps.zarinpal.ZarinPal;
import ir.moslem_deris.apps.zarinpal.enums.ZarinPalError;
import ir.moslem_deris.apps.zarinpal.listeners.OnPaymentListener;
import ir.moslem_deris.apps.zarinpal.models.Payment;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textview);
    }

    public void onPaymentButtonClicked(View view){
        textView.setText("در حال انتقال به صفحه پرداخت\nلطفا صبر کنید ...");
        pay();
    }

    private void pay(){
        Payment payment = new PaymentBuilder()
                .setMerchantID("4ff2f25a-82c8-45eb-b540-4c5b5ee8aeb5")  //  This is an example, put your own merchantID here.
                .setAmount(1000)                                        //  In Toman
                .setDescription("پرداخت تست پلاگین اندروید")
                .setEmail("moslem.deris@gmail.com")                     //  This field is custom.
                .setMobile("09123456789")                               //  This field is custom.
                .create();

        ZarinPal.pay(this, payment, new OnPaymentListener() {
            @Override
            public void onSuccess(String refID) {
                Toast.makeText(MainActivity.this, refID, Toast.LENGTH_LONG).show();
                Log.wtf("TAG", "Main Activity: " + refID);
                textView.setText("پرداخت با موفقیت انجام شد\nکد پیگیری: " + refID);
            }

            @Override
            public void onFailure(ZarinPalError error) {
                String errorMessage = "";
                switch (error){
                    case INVALID_PAYMENT: errorMessage = "پرداخت تایید نشد";           break;
                    case USER_CANCELED:   errorMessage = "پرداخت توسط کاربر متوقف شد"; break;
                    case NOT_ENOUGH_DATA: errorMessage = "اطلاعات پرداخت کافی نیست";    break;
                    case UNKNOWN:         errorMessage = "خطای ناشناخته";              break;
                }
                Log.wtf("TAG", "Main Activity: " + "ERROR: " + errorMessage);
                textView.setText("خطا!!!" + "\n" + errorMessage);
            }
        });
    }
}
