package com.app.agnie.owl.Util;

import android.os.Parcel;
import android.os.Parcelable;

public class Lesson implements Parcelable {

    public static final Parcelable.Creator<Lesson> CREATOR = new Parcelable.Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel source) {
            return new Lesson(source);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };
    private int id;
    private String language;
    private String translation;
    private String content;
    private String caption;
    private String subtitle;
    private int category;

    public Lesson(int id, String language, String translation, String content, String caption, String subtitle) {
        this.id = id;
        this.language = language;
        this.translation = translation;
        this.content = content;
        this.caption = caption;
        this.subtitle = subtitle;
    }

    public Lesson() {
    }

    protected Lesson(Parcel in) {
        this.id = in.readInt();
        this.language = in.readString();
        this.translation = in.readString();
        this.content = in.readString();
        this.caption = in.readString();
        this.subtitle = in.readString();
        this.category = in.readInt();
    }

    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.language);
        dest.writeString(this.translation);
        dest.writeString(this.content);
        dest.writeString(this.caption);
        dest.writeString(this.subtitle);
        dest.writeInt(this.category);
    }
}
