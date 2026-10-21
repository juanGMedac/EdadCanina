package es.davante.edadcanina;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Constantes: evitamos "números mágicos" repartidos por el código
    private static final int FACTOR_CANINO = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad

    private EditText ageEdit;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Declaramos variables
        ageEdit = findViewById(R.id.age_input);
        resultText = findViewById(R.id.result_text);
        Button button1 = findViewById(R.id.btn_calc);

        // Implementar el botón (setOnClickListener)
        button1.setOnClickListener(view -> calcularEdad());
    }

    /**
     * Lee la edad, la valida y muestra el resultado.
     * Si algo falla, marca el error en el EditText y no calcula nada.
     */
    private void calcularEdad() {
        // 1. Leer el texto quitando espacios al principio y al final
        String ageString = ageEdit.getText().toString().trim();

        // 2. Campo vacío: Integer.parseInt("") lanzaría NumberFormatException
        if (ageString.isEmpty()) {
            mostrarError(getString(R.string.error_vacio));
            return;
        }

        // 3. Conversión segura de String a int
        //    (texto no numérico, decimales pegados o números enormes que no caben en un int)
        int age;
        try {
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            mostrarError(getString(R.string.error_no_numero));
            return;
        }

        // 4. Rango lógico: ni 0, ni negativos, ni edades imposibles.
        //    Además garantiza que age * 7 nunca desborda el int.
        if (age < EDAD_MINIMA || age > EDAD_MAXIMA) {
            mostrarError(getString(R.string.error_rango, EDAD_MINIMA, EDAD_MAXIMA));
            return;
        }

        // 5. Todo correcto: calculamos y mostramos
        ageEdit.setError(null);
        int dogAge = age * FACTOR_CANINO;
        resultText.setText(getString(R.string.tu_edad_format, dogAge));
    }

    /** Muestra el mensaje de error en el campo y borra un resultado anterior. */
    private void mostrarError(String mensaje) {
        ageEdit.setError(mensaje);
        ageEdit.requestFocus();
        resultText.setText("");
    }
}
