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
        private int userIdActual; //ID del usuario logueado
        private List<Tarea> todasLasTareas;

        // Constructor con usuario actual
        public MainWindow(int userId)
        {
            InitializeComponent();
            userIdActual = userId;
        }

        private void Window_MouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
                DragMove();
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
            new InsertarUsuarios().ShowDialog();
        }

        private void MenuUsuario_Modificar(object sender, RoutedEventArgs e)
        {
            new ModificarUsuarios().ShowDialog();
        }

        private void MenuUsuario_Baja(object sender, RoutedEventArgs e)
        {
            new BajaUsuario().ShowDialog();
        }

        private void MenuTareas_Crear(object sender, RoutedEventArgs e)
        {
            //Pasamos el usuario actual a la ventana de creación
            var crearTarea = new CrearTarea(userIdActual);
            crearTarea.ShowDialog();
            RecargarTareas();
        }

        private async void Window_Loaded(object sender, RoutedEventArgs e)
        {
            await RecargarTareas();
        }

        private void Filtro_TextChanged(object sender, EventArgs e)
        {
            AplicarFiltros();
        }

        private async Task RecargarTareas()
        {
            todasLasTareas = await ObtenerTareas();
            AplicarFiltros();
        }

        private void AplicarFiltros()
        {
            if (todasLasTareas == null) return;

            string filtroTitulo = txtFiltroTitulo.Text.ToLower();
            string estadoSeleccionado = (cmbEstado.SelectedItem as ComboBoxItem)?.Content?.ToString() ?? "Todos";

            var filtradas = todasLasTareas.Where(t =>
                t.UserId == userIdActual && // <-- Solo sus tareas
                t.Titulo.ToLower().Contains(filtroTitulo) &&
                (estadoSeleccionado == "Todos" || t.Estado.ToString() == estadoSeleccionado)
            ).ToList();

            lvTareas.ItemsSource = filtradas;
        }

        private async Task<List<Tarea>> ObtenerTareas()
        {
            using var service = new ServiceTarea();
            var todas = await service.Listar();
            //Filtrar por usuario
            return todas.Where(t => t.UserId == userIdActual).ToList();
        }

        private void lvTareas_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
        }

        private void lvTareas_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            if (lvTareas.SelectedItem is Tarea tareaSeleccionada)
            {
                var crearTarea = new CrearTarea(userIdActual, tareaSeleccionada.Id);
                crearTarea.ShowDialog();
                RecargarTareas();
            }
        }
    }

}