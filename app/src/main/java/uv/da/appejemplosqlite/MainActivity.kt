package uv.da.appejemplosqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.ContentValues
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtId = findViewById<EditText>(R.id.txtId)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtPrecio = findViewById<EditText>(R.id.txtPrecio)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnConsultar = findViewById<Button>(R.id.btnConsultar)
        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        btnRegistrar.setOnClickListener {

            val admin = AdminSQLite (this, "admistracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo", txtId.getText().toString())
            registro.put("descripcion", txtDescripcion.getText().toString())
            registro.put("precio", txtPrecio.getText().toString())

            bd.insert("articulos", null, registro)
            bd.close()

            txtId.setText("")
            txtDescripcion.setText("")
            txtPrecio.setText("")

            Toast.makeText(this, "Se registraron los artículos", Toast.LENGTH_SHORT).show()

            //comentario que debo borrar, para agregar
            var borrarme: Int = 10

        }

        btnConsultar.setOnClickListener {
            val admin = AdminSQLite (this, "admistracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo = '${txtId.text.toString()}'", null)

            if (fila.moveToFirst()){

                txtDescripcion.setText(fila.getString(0))
                txtPrecio.setText(fila.getString(1))

            }else{
                Toast.makeText(this, "No existe el artículo", Toast.LENGTH_SHORT).show()
            }

            bd.close()

        }

        btnEliminar.setOnClickListener {
            val admin = AdminSQLite (this, "admistracion", null, 1)
            val bd = admin.writableDatabase
            val cantidad = bd.delete("articulos", "codigo = '${txtId.text.toString()}'",null)

            bd.close()

            txtId.setText("")
            txtDescripcion.setText("")
            txtPrecio.setText("")

            if (cantidad == 1)
                Toast.makeText(this, "Eliminación exitosa del producto", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No se encontró el elemento", Toast.LENGTH_SHORT).show()


        }

        btnActualizar.setOnClickListener {
            val admin = AdminSQLite (this, "admistracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()

            registro.put("descripcion", txtDescripcion.text.toString())
            registro.put("precio", txtPrecio.text.toString())

            val cantidad = bd.update("articulos", registro, "codigo = '${txtId.text.toString()}'", null)
            bd.close()

            if (cantidad == 1)
                Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No se encontró el artículo", Toast.LENGTH_SHORT).show()

        }

        //Actividad: Consulta por "descripción"; validen que EditText no está vacío, para que se haga la consulta






    }
}