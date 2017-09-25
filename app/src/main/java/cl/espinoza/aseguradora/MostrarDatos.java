package cl.espinoza.aseguradora;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MostrarDatos extends AppCompatActivity {
    TextView    tvMarcaModelo;
    TextView    tvAntiguedad;
    TextView    tvPatente;
    TextView    tvResultado;
    TextView    tvValor;
    ImageView   imResultado;
    String      ppu;
    String      marca;
    String      modelo;
    String      mensajeEstadoSeguro;
    String      mensajeDatosSeguro;
    String      mensajeMarcaModelo;
    String      mensajePatente;
    String      mensajeAntiguedad;
    String      estadoVehiculo; //Asegurable o no Asegurable
    int         anioVehiculo;
    int         anioActual;
    int         antiguedad;
    double      valorUF;
    String      valorSeguro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        tvMarcaModelo   = (TextView) findViewById(R.id.txv_MarcaAuto);
        tvPatente       = (TextView) findViewById(R.id.txv_PatenteAuto);
        tvAntiguedad    = (TextView) findViewById(R.id.txv_AntiguedadAuto);
        tvResultado     = (TextView) findViewById(R.id.txv_resultado);
        tvValor         = (TextView) findViewById(R.id.txv_ValorSeguro);
        imResultado     = (ImageView)findViewById(R.id.imv_Estado);
        //Se obtienen los datos capturados en MainActivity
        Bundle bundle   = getIntent().getExtras();
        ppu             = bundle.getString("ppu");
        marca           = bundle.getString("marca");
        modelo          = bundle.getString("modelo");
        anioVehiculo    = bundle.getInt("anioVehiculo");
        anioActual      = bundle.getInt("anioActual");
        antiguedad      = obtnenerAntiguedad(anioActual,anioVehiculo);
        estadoVehiculo  = determinarEstadoVehiculo(antiguedad);
        valorUF         =26647.91;//Valor de UF a la fecha 25-09-2017 05:49 PM
        valorSeguro     = calcularValorSeguro(antiguedad, valorUF);
        //Creacion del mensaje a mostrar
        mensajePatente          =String.format(getResources().getString(R.string.mensaje_patente),ppu);
        mensajeMarcaModelo      = String.format(getResources().getString(R.string.mensaje_marcamodelo),marca,modelo);
        if(antiguedad > 1){
            mensajeAntiguedad =String.format(getResources().getString(R.string.mensaje_2antiguedad),antiguedad);
        }else if (antiguedad == 1){
            mensajeAntiguedad =String.format(getResources().getString(R.string.mensaje_1antiguedad),antiguedad);
        }else{
            mensajeAntiguedad = getResources().getString(R.string.mensaje_menos1antiguedad);
        }
        mensajeEstadoSeguro     = String.format(getResources().getString(R.string.mensaje_resultado),estadoVehiculo);
        mensajeDatosSeguro      = String.format(getResources().getString(R.string.valor_seguro),valorSeguro);

        //Se muestra el mensaje con los datos del vehiculo
        if (estadoVehiculo.equals("ES ASEGURABLE")){
            imResultado.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.valido));
        }else{
            imResultado.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.novalido));
        }
        tvPatente.setText(mensajePatente);
        tvMarcaModelo.setText(mensajeMarcaModelo);
        tvAntiguedad.setText(mensajeAntiguedad);
        tvResultado.setText(mensajeEstadoSeguro);
        tvValor.setText(mensajeDatosSeguro);
    }

    /* Calcula el valor del seguro, multiplicando 0,1 UF por Años de antiguedad,
    y esto transladandolo a pesos chilenos multiplicando por el valor de la uf */
    public String calcularValorSeguro (int antiguedad, double uf){
        String stvalor;
        double valor;
        DecimalFormat df = new DecimalFormat("#.00");
        if(antiguedad <= 10 && antiguedad >=1){
            valor = (antiguedad * 0.1);
            valor = valor * uf;
        }else if(antiguedad <=0) {
            valor = (1 * 0.1);
            valor = valor * uf;
        }else{
            valor = 0;
        }
        df.format(valor);
        return df.format(valor);
    }

    public int obtnenerAntiguedad(int anioActual, int anioVehiculo){
        int diferencia;
        diferencia = anioActual - anioVehiculo;
        return diferencia;
    }

    public String determinarEstadoVehiculo(int antiguedad){
        if (antiguedad <= 10){
            return "ES ASEGURABLE";
        }else {
            return "NO ES ASEGURABLE";
        }
    }

    public void salir(View v) {
        finish();
    }
}
