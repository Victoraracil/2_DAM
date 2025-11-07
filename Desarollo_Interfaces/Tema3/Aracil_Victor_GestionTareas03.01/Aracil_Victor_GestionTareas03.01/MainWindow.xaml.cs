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

namespace Aracil_Victor_GestionTareas03._01
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    /// <author> Victor Aracil Gozalvez</author>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }
        private void Window_MouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
            {
                DragMove();
            }
        }
        private void btn_Minimizar_Click(object sender, RoutedEventArgs e)
        {
            WindowState = WindowState.Minimized;
        }
        private void btn_Close_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }
        private void MenuSalir_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }

        private void MenuUsuario_Insertar(object sender, RoutedEventArgs e)
        {
            InsertarUsuarios insertarusuarios = new InsertarUsuarios();
            insertarusuarios.ShowDialog();
        }

        private void MenuUsuario_Modificar(object sender, RoutedEventArgs e)
        {
            ModificarUsuarios modificarusuarios = new ModificarUsuarios();
            modificarusuarios.ShowDialog();
        }

        private void MenuUsuario_Baja(object sender, RoutedEventArgs e)
        {
            BajaUsuario bajausuario = new BajaUsuario();
            bajausuario.ShowDialog();
        }
    }
}