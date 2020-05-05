package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.YameenApplication;
import qa.happytots.yameenhome.components.SlotLayout;
import qa.happytots.yameenhome.model.delivery.DeliveryHeader;
import qa.happytots.yameenhome.model.delivery.DeliverySlot;
import qa.happytots.yameenhome.model.payment.methods.response.Cod;
import qa.happytots.yameenhome.model.payment.methods.response.Telr;

public class SlotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SLOT = 1;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_PAYMENT = 3;

    private List<Bridge> mAddresses;
    private OnSlotInteractionListener mlistener;

    public SlotAdapter(List<Bridge> mAddress, OnSlotInteractionListener mlistener) {
        this.mAddresses = mAddress;
        this.mlistener = mlistener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.single_slot_header, parent, false);
            return new DeliveryHeaderViewHolder(view);
        } else if (viewType == TYPE_PAYMENT) {
            View view = inflater.inflate(R.layout.single_slop_payment_option, parent, false);
            return new DeliveryPaymentOptionViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.single_slot, parent, false);
            return new DeliverySlotViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof DeliveryHeaderViewHolder) {
            DeliveryHeaderViewHolder holder = (DeliveryHeaderViewHolder) viewHolder;
            DeliveryHeader header = (DeliveryHeader) mAddresses.get(position);
            holder.header.setText(header.getHeader());
        } else if (viewHolder instanceof DeliveryPaymentOptionViewHolder) {
            DeliveryPaymentOptionViewHolder holder = (DeliveryPaymentOptionViewHolder) viewHolder;
            if (mAddresses.get(position) instanceof Cod) {
                Cod cod = (Cod) mAddresses.get(position);
                if (!cod.isSelected()) {
                    holder.option.setChecked(false);
                }
                holder.option.setText(cod.getTitle());
            } else {
                Telr telr = (Telr) mAddresses.get(position);
                if (!telr.isSelected()) {
                    holder.option.setChecked(false);
                }
                holder.option.setText(telr.getTitle());
            }
        } else if (viewHolder instanceof DeliverySlotViewHolder) {
            DeliverySlotViewHolder holder = (DeliverySlotViewHolder) viewHolder;
            DeliverySlot slot = (DeliverySlot) mAddresses.get(position);

            String[] today = slot.getToday().split("-");
            String[] tomorrow = slot.getTomorrow().split("-");
            String[] dayAfterTomorrow = slot.getDayAfterTomorrow().split("-");
            String[] fourthDay = slot.getFourthDay().split("-");
            String[] fifthDay = slot.getFifthDay().split("-");

//            if (slot.getSelectedDate() > 1) {
//                holder.rbMorningSlot.setVisibility(View.VISIBLE);
//                holder.rbEveningSlot.setVisibility(View.VISIBLE);
//            } else {
//                holder.rbMorningSlot.setVisibility(View.GONE);
//                holder.rbEveningSlot.setVisibility(View.GONE);
//            }
            holder.rbMorningSlot.setVisibility(View.VISIBLE);
            holder.rbEveningSlot.setVisibility(View.VISIBLE);

            holder.slFirst.setVisibility(View.GONE);

            holder.slSecond.setWeekDay(tomorrow[0]);
            holder.slSecond.setMonthDay(Integer.parseInt(tomorrow[1]));
            holder.slSecond.setMonth(tomorrow[2]);

            holder.slThird.setWeekDay(dayAfterTomorrow[0]);
            holder.slThird.setMonthDay(Integer.parseInt(dayAfterTomorrow[1]));
            holder.slThird.setMonth(dayAfterTomorrow[2]);

            holder.slFourth.setWeekDay(fourthDay[0]);
            holder.slFourth.setMonthDay(Integer.parseInt(fourthDay[1]));
            holder.slFourth.setMonth(fourthDay[2]);

            holder.slFifth.setWeekDay(fifthDay[0]);
            holder.slFifth.setMonthDay(Integer.parseInt(fifthDay[1]));
            holder.slFifth.setMonth(fifthDay[2]);

            holder.slFirst.setSelected(false);
            holder.slSecond.setSelected(false);
            holder.slThird.setSelected(false);
            holder.slFourth.setSelected(false);
            holder.slFifth.setSelected(false);

            if (slot.getSelectedDate() == 1) {
                holder.slFirst.setSelected(true);
            } else if (slot.getSelectedDate() == 2) {
                holder.slSecond.setSelected(true);
            } else if (slot.getSelectedDate() == 3) {
                holder.slThird.setSelected(true);
            } else if (slot.getSelectedDate() == 4) {
                holder.slFourth.setSelected(true);
            } else if (slot.getSelectedDate() == 5) {
                holder.slFifth.setSelected(true);
            }


        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mAddresses.get(position) instanceof DeliveryHeader) {
            return TYPE_HEADER;
        } else if (mAddresses.get(position)  instanceof DeliverySlot) {
            return TYPE_SLOT;
        } else {
            return TYPE_PAYMENT;
        }
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class DeliverySlotViewHolder extends RecyclerView.ViewHolder {
        private SlotLayout slFirst;
        private SlotLayout slSecond;
        private SlotLayout slThird;
        private SlotLayout slFourth;
        private SlotLayout slFifth;

        private AppCompatRadioButton rbMorningSlot;
        private AppCompatRadioButton rbEveningSlot;

        DeliverySlotViewHolder(View itemView) {
            super(itemView);
            slFirst = itemView.findViewById(R.id.sl_first);
            slSecond = itemView.findViewById(R.id.sl_second);
            slThird = itemView.findViewById(R.id.sl_third);
            slFourth = itemView.findViewById(R.id.sl_fourth);
            slFifth = itemView.findViewById(R.id.sl_fifth);

            rbMorningSlot = itemView.findViewById(R.id.rb_slot_morning);
            rbEveningSlot = itemView.findViewById(R.id.rb_slot_evening);

            rbMorningSlot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        DeliverySlot slot = (DeliverySlot) mAddresses.get(getLayoutPosition());
                        slot.setSelectedSlot(1);
                    }
                }
            });

            rbEveningSlot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DeliverySlot slot = (DeliverySlot) mAddresses.get(getLayoutPosition());
                    slot.setSelectedSlot(2);
                }
            });

            slFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slFirst.setColorCode(R.color.red);
                    slFirst.setSelected(true);

                    slSecond.setColorCode(R.color.gray);
                    slSecond.setSelected(false);
                    slThird.setColorCode(R.color.gray);
                    slThird.setSelected(false);
                    slFourth.setColorCode(R.color.gray);
                    slFourth.setSelected(false);
                    slFifth.setColorCode(R.color.gray);
                    slFifth.setSelected(false);

                    DeliverySlot slot = (DeliverySlot) mAddresses.get(getLayoutPosition());
                    slot.setSelectedDate(1);

                    if (YameenApplication.isTimeSlotTodayMorning()) {
                        rbEveningSlot.setVisibility(View.GONE);
                        rbMorningSlot.setVisibility(View.GONE);
                    }
                }
            });

            slSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slSecond.setColorCode(R.color.red);
                    slSecond.setSelected(true);

                    slFirst.setColorCode(R.color.gray);
                    slFirst.setSelected(false);
                    slThird.setColorCode(R.color.gray);
                    slThird.setSelected(false);
                    slFourth.setColorCode(R.color.gray);
                    slFourth.setSelected(false);
                    slFifth.setColorCode(R.color.gray);
                    slFifth.setSelected(false);

                    DeliverySlot slot = (DeliverySlot) mAddresses.get(getLayoutPosition());
                    slot.setSelectedDate(2);

                    rbEveningSlot.setVisibility(View.VISIBLE);
                    rbMorningSlot.setVisibility(View.VISIBLE);
                }
            });

            slThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slThird.setColorCode(R.color.red);
                    slThird.setSelected(true);

                    slFirst.setColorCode(R.color.gray);
                    slFirst.setSelected(false);
                    slSecond.setColorCode(R.color.gray);
                    slSecond.setSelected(false);
                    slFourth.setColorCode(R.color.gray);
                    slFourth.setSelected(false);
                    slFifth.setColorCode(R.color.gray);
                    slFifth.setSelected(false);

                    DeliverySlot slot = (DeliverySlot) mAddresses.get(getLayoutPosition());
                    slot.setSelectedDate(3);

                    rbEveningSlot.setVisibility(View.VISIBLE);
                    rbMorningSlot.setVisibility(View.VISIBLE);
                }
            });

            slFourth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slFourth.setColorCode(R.color.red);
                    slFourth.setSelected(true);

                    slFirst.setColorCode(R.color.gray);
                    slFirst.setSelected(false);
                    slThird.setColorCode(R.color.gray);
                    slThird.setSelected(false);
                    slSecond.setColorCode(R.color.gray);
                    slSecond.setSelected(false);
                    slFifth.setColorCode(R.color.gray);
                    slFifth.setSelected(false);

                    DeliverySlot slot = (DeliverySlot) mAddresses.get(getLayoutPosition());
                    slot.setSelectedDate(4);

                    rbEveningSlot.setVisibility(View.VISIBLE);
                    rbMorningSlot.setVisibility(View.VISIBLE);
                }
            });

            slFifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slFifth.setColorCode(R.color.red);
                    slFifth.setSelected(true);

                    slFirst.setColorCode(R.color.gray);
                    slFirst.setSelected(false);
                    slThird.setColorCode(R.color.gray);
                    slThird.setSelected(false);
                    slFourth.setColorCode(R.color.gray);
                    slFourth.setSelected(false);
                    slSecond.setColorCode(R.color.gray);
                    slSecond.setSelected(false);

                    DeliverySlot slot = (DeliverySlot) mAddresses.get(getLayoutPosition());
                    slot.setSelectedDate(5);

                    rbEveningSlot.setVisibility(View.VISIBLE);
                    rbMorningSlot.setVisibility(View.VISIBLE);
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
                    if (isChecked) {
                        updatePaymentMethods(mAddresses.get(getLayoutPosition()));
                        mlistener.onPaymentSelect(getLayoutPosition(), mAddresses.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnSlotInteractionListener {
        void onSlotSelect(int position, int selectedDate, DeliverySlot slot);
        void onPaymentSelect(int position, Bridge method);
    }


    private void updatePaymentMethods(Bridge paymentMethod) {
        for (Bridge bridge : mAddresses) {
            if (bridge instanceof Cod) {
                Cod cod = (Cod) bridge;
                if (bridge == paymentMethod) {
                    cod.setSelected(true);
                } else {
                    cod.setSelected(false);
                }
            } else if (bridge instanceof Telr) {
                Telr telr = (Telr) bridge;
                if (bridge == paymentMethod) {
                    telr.setSelected(true);
                } else {
                    telr.setSelected(false);
                }
            }
        }

        notifyDataSetChanged();
    }
}
