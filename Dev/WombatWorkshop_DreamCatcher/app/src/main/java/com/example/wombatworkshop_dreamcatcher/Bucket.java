package com.example.wombatworkshop_dreamcatcher;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Bucket implements Parcelable {
    private String bucket_pic_num;
    private String bucket_name;
    private String bucket_creator;
    private String bucket_privacy;
    private String bucket_id;
    private ArrayList sharedWith;
    private ArrayList items;

    public Bucket(){

    }

    public Bucket(String creator, ArrayList otherUsers, ArrayList itemIDs){
        this.bucket_creator = creator;
        this.sharedWith = otherUsers;
        this.items = itemIDs;
    }

    public Bucket(String pic_num, String name, String privacy, String id){
        this.bucket_pic_num = pic_num;
        this.bucket_name = name;
        this.bucket_privacy = privacy;
        this.bucket_id = id;
    }

    protected Bucket(Parcel in) {
        bucket_pic_num = in.readString();
        bucket_name = in.readString();
        bucket_creator = in.readString();
        bucket_privacy = in.readString();
        bucket_id = in.readString();
    }

    public static final Creator<Bucket> CREATOR = new Creator<Bucket>() {
        @Override
        public Bucket createFromParcel(Parcel in) {
            return new Bucket(in);
        }

        @Override
        public Bucket[] newArray(int size) {
            return new Bucket[size];
        }
    };

    @Override
    public String toString() {
        return "Bucket{" +
                "bucket_pic_num='" + bucket_pic_num + '\'' +
                ", bucket_name='" + bucket_name + '\'' +
                ", bucket_creator='" + bucket_creator + '\'' +
                ", bucket_privacy='" + bucket_privacy + '\'' +
                ", bucket_id='" + bucket_id + '\'' +
                ", sharedWith=" + sharedWith +
                ", itemIDs=" + items +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bucket_pic_num);
        parcel.writeString(bucket_name);
        parcel.writeString(bucket_creator);
        parcel.writeString(bucket_privacy);
        parcel.writeString(bucket_id);
    }

    public String getBucket_pic_num() {
        return bucket_pic_num;
    }

    public void setBucket_pic_num(String bucket_pic_num) {
        this.bucket_pic_num = bucket_pic_num;
    }

    public String getBucket_name() {
        return bucket_name;
    }

    public void setBucket_name(String bucket_name) {
        this.bucket_name = bucket_name;
    }

    public String getBucket_creator() {
        return bucket_creator;
    }

    public void setBucket_creator(String bucket_creator) {
        this.bucket_creator = bucket_creator;
    }

    public String getBucket_privacy() {
        return bucket_privacy;
    }

    public void setBucket_privacy(String bucket_privacy) {
        this.bucket_privacy = bucket_privacy;
    }

    public String getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(String bucket_id) {
        this.bucket_id = bucket_id;
    }

    public ArrayList getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(ArrayList sharedWith) {
        this.sharedWith = sharedWith;
    }

    public ArrayList getItems() {
        return items;
    }

    public void setItems(ArrayList items) {
        this.items = items;
    }
}
