package dev.xfoil.models;

public class BillInfoMap{
    private String denomination;
    private Integer count;

    public BillInfoMap() {
    }

    public BillInfoMap(String denomination, Integer count) {
        this.denomination = denomination;
        this.count = count;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "{denomination='" + denomination + ", count=" + count+"}" ;
    }
}

