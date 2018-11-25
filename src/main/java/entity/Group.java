package entity;

import interfaces.Identified;

public class Group implements Identified<Long> {
    
    private Long number;
    private String nameFaculty;

    public Group() {
    }

    public Group(long number, String nameFaculty) {
        this.number = number;
        this.nameFaculty = nameFaculty;
    }

    public long getNumber() {
        return number;
    }

    public String getNameFaculty() {
        return nameFaculty;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setNameFaculty(String nameFaculty) {
        this.nameFaculty = nameFaculty;
    }

    @Override
    public Long getId() {
        return number;
    }

    @Override
    public String toString() {
        return "Group{" + "number=" + number + ", nameFaculty=" + nameFaculty + '}';
    }
    
}
