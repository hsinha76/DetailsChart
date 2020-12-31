package com.application.detailschart.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stat_table")
public class StatLocal implements Comparable<StatLocal> {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ColumnInfo(name = "priority")
    int priority;
    @ColumnInfo(name = "month")
    String month;
    @ColumnInfo(name = "stat")
    String stat;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public int compareTo(StatLocal o) {
        return Integer.compare(priority, o.getPriority());
    }
}
