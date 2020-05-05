package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.delivery.DeliveryHeader;
import qa.happytots.yameenhome.model.delivery.DeliverySlot;
import qa.happytots.yameenhome.model.fetchaddress.Address;
import qa.happytots.yameenhome.model.payment.methods.response.PaymentMethods;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ADDRESS = 0;
    private static final int TYPE_SLOT = 1;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_PAYMENT = 3;

    public static final int START = 0;
    public static final int CENTER = 1;
    public static final int END = 2;

    private List<Bridge> mAddresses;
    private OnAddressInteractionListener mlistener;

    private boolean isFromMeScreen;

    public DeliveryAddressAdapter(List<Bridge> mAddress, OnAddressInteractionListener mlistener, boolean fromMeScreen) {
        this.mAddresses = mAddress;
        this.mlistener = mlistener;
        isFromMeScreen = fromMeScreen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_ADDRESS) {
            View view = inflater.inflate(R.layout.single_delivery_address, parent, false);
            return new DeliveryAddressViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.single_slot_header, parent, false);
            return new DeliveryHeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.single_slop_payment_option, parent, false);
            return new DeliveryPaymentOptionViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof DeliveryAddressViewHolder) {
            DeliveryAddressViewHolder holder = (DeliveryAddressViewHolder) viewHolder;
            Address address = (Address) mAddresses.get(position);
            holder.tvName.setText(address.getFirstname() + " " + address.getLastname());
            StringBuilder builder = new StringBuilder();
            builder.append(address.getCompany());
            builder.append("\n");
            builder.append(address.getAddress1());
            builder.append(",");
            builder.append(address.getAddress2());
            builder.append("\n");
            builder.append(address.getCity());
            builder.append("\n");
            builder.append(address.getCountry());
            builder.append(",");
            builder.append("PIN - ");
            builder.append(address.getPostcode());
            holder.tvAddress.setText(builder.toString());
            if (!isFromMeScreen) {
                if (!address.isSelected()) {
                    holder.rbDeliverHere.setChecked(false);
                }
                holder.rbDeliverHere.setVisibility(View.VISIBLE);
                holder.tvDelete.setVisibility(View.INVISIBLE);
            } else {
                holder.rbDeliverHere.setVisibility(View.INVISIBLE);
                holder.tvDelete.setVisibility(View.VISIBLE);
            }
        } else if (viewHolder instanceof DeliveryHeaderViewHolder) {
            DeliveryHeaderViewHolder holder = (DeliveryHeaderViewHolder) viewHolder;
            DeliveryHeader header = (DeliveryHeader) mAddresses.get(position);
            holder.header.setText(header.getHeader());
        } else if (viewHolder instanceof DeliveryPaymentOptionViewHolder) {
            DeliveryPaymentOptionViewHolder holder = (DeliveryPaymentOptionViewHolder) viewHolder;
            PaymentMethods option = (PaymentMethods) mAddresses.get(position);
            holder.option.setText(option.getCod().getTitle());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mAddresses.get(position) instanceof DeliveryHeader) {
            return TYPE_HEADER;
        } else if (mAddresses.get(position)  instanceof DeliverySlot) {
            return TYPE_SLOT;
        } else if (mAddresses.get(position)  instanceof PaymentMethods) {
            return TYPE_PAYMENT;
        } else {
            return TYPE_ADDRESS;
        }
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class DeliveryAddressViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvAddress;
        private TextView tvEdit;
        private YameenTextView tvDelete;
        private AppCompatRadioButton rbDeliverHere;

        DeliveryAddressViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_delivery_name);
            tvAddress = itemView.findViewById(R.id.tv_delivery_address);
            tvEdit = itemView.findViewById(R.id.tv_edit_delivery_address);
            rbDeliverHere = itemView.findViewById(R.id.rb_deliver_here);
            tvDelete = itemView.findViewById(R.id.tv_address_delete);

            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.edit( (Address) mAddresses.get(getLayoutPosition()));
                }
            });

            rbDeliverHere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mlistener.addressSelection(getLayoutPosition(), isChecked, (Address) mAddresses.get(getLayoutPosition()));
                }
            });

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.deleteAddress(getLayoutPosition(), (Address) mAddresses.get(getLayoutPosition()));
                }
            });

        }
    }

    class DeliveryHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView header;
        DeliveryHeaderViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.tv_slot_header);
        }
    }

    class DeliveryPaymentOptionViewHolder extends RecyclerView.ViewHolder {
        private AppCompatRadioButton option;
        DeliveryPaymentOptionViewHolder(View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.rb_slot_payment_option);

            option.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updatePaymentMehtods();
                    PaymentMethods paymentMethods = (PaymentMethods) mAddresses.get(getLayoutPosition());
                    paymentMethods.setSelected(true);
                }
            });
        }
    }

    public interface OnAddressInteractionListener {
        void edit(Address address);
        void onSlotSelect(int position, int selectedDate, DeliverySlot slot);
        void addressSelection(int position, boolean isChecked, Address address);
        void deleteAddress(int position, Address address);
    }

    private boolean isTimeSlotTodayMorning() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour > 0 && hour < 12;
    }

    private boolean isTimeSlotTodayEvening() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour >= 12 && hour < 24;
    }

    private void updatePaymentMehtods() {
        for (Bridge bridge : mAddresses) {
            if (bridge instanceof PaymentMethods) {
                PaymentMethods paymentMethods = (PaymentMethods) bridge;
                paymentMethods.setSelected(false);
            }
        }
    }
}
