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
            // Funcion que crea un usuario en la base de datos

            // Comprobamos que todos los campos del formulario esten cumplimentados
            if (string.IsNullOrWhiteSpace(txtNuevoUsername.Text) || string.IsNullOrWhiteSpace(txtNuevoFullName.Text) || string.IsNullOrWhiteSpace(txtNuevoPassword.Password))
            {
                // Si falta algun dato lo comunicamos
                MessageBox.Show("ERROR: No se puede crear el usuario. Por favor, rellene todos los campos");
            }
            else
            {
                // Si existen los datos, creamos un objeto usuario con esos datos
                var nuevoUser = new User
                {
                    Usuario = txtNuevoUsername.Text,
                    NombreCompleto = txtNuevoFullName.Text,
                    PasswordHash = PasswordHelper.HashPassword(txtNuevoPassword.Password),
                    CorreoElectronico = txtNuevoEmail.Text,
                    Activo = 1,
                }
                ;
                // Iniciamos un objeto para acceder a la base de datos
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
            // Funcion simplemente estetica, sirve para limpiar los campos de los formularios tras una acción

            // Limpiamos el formulario de crear usuario
            txtNuevoUsername.Text = "";
            txtNuevoFullName.Text = "";
            txtNuevoPassword.Password = "";
            txtNuevoEmail.Text = "";
        }
    }
}
