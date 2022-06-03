package com.example.wombatworkshop_dreamcatcher;


import android.os.Parcel;
import android.os.Parcelable;

public class BucketItem implements Parcelable {
    private String itemName;
    private String month;
    private String day;
    private String year;
    private String description;
    private Boolean isCompleted;
    private String item_id;
    private int priority;

    public BucketItem(String itemName, String month, String day, String year, String description, Boolean isCompleted, String item_id, int priority) {
        this.itemName = itemName;
        this.month = month;
        this.day = day;
        this.year = year;
        this.description = description;
        this.isCompleted = isCompleted;
        this.item_id = item_id;
        this.priority = priority;
    }

    public BucketItem() {
        this.itemName = "";
        this.month = "";
        this.day = "";
        this.year = "";
        this.description = "";
        this.isCompleted = Boolean.FALSE;
        this.item_id = "";
        this.priority = 0;
    }

    protected BucketItem(Parcel in) {
        itemName = in.readString();
        month = in.readString();
        day = in.readString();
        year = in.readString();
        description = in.readString();
        byte tmpIsCompleted = in.readByte();
        isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
        item_id = in.readString();
        priority = in.readInt();
    }

    public static final Creator<BucketItem> CREATOR = new Creator<BucketItem>() {
        @Override
        public BucketItem createFromParcel(Parcel in) {
            return new BucketItem(in);
        }

        @Override
        public BucketItem[] newArray(int size) {
            return new BucketItem[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean completed) {
        this.isCompleted = completed;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "BucketItem{" +
                "itemName='" + itemName + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", year='" + year + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                ", item_id=" + item_id +
                ", priority=" + priority +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeString(month);
        parcel.writeString(day);
        parcel.writeString(year);
        parcel.writeString(description);
        parcel.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
        parcel.writeString(item_id);
        parcel.writeInt(priority);
    }

    public boolean sameID(BucketItem item){
        if(item_id.equals(item.getItem_id())){
            return true;
        }else{
            return false;
        }
    }

    public String getDate() {
        return month + '/' + day + '/' + year;
    }
}
