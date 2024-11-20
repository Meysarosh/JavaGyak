package com.example.utazas;

import jakarta.persistence.*;

@Entity
@Table(name="tavasz")
public class Tavasz {
    @Id
    private int sorszam;
    @Column(name="szalloda_az")
    private String szalloda_az;
    @Column(name="indulas")
    private String indulas;
    @Column(name="idotartam")
    private int idotartam;
    @Column(name="ar")
    private int ar;

    @ManyToOne
    @JoinColumn(name = "szalloda_az", referencedColumnName = "az",insertable=false, updatable=false)
//    @JoinColumn(name = "az", insertable=false, updatable=false)
    private Szalloda szalloda;

    public int getSorszam() {
        return sorszam;
    }

    public void setSorszam(int sorszam) {
        this.sorszam = sorszam;
    }

    public Szalloda getSzalloda() {
        return szalloda;
    }

    public void setSzalloda(Szalloda szalloda) {
        this.szalloda = szalloda;
    }

    public int getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(int idotartam) {
        this.idotartam = idotartam;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public String getIndulas() {
        return indulas;
    }

    public void setIndulas(String indulas) {
        this.indulas = indulas;
    }

    public String getSzalloda_az() {
        return szalloda_az;
    }

    public void setSzalloda_az(String szalloda_az) {
        this.szalloda_az = szalloda_az;
    }
}
