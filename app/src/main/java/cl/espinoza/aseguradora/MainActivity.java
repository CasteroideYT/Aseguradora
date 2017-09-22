package cl.espinoza.aseguradora;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ToggleButton;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText        edtPPU1; //Primeras 2 Letras de ppu
    EditText        edtPPU2; // Segundas 2 letras o numeros de ppu, segun el formato seleccionado
    EditText        edtPPU3; // Ultimos 2 numeros de ppu
    EditText        edtMarca;
    EditText        edtModelo;
    ToggleButton    btnTipoPpu;
    NumberPicker    npAnio;
    String          ufApiURL    = "http://api.sbif.cl/api-sbifv3/recursos_api/uf";  // API para obtener uf, URL base
    String          ufKey       = "bf8b1c148c395337b5bd244a51b67a571670e7a8"; //Api para obtener UF, clave
    String          url; //La mescla de url base y Clave
    String          ppu; //En este string se juntaran las 3 partes de la ppu
    int             anioActual  = obtenerAnioActual();
    int             anioVehiculo;
    boolean         correcto    = true;
    boolean         formatonuevo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtPPU1     = (EditText)     findViewById(R.id.etx_PPU1);
        edtPPU2     = (EditText)     findViewById(R.id.etx_PPU2);
        edtPPU3     = (EditText)     findViewById(R.id.etx_PPU3);
        edtMarca    = (EditText)     findViewById(R.id.etx_Marca);
        edtModelo   = (EditText)     findViewById(R.id.etx_Modelo);
        npAnio      = (NumberPicker) findViewById(R.id.np_Anio);
        btnTipoPpu  = (ToggleButton) findViewById(R.id.tgb_formato);
        //Se especifica un valor minimo para el año, un maximo y el seleccionado por defecto
        npAnio.setMinValue(1940);
        npAnio.setMaxValue(anioActual+1);
        npAnio.setValue(anioActual-10);
        //Permite que el selector se mueva entre los limites
        npAnio.setWrapSelectorWheel(true);

        //Metodos para cambiar el tipo de entrada al cambiar el ToggleButton
        btnTipoPpu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean seleccionado) {
                if (seleccionado){
                    //Se configura para el formato Nuevo de patente
                    edtPPU2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    edtPPU2.setHint("AA");
                    formatonuevo = true;
            }else{
                    edtPPU2.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edtPPU2.setHint("00");
                    formatonuevo = false;
                }
        };

        });
    }
    public void ver (View v) {
        correcto = true;
        anioVehiculo = npAnio.getValue();
        ppu = (edtPPU1.getText().toString()+edtPPU2.getText().toString()+edtPPU3.getText().toString()).toUpperCase();
        validarVacios(); //Se llama al metodo para validar que no existan datos en blanco
        if (formatonuevo==true){
            validadPpuNueva(); //Se llama al metodo para validar el ingreso segun formato de Patente nueva
        }else {
            validarPpuAntigua(); //Se llama al metodo para validar el ingreso segun formato de Patente antigua
        }
        if(correcto==true){
            Intent i=new Intent(this,MostrarDatos.class);
            i.putExtra("ppu", ppu);
            i.putExtra("marca", edtMarca.getText().toString());
            i.putExtra("modelo", edtModelo.getText().toString());
            i.putExtra("anioActual", anioActual);
            i.putExtra("anioVehiculo", anioVehiculo);
            startActivity(i);
        }
    }
    public int obtenerAnioActual() {
        Calendar calendario = new GregorianCalendar(TimeZone.getTimeZone("America/Santiago"));
        return calendario.get(Calendar.YEAR);
    }
    public boolean validarVacios(){
        //Valida que el campo de Marca no este vacio
        if(edtMarca.getText().toString().equals("")){
            edtMarca.setError("Ingrese marca del vehiculo");
            correcto = false;
        }
        //Valida que el campo de Modelo no este vacio
        if(edtModelo.getText().toString().equals("")){
            edtModelo.setError("Ingrese modelo del vehiculo");
            correcto = false;
        }
        if(edtPPU1.getText().toString().length()!=2){
            edtPPU1.setError("Debe ingresar 2 digitos");
            correcto = false;
        }
        if(edtPPU2.getText().toString().length()!=2){
            edtPPU2.setError("Debe ingresar 2 digitos");
            correcto = false;
        }
        if(edtPPU3.getText().toString().length()!=2){
            edtPPU3.setError("Debe ingresar 2 digitos");
            correcto = false;
        }

        return correcto;
    }
    public boolean validarPpuAntigua (){
        Pattern patronVieja = Pattern.compile("[ABCEFGHDKLNPRSTUVXYZWM][ABCEFGHDKLNPRSTUVXYZWM]");
        Matcher valPpuPt1 = patronVieja.matcher(edtPPU1.getText().toString());
        if(!valPpuPt1.find()){
            edtPPU1.setError("Ingrese formato valido");
            correcto = false;
        }
        return correcto;
    }
    public boolean validadPpuNueva(){
        Pattern patronNueva = Pattern.compile("[BCDFGHJKLPRSTVWXYZ][BCDFGHJKLPRSTVWXYZ]");
        Matcher valPpuPt1 = patronNueva.matcher(edtPPU1.getText().toString());
        Matcher valPpuPt2 = patronNueva.matcher(edtPPU2.getText().toString());
        if(!valPpuPt1.find()){
            edtPPU1.setError("Ingrese formato valido");
            correcto = false;
        }
        if(!valPpuPt2.find()){
            edtPPU2.setError("Ingrese formato valido");
            correcto = false;
        }
        return correcto;
    }
}
