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

namespace Ejemplo_Usuarios_Acceso_Datos
{
    /// <summary>
    /// Lógica de interacción para EditarUserWindow.xaml
    /// </summary>
    public partial class EditarUserWindow : Window
    {
        // Creamos un atributo para trabajar con el ID internamente
        private int _userID;
        public EditarUserWindow(int usuarioID)
        {
            InitializeComponent();
            // Guardamos el valor del atributo que recibimos
            _userID = usuarioID;
            // Cargar datos del usuario al abrir la ventana
            CargarUsuario(_userID);
        }

        private async void CargarUsuario(int usuarioID)
        {
            // Función que carga la información actual del usuario que se va a editar en el formulario

            // Iniciamos un objeto para acceder a la base de datos
            var service = new ServiceUser();
            // Le pedimos a la base de datos el usuario que tenemos que mostrar
            var usuario = await service.Listar(usuarioID);
            if (usuario != null)
            {
                // Si el usuario sigue existiendo actualizamos los campos del formulario
                txtUsernameEdit.Text = usuario.Username;
                txtFullNameEdit.Text = usuario.FullName;
            }
            else
            {
                // Si no se encuentra el usuario, se muestra un error
                MessageBox.Show("No se ha encontrado el usuario.");
                this.Close();
            }
        }

        private void Cancelar_Click(object sender, RoutedEventArgs e)
        {
            // Funcion que cierra la ventana

            this.Close(); // Cierra la ventana
        }

        private async void Aplicar_Click(object sender, RoutedEventArgs e)
        {
            // Función que aplica los cambios en la base de datos

            // Iniciamos un objeto para acceder a la base de datos
            var service = new ServiceUser();
            // Le pedimos a la base de datos el usuario que tenemos que mostrar
            var usuarioActualizado = await service.Listar(_userID);
            if (usuarioActualizado != null)
            {
                // Si el usuario sigue existiendo actualizamos la información en el objeto recibido

                // Comprobamos que todos los campos del formulario esten cumplimentados
                if (string.IsNullOrWhiteSpace(txtUsernameEdit.Text) || string.IsNullOrWhiteSpace(txtFullNameEdit.Text) || string.IsNullOrWhiteSpace(txtPasswordEdit.Password))
                {
                    // Si falta algun dato lo comunicamos
                    MessageBox.Show("ERROR: No se puede actualizar el usuario. Por favor, rellene todos los campos");
                }
                else
                {
                    usuarioActualizado.Username = txtUsernameEdit.Text;
                    usuarioActualizado.FullName = txtFullNameEdit.Text;
                    usuarioActualizado.PasswordHash = PasswordHelper.HashPassword(txtPasswordEdit.Password);
                    // Mandamos a la base de datos el usuario actualizado
                    var actualizado = await service.Actualizar(usuarioActualizado);
                    if (actualizado)
                    {
                        // Si la base de datos nos confirma la actualizacion, informamos y cerramos el formulario
                        MessageBox.Show("Usuario actualizado correctamente.");
                        this.Close();
                    }
                    else
                    {
                        // Si ha ocurrido algun error, lanzamos un warning
                        MessageBox.Show("Ha ocurrido un error en el proceso de actualización.");
                    }
                }
                
            }
            else
            {
                // Si ha ocurrido algun error, lanzamos un warning
                MessageBox.Show("Ha ocurrido un error.");
                this.Close();
            }
            
        }

    }
}
