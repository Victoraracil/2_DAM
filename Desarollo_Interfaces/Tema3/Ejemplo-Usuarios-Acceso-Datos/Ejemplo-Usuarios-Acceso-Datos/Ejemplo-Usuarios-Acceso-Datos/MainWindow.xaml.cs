using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Ejemplo_Usuarios_Acceso_Datos
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private async void Buscar_Click(object sender, RoutedEventArgs e)
        {
            // Función que busca un usuario por el nombre que hay en el formulario

            // Comprobamos que se haya introducido algun nombre en la casilla
            if (string.IsNullOrWhiteSpace(txtBuscarNombre.Text))
            {
                // Si no hay nada escrito
                MessageBox.Show("Debe introducir un nombre de usuario para buscar");
                LimpiarInterfaz();
            }
            else
            {
                // Extraemos el nombre del formulario
                string nombreUsuario = txtBuscarNombre.Text;
                // Iniciamos un objeto para acceder a la base de datos
                var service = new ServiceUser();
                // Le pedimos a la base de datos si tiene algun usuario con ese nombre
                var usuario = await service.Listar(nombreUsuario);
                if (usuario != null)
                {
                    // Si el usuario existe lo mostramos
                    lblId.Text = usuario.Id.ToString();
                    lblUsername.Text = usuario.Username;
                    lblFullName.Text = usuario.FullName;
                    lblCreatedAt.Text = usuario.CreatedAt.ToString("g");
                } else
                {
                    // Si no existe limpiamos pantalla y mostramos un warning
                    lblId.Text = "";
                    lblUsername.Text = "";
                    lblFullName.Text = "";
                    lblCreatedAt.Text = "";
                    MessageBox.Show("No se ha encontrado el usuario");
                }
            }
                
        }



        private async void Crear_Click(object sender, RoutedEventArgs e)
        {
            // Funcion que crea un usuario en la base de datos

            // Comprobamos que todos los campos del formulario esten cumplimentados
            if(string.IsNullOrWhiteSpace(txtNuevoUsername.Text) || string.IsNullOrWhiteSpace(txtNuevoFullName.Text) || string.IsNullOrWhiteSpace(txtNuevoPassword.Password))
            {
                // Si falta algun dato lo comunicamos
                MessageBox.Show("ERROR: No se puede crear el usuario. Por favor, rellene todos los campos");
            } else
            {
                // Si existen los datos, creamos un objeto usuario con esos datos
                var nuevoUser = new User
                {
                    Username = txtNuevoUsername.Text,
                    FullName = txtNuevoFullName.Text,
                    PasswordHash = PasswordHelper.HashPassword(txtNuevoPassword.Password)
                };
                // Iniciamos un objeto para acceder a la base de datos
                var service = new ServiceUser();
                // Le mandamos a la base de datos la informacion del nuevo usuario
                var nuevoUserCreado = await service.Insertar(nuevoUser);
                if(nuevoUserCreado != null)
                {
                    // Si el usuario se crea correctamente informamos
                    MessageBox.Show("Usuario creado correctamente.");
                } else
                {
                    // Si hay algun error, mostramos un warning
                    MessageBox.Show("Ha ocurrido un error en el proceso de creación.");
                }
                LimpiarInterfaz();
            }
                
        }

        private void Editar_Click(object sender, RoutedEventArgs e)
        {
            // Funcion para editar un usuario existente
            if (string.IsNullOrWhiteSpace(lblId.Text))
            {
                // Si no hay un usuario seleccionado mostramos un warning
                MessageBox.Show("No hay un usuario seleccionado para editar.");
            } else
            {
                // Si hay un usuario, cogemos su ID y creamos una ventana de edicion enviandole ese ID de usuario
                int userID = int.Parse(lblId.Text);
                var ventanaEditar = new EditarUserWindow(userID);
                // Ocultamos la ventana principal hasta que se cierre la de edición
                this.Hide();
                // Mostramos la ventana de edicion
                ventanaEditar.ShowDialog();
                // Volvemos a abrir la ventana principal una vez se cierra la anterior
                this.Show();
            }

        }

        private async void Borrar_Click(object sender, RoutedEventArgs e)
        {
            // Funcion que se encarga de eliminar el usuario seleccionado

            // Comprobamos que haya un usuario seleccionado
            if (string.IsNullOrWhiteSpace(lblId.Text))
            {
                // do nothing
            } else
            {
                // Iniciamos un objeto para acceder a la base de datos
                var service = new ServiceUser();
                // Le decimos a la base de datos que elimine el usuario con ese ID
                var borrado = await service.Borrar(int.Parse(lblId.Text));
                if (borrado)
                {
                    // Si el usuario se borra, informamos
                    MessageBox.Show("Usuario borrado correctamente.");
                }
                else
                {
                    // Si ocurre algun error, lanzamos un warning
                    MessageBox.Show("Ha ocurrido un error en el proceso de creación.");
                }
                LimpiarInterfaz();
            }

        }

        private void LimpiarInterfaz()
        {
            // Funcion simplemente estetica, sirve para limpiar los campos de los formularios tras una acción

            // Limpiamos el campo del buscador
            txtBuscarNombre.Text = "";

            // Limpiamos el formulario de crear usuario
            txtNuevoUsername.Text = "";
            txtNuevoFullName.Text = "";
            txtNuevoPassword.Password = "";


            // Limpiamos los resultados de la busqueda
            lblId.Text = "";
            lblUsername.Text = "";
            lblFullName.Text = "";
            lblCreatedAt.Text = "";
        }

    }
}