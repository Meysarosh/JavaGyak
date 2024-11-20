package com.example.utazas;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="helyseg")
public class Helyseg {
    @Id
    private int az;
    @Column(name="nev")
    private String nev;
    @Column(name="orszag")
    private String orszag;

//    @OneToMany
//    @JoinColumn(name="helyseg_az", insertable=false, updatable=false)
//    private List<Szalloda> szalloda;

    @OneToMany(mappedBy = "helyseg") // Mapped by Szalloda
    private List<Szalloda> szalloda;

    public int getAz() {
        return az;
    }

    public void setAz(int az) {
        this.az = az;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public void setOrszag(String orszag) {
        this.orszag = orszag;
    }

    public List<Szalloda> getSzalloda() {
        return szalloda;
    }

    public void setSzalloda(List<Szalloda> szalloda) {
        this.szalloda = szalloda;
    }
}
