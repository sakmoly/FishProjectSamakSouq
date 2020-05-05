package qa.happytots.yameenhome.model.delivery;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class DeliverySlot implements Bridge {
    String today;
    String tomorrow;
    String dayAfterTomorrow;
    String fourthDay;
    String fifthDay;
    int selectedSlot = -1;
    int selectedDate = -1;

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public int getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(int selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(String tomorrow) {
        this.tomorrow = tomorrow;
    }

    public String getDayAfterTomorrow() {
        return dayAfterTomorrow;
    }

    public void setDayAfterTomorrow(String dayAfterTomorrow) {
        this.dayAfterTomorrow = dayAfterTomorrow;
    }

    public String getFourthDay() {
        return fourthDay;
    }

    public void setFourthDay(String fourthDay) {
        this.fourthDay = fourthDay;
    }

    public String getFifthDay() {
        return fifthDay;
    }

    public void setFifthDay(String fifthDay) {
        this.fifthDay = fifthDay;
    }
}
