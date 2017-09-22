package cl.espinoza.aseguradora;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.android.volley.RequestQueue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    TextView    uf;
    EditText    edtPPU1; //Primeras 2 Letras de ppu
    EditText    edtPPU2; // Segundas 2 letras o numeros de ppu, segun el formato seleccionado
    EditText    edtPPU3; // Ultimos 2 numeros de ppu
    EditText    edtMarca;
    EditText    edtModelo;
    NumberPicker    npAnio;
    String      ufApiURL    = "http://api.sbif.cl/api-sbifv3/recursos_api/uf";  // API para obtener uf, URL base
    String      ufKey       = "bf8b1c148c395337b5bd244a51b67a571670e7a8"; //Api para obtener UF, clave
    String      url; //La mescla de url base y Clave
    String      ppu; //En este string se juntaran las 3 partes de la ppu
    int         anioActual = obtenerAnioActual();
    int         anioVehiculo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtPPU1     = (EditText) findViewById(R.id.etx_PPU1);
        edtPPU2     = (EditText) findViewById(R.id.etx_PPU2);
        edtPPU3     = (EditText) findViewById(R.id.etx_PPU3);
        edtMarca    = (EditText) findViewById(R.id.etx_Marca);
        edtModelo   = (EditText) findViewById(R.id.etx_Modelo);
        npAnio      = (NumberPicker) findViewById(R.id.np_anio);
        //Se especifica un valor minimo para el año, un maximo y el seleccionado por defecto
        npAnio.setMinValue(1980);
        npAnio.setMaxValue(anioActual);
        npAnio.setValue(2010);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        npAnio.setWrapSelectorWheel(true);


    }
    public void ver (View v) {
        Intent i=new Intent(this,MostrarDatos.class);
        anioVehiculo = npAnio.getValue();
        ppu = (edtPPU1.getText().toString()+edtPPU2.getText().toString()+edtPPU3.getText().toString()).toUpperCase();
        i.putExtra("ppu", ppu);
        i.putExtra("marca", edtMarca.getText().toString());
        i.putExtra("modelo", edtModelo.getText().toString());
        i.putExtra("anioActual", anioActual);
        i.putExtra("anioVehiculo", anioVehiculo);
        startActivity(i);
    }
    public int obtenerAnioActual() {
        Calendar calendario = new GregorianCalendar(TimeZone.getTimeZone("America/Santiago"));
        return calendario.get(Calendar.YEAR);
    }




}
