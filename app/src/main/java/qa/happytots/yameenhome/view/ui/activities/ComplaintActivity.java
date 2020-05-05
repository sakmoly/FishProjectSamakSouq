package qa.happytots.yameenhome.view.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.contact.ContactRequest;
import qa.happytots.yameenhome.model.user.Data;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class ComplaintActivity extends BaseActivity {

    private AppCompatEditText edtName;
    private AppCompatEditText edtEmail;
    private AppCompatEditText edtComplaint;
    private AppCompatTextView btnSubmit;

    private Data mUserDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        Toolbar toolbar = findViewById(R.id.toolbar_complaint);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtName = findViewById(R.id.edt_complaint_name);
        edtEmail = findViewById(R.id.edt_complaint_email);
        edtComplaint = findViewById(R.id.edt_complaint);
        btnSubmit = findViewById(R.id.btn_submit);

        mUserDetail = getIntent().getParcelableExtra(Support.BUNDLE_USER_DETAILS);
        if (mUserDetail != null) {
            setUserDetails();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUserDetails() {
        String username = mUserDetail.getFirstname() + " " + mUserDetail.getLastname();
        edtName.setText(username);
        edtEmail.setText(mUserDetail.getEmail());
    }

    private void validate() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String complaint = edtComplaint.getText().toString();

        if (TextUtils.isEmpty(name)) {
            edtName.setError("Field can not be empty");
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Field can not be empty");
        } else if (TextUtils.isEmpty(complaint)) {
            edtComplaint.setError("Field can not be empty");
        }else if (complaint.length() < 10) {
            edtComplaint.setError("Enquiry must be between 10 and 3000 characters!");
        } else {
            ContactRequest request = new ContactRequest();
            request.setName(name);
            request.setEmail(email);
            request.setEnquiry(complaint);

            callApiForComplaintRegister(request);
        }
    }

    private void callApiForComplaintRegister(ContactRequest request) {
        UtilityClass.showProgressDialog(ComplaintActivity.this);
        DatabaseHandler handler = new DatabaseHandler(ComplaintActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForComplaintRegister(handler.getSessionValue(), request);
    }

    @Override
    public void successResponse(Response response) {
        CommonResponse response1 = (CommonResponse) response.body;
        if (response1.getSuccess() == Constants.SUCCESS_RESPONSE) {
            Toast.makeText(ComplaintActivity.this, R.string.label_message_complaint_register_success, Toast.LENGTH_SHORT).show();
            ComplaintActivity.this.finish();
        } else {
            Toast.makeText(ComplaintActivity.this, R.string.label_message_complaint_register_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failureResponse(Response response) {
        super.failureResponse(response);
        Toast.makeText(ComplaintActivity.this, R.string.label_message_complaint_register_failed, Toast.LENGTH_SHORT).show();
    }
}
