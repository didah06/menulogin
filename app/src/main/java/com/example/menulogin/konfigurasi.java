package com.example.menulogin;

import java.net.FileNameMap;

public class konfigurasi {
    public static final String URL_ADD = "http://192.168.3.67/menulogin/insert_absensi.php";
    public  static final String URL_GET_ALL = "http:// 192.168.3.67/menulogin/getPLP.php";
    public static final String URL_GET_EMP = "http:// 192.168.3.67/menulogin/getSinglePlp.php?id_santri=";
    public static final String URL_SEARCH = "http:// 192.168.3.67/menulogin/search_data.php";
    public  static final String simpan_url= "http:// 192.168.3.67/menulogin/input_absensi.php";
    public static final String inputziyadah_url= "http:// 192.168.5.3.67/menulogin/mutabaah.php";


    public static final String KEY_EMP_ID = "id_santri";
    public static final String KEY_EMP_NAMA = "name";
    public static final String KEY_EMP_IDPLP = "id_plp";
    public static final String KEY_EMP_TANGGAL = "tanggal";
    public static final String KEY_EMP_SESI = "sesi";
    public static final String KEY_EMP_STATUS = "status_kehadiran";
    public static final String KEY_EMP_KET = "ket";

    public static final String KEY_EMP_JUZ = "juz";
    public static final String KEY_EMP_IDSURAT = "id_surat";
    public static final String KEY_EMP_AYATAWAL = "ayat_awal";
    public static final String KEY_EMP_AYATAKHIR = "ayat_akhir";
    public static final String KEY_EMP_NILAI = "nilai";


    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id_santri";
    public static final String TAG_NAMA = "name";
    public static final String TAG_IDPLP = "id_plp";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";
    public static final String TAG = daftarkelas.class.getSimpleName();
    public static final String EMP_ID = "emp_id";
    public static final String EMP_ID2 = "emp_id";

}
