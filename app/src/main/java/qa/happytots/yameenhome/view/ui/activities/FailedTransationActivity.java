package qa.happytots.yameenhome.view.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.response.status.StatusResponse;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;

public class FailedTransationActivity extends Activity {

    private YameenTextView tvReason;
    private YameenTextView tvTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failedtransaction);

        tvReason = findViewById(R.id.tv_failure_reason);
        tvTryAgain = findViewById(R.id.tv_try_again);

        tvTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Object object = intent.getParcelableExtra(WebviewActivity.PAYMENT_RESPONSE);

        if(object instanceof StatusResponse) {
            StatusResponse status = (StatusResponse) object;

            if (status.getAuth() != null) {
                status.getAuth().getStatus();   // Authorisation status. A indicates an authorised transaction. H also indicates an authorised transaction, but where the transaction has been placed on hold. Any other value indicates that the request could not be processed.
                status.getAuth().getAvs();      /* Result of the AVS check:
                                            Y = AVS matched OK
                                            P = Partial match (for example, post-code only)
                                            N = AVS not matched
                                            X = AVS not checked
                                            E = Error, unable to check AVS */
                status.getAuth().getCode();     // If the transaction was authorised, this contains the authorisation code from the card issuer. Otherwise it contains a code indicating why the transaction could not be processed.
                status.getAuth().getMessage();  // The authorisation or processing error message.
                status.getAuth().getCa_valid();
                status.getAuth().getCardcode(); // Code to indicate the card type used in the transaction. See the code list at the end of the document for a list of card codes.
              //  status.getAuth().getCardlast4();// The last 4 digits of the card number used in the transaction. This is supplied for all payment types (including the Hosted Payment Page method) except for PayPal.
                status.getAuth().getCvv();      /* Result of the CVV check:
                                           Y = CVV matched OK
                                           N = CVV not matched
                                           X = CVV not checked
                                           E = Error, unable to check CVV */
                status.getAuth().getTranref(); //The payment gateway transaction reference allocated to this request.
                status.getAuth().getAvs();     /* Result of the AVS check:
                                           Y = AVS matched OK
                                           P = Partial match (for example, post-code only)
                                           N = AVS not matched
                                           X = AVS not checked
                                           E = Error, unable to check AVS */
                tvReason.setText(status.getAuth().getMessage());
            }
        } else if (object instanceof String){
            String errorMessage = (String) object;
            tvReason.setText(errorMessage);
        }
    }

}
