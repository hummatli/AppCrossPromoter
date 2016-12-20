package com.mobapphome.mahads.types;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.mobapphome.mahads.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Program implements Serializable {
    int id;
    String name;
    String desc;
    String uri;
    String img;
    String release_date;
    String update_date;

    static DateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    static long ONE_MONTTH_MILLI_SEC = 1000L * 60 * 60 * 24 * 30;

    public enum Freshnest {UPDATED, NEW, OLD}

    public Program() {
    }

    public Program(int id, String name, String desc, String uri, String img,
                   String release_date, String update_date) {
        super();
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.uri = uri;
        this.img = img;
        this.release_date = release_date;
        this.update_date = update_date;
    }


    public Freshnest getFreshnest() {
        if (checkForFreshnest(release_date)) {
            return Freshnest.NEW;
        } else if (checkForFreshnest(update_date)) {
            return Freshnest.UPDATED;
        } else {
            return Freshnest.OLD;
        }
    }

    public String getFreshnestStr(Context context) {
        String retStr = null;
        switch (getFreshnest()) {
            case NEW:
                retStr = context.getString(R.string.mah_ads_new_text);
                break;

            case UPDATED:
                retStr = context.getString(R.string.mah_ads_updated_text);
                break;

            case OLD:
            default:
                break;
        }
        return retStr;
    }

    public int getFreshnestStrTextSizeInSP(Context context) {
        int sizeInSP = 10;
        switch (getFreshnest()) {
            case NEW:
                sizeInSP = 12;
                break;

            case UPDATED:
                sizeInSP = 10;
                break;

            case OLD:
            default:
                break;
        }
        return sizeInSP;
    }

    private boolean checkForFreshnest(String dateStr) { //Not later than 1 month
        boolean isFresh = false;

        try {
            Date dateAsDate = DATE_FORMATTER.parse(dateStr);
            if (dateAsDate != null) {
                long dateToday = new Date().getTime();
                long dateChecked = dateAsDate.getTime();
                long diff = dateToday - dateChecked;
                if (diff <= ONE_MONTTH_MILLI_SEC) {
                    isFresh = true;
                    Log.i("Test", "Program new = " + name + " date = " + dateAsDate);
                }
            }
        } catch (java.text.ParseException e) {
            Log.i("Test", "Paresing program date Exception: " + e.getMessage());
        }
        return isFresh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Program other = (Program) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
