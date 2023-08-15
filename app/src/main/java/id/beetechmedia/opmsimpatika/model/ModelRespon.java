package id.beetechmedia.opmsimpatika.model;

import java.util.List;

/**
 * Created by Sabiqul on 22/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class ModelRespon {
    private String kode, pesan;

    private List<ModelOpm> dataopm;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }


    public List<ModelOpm> getDataopm() {
        return dataopm;
    }
}