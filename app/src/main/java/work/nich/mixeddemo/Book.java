package work.nich.mixeddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
    public String name;
    public String id;

    public Book(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }
}
