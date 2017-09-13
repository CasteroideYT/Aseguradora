package cl.espinoza.aseguradora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MostrarDatos extends AppCompatActivity {
    TextView    tvDatos;
    TextView    tvValor;
    String      ppu;
    String      marca;
    String      modelo;
    String      datosVehiculo;
    String      datosSeguro;
    String      estadoVehiculo; //Asegurable o no Asegurable
    int         anioVehiculo;
    int         anioActual;
    int         antiguedad;
    double      valorUF;
    double      valorSeguro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        tvDatos         = (TextView) findViewById(R.id.txv_DatosAuto);
        tvValor         = (TextView) findViewById(R.id.txv_ValorSeguro);
        //Se obtienen los datos capturados en MainActivity
        Bundle bundle   = getIntent().getExtras();
        ppu             = bundle.getString("ppu");
        marca           = bundle.getString("marca");
        modelo          = bundle.getString("modelo");
        anioVehiculo    = bundle.getInt("anioVehiculo");
        anioActual      = bundle.getInt("anioActual");
        antiguedad      = obtnenerAntiguedad(anioActual,anioVehiculo);
        estadoVehiculo  = determinarEstadoVehiculo(antiguedad);
        valorUF         = 26624.85; //Valor de UF a la fecha 12-09-2017 02:45 AM
        valorSeguro     = calcularValorSeguro(antiguedad, valorUF);
        //Creacion del mensaje a mostrar
        datosVehiculo   = String.format(getResources().getString(R.string.mensaje_resultado),marca,modelo,ppu,antiguedad,estadoVehiculo);
        datosSeguro     = String.format(getResources().getString(R.string.valor_seguro),valorSeguro);
        //Se muestra el mensaje con los datos del vehiculo
        tvDatos.setText(datosVehiculo);
        tvValor.setText(datosSeguro);
    }

    /* Calcula el valor del seguro, multiplicando 0,1 UF por Años de antiguedad,
    y esto transladandolo a pesos chilenos multiplicando por el valor de la uf */
    public double calcularValorSeguro (int antiguedad, double uf){
        double valor;
        if(antiguedad < 10){
            valor = (antiguedad * 0.1);
            valor = valor * uf;
        }else {
            valor = 0;
        }

        return valor;
    }

    public int obtnenerAntiguedad(int anioActual, int anioVehiculo){
        int diferencia;
        diferencia = anioActual - anioVehiculo;
        return diferencia;
    }

    public String determinarEstadoVehiculo(int antiguedad){
        if (antiguedad < 10){
            return "ES ASEGURABLE";
        }else {
            return "NO ES ASEGURABLE";
        }
    }

    public void salir(View v) {
        finish();
    }
}
