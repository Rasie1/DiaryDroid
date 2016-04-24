package com.kvachev.diarydroid;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by rasiel on 21.04.16.
 */
public class DiaryEntry implements Serializable {
//    public Calendar date; фичу пришлось вырезать из-за внезапного дедлайна
    public String message;

    public String toString()
    {
        return "{" +/* date + "|" + */ message + "}";
    }
}
