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
        private void MenuTareas_Crear(object sender, RoutedEventArgs e)
        {
            CrearTarea crearTarea = new CrearTarea();
            crearTarea.ShowDialog();
        }

        private List<Tarea> todasLasTareas;

        private async void Window_Loaded(object sender, RoutedEventArgs e)
        {
            todasLasTareas = await ObtenerTareas();
            AplicarFiltros();
        }

        private void Filtro_TextChanged(object sender, EventArgs e)
        {
            AplicarFiltros();
        }

        private void AplicarFiltros()
        {
            string filtroTitulo = txtFiltroTitulo.Text.ToLower();
            string estadoSeleccionado = (cmbEstado.SelectedItem as ComboBoxItem)?.Content.ToString();

            var filtradas = todasLasTareas.Where(t =>
                t.Titulo.ToLower().Contains(filtroTitulo) &&
                (estadoSeleccionado == "Todos" || t.Estado.ToString() == estadoSeleccionado) &&
                (estadoSeleccionado == "Archivada" || t.Estado != EstadoTarea.Archivada)
            ).ToList();

            lvTareas.ItemsSource = filtradas;
        }

        private async Task<List<Tarea>> ObtenerTareas()
        {
            using (var service = new ServiceTarea())
            {
                return await service.Listar();
            }
        }

    }
}