using Aracil_Victor_GestionTareas03._01.Migrations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Aracil_Victor_GestionTareas03._01
{
    /// <summary>
    /// Lógica de interacción para InsertarUsuarios.xaml
    /// </summary>
    ///<author> Victor Aracil Gozalvez</author>
    public partial class InsertarUsuarios : Window
    {
        public InsertarUsuarios()
        {
            InitializeComponent();
        }
        private async void Crear_Click(object sender, RoutedEventArgs e)
        {

            // Comprobacion de que los campos son correctos
            if (string.IsNullOrWhiteSpace(txtNuevoUsername.Text) || string.IsNullOrWhiteSpace(txtNuevoFullName.Text) || string.IsNullOrWhiteSpace(txtNuevoPassword.Password))
            {
                //Mensaje de error si falta dato
                MessageBox.Show("ERROR: No se puede crear el usuario. Por favor, rellene todos los campos");
            }
            else
            {
                // Si existen los datos, creamos un objeto usuario con esos datos
                var nuevoUser = new User
                {
                    Usuario = txtNuevoUsername.Text,
                    NombreCompleto = txtNuevoFullName.Text,
                    PasswordHash = txtNuevoPassword.Password,//no hasheo la contraseña porque luego da errores, te lo explico el proximo dia
                    CorreoElectronico = txtNuevoEmail.Text,
                    Activo = 1,
                }
                ;
                var service = new ServiceUser();
                // Le mandamos a la base de datos la informacion del nuevo usuario
                var nuevoUserCreado = await service.Insertar(nuevoUser);
                if (nuevoUserCreado != null)
                {
                    // Si el usuario se crea correctamente informamos
                    MessageBox.Show("Usuario creado correctamente.");
                }
                else
                {
                    // Si hay algun error, mostramos un warning
                    MessageBox.Show("Ha ocurrido un error en el proceso de creación.");
                }
                LimpiarInterfaz();
            }

        }

        private void LimpiarInterfaz()
        {

            // Limpiamos el formulario de crear usuario
            txtNuevoUsername.Text = "";
            txtNuevoFullName.Text = "";
            txtNuevoPassword.Password = "";
            txtNuevoEmail.Text = "";
        }
    }
}
